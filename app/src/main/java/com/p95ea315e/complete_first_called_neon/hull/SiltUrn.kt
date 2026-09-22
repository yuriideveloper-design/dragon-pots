package com.p95ea315e.complete_first_called_neon.hull

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.util.Base64
import android.webkit.WebView
import com.p95ea315e.complete_first_called_neon.cask.VaultShelf

class SiltUrn(context: Context) {
    private val store = VaultShelf.open(context, VaultShelf.SURFACE)

    fun hasStateFor(target: String): Boolean {
        if (target.isBlank()) return false
        return store.getString(KEY_DEST, null) == target &&
            !store.getString(KEY_STATE, null).isNullOrBlank()
    }

    fun save(webView: WebView, target: String) {
        if (target.isBlank()) return
        val bundle = Bundle()
        webView.saveState(bundle)
        val encoded = marshal(bundle) ?: run {
            clear()
            return
        }
        store.edit()
            .putString(KEY_STATE, encoded)
            .putString(KEY_DEST, target)
            .putString(KEY_LAST_URL, webView.url ?: "")
            .apply()
    }

    fun restoreInto(webView: WebView, target: String): Boolean {
        if (!hasStateFor(target)) return false
        val encoded = store.getString(KEY_STATE, null).orEmpty()
        val bundle = unmarshal(encoded) ?: run {
            clear()
            return false
        }
        return try {
            webView.restoreState(bundle) != null
        } catch (_: Exception) {
            clear()
            false
        }
    }

    fun clear() {
        store.edit()
            .remove(KEY_STATE)
            .remove(KEY_DEST)
            .remove(KEY_LAST_URL)
            .apply()
    }

    private fun marshal(bundle: Bundle): String? {
        val parcel = Parcel.obtain()
        return try {
            parcel.writeBundle(bundle)
            Base64.encodeToString(parcel.marshall(), Base64.NO_WRAP)
        } catch (_: Exception) {
            null
        } finally {
            parcel.recycle()
        }
    }

    private fun unmarshal(encoded: String): Bundle? {
        val parcel = Parcel.obtain()
        return try {
            val bytes = Base64.decode(encoded, Base64.NO_WRAP)
            parcel.unmarshall(bytes, 0, bytes.size)
            parcel.setDataPosition(0)
            parcel.readBundle(WebView::class.java.classLoader)
        } catch (_: Exception) {
            null
        } finally {
            parcel.recycle()
        }
    }

    private companion object {
        const val KEY_STATE = "state_b64"
        const val KEY_DEST = "tgt"
        const val KEY_LAST_URL = "last_href"
    }
}
