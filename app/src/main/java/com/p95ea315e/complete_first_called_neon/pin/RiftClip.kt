package com.p95ea315e.complete_first_called_neon.pin

import android.content.Context
import com.p95ea315e.complete_first_called_neon.cask.VaultShelf

class RiftClip(
    context: Context,
) {
    private val prefs = VaultShelf.open(context, VaultShelf.SURFACE)

    data class RiftPin(
        val href: String,
        val expiresAtEpochMs: Long,
    )

    fun pinClip(href: String, expiresAtEpochMs: Long) {
        prefs.edit()
            .putString(K_HREF, href)
            .putBoolean(K_HOLD, true)
            .putLong(K_UNTIL, expiresAtEpochMs)
            .apply()
    }

    fun peekClip(nowMs: Long = System.currentTimeMillis()): RiftPin? {
        if (!prefs.getBoolean(K_HOLD, false)) return null
        val href = prefs.getString(K_HREF, null)?.takeIf { GustParse.isHttpsHref(it) } ?: return null
        val until = prefs.getLong(K_UNTIL, 0L)
        if (until in 1..<nowMs) {
            dropClip()
            return null
        }
        return RiftPin(href, until)
    }

    fun sameClip(href: String): Boolean {
        val held = peekClip() ?: return false
        return held.href == href
    }

    fun dropClip() {
        prefs.edit()
            .remove(K_HREF)
            .remove(K_HOLD)
            .remove(K_UNTIL)
            .apply()
    }

    private companion object {
        const val K_HREF = "quay"
        const val K_HOLD = "moor"
        const val K_UNTIL = "span"
    }
}
