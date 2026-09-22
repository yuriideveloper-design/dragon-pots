package com.p95ea315e.complete_first_called_neon.cask

import android.content.Context
import android.os.Build
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.Tasks
import com.google.firebase.messaging.FirebaseMessaging
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import org.json.JSONObject

class StoveBag(
    private val context: Context,
) {
    suspend fun packBlips(): JSONObject = withContext(Dispatchers.IO) {
        val app = context.applicationContext
        val store = VaultShelf.open(app, RUNTIME_FILE)
        val prior = runCatching {
            JSONObject(store.getString(PACK, null) ?: "{}")
        }.getOrDefault(JSONObject())
        val cachedUid = prior.optString(H_UID).trim()
        val locale = Locale.getDefault()
        val pInfo = runCatching { app.packageManager.getPackageInfo(app.packageName, 0) }.getOrNull()
        val versionName = pInfo?.versionName ?: "0"
        val versionCode = if (Build.VERSION.SDK_INT >= 28) {
            pInfo?.longVersionCode ?: 0L
        } else {
            @Suppress("DEPRECATION")
            pInfo?.versionCode?.toLong() ?: 0L
        }
        val adv = withTimeoutOrNull(4_000L.milliseconds) { readAdv(app) }.orEmpty()
        val installId = pickHeldUid(cachedUid, adv)
        val ref = withTimeoutOrNull(4_000L.milliseconds) { readRef(app) }.orEmpty()
        val token = readTok(app, prior.optString(H_TOK))
        JSONObject().apply {
            put(H_UID, installId)
            put(H_ADV, adv)
            put(H_REF, ref)
            put(H_MDL, listOf(Build.MANUFACTURER, Build.MODEL).joinToString(" ").trim().ifBlank { "unknown" })
            put(H_OSV, "Android ${Build.VERSION.RELEASE}")
            put(H_SDK, Build.VERSION.SDK_INT)
            put(H_LOC, locale.toLanguageTag())
            put(H_TZN, TimeZone.getDefault().id)
            put(H_AVN, versionName)
            put(H_BLD, versionCode.toString())
            if (token.isNotBlank()) put(H_TOK, token)
            put(H_LNG, locale.language)
            put(H_CTY, locale.country)
        }.also { persist(store, it) }
    }

    fun cacheTok(token: String) {
        if (token.isBlank()) return
        val store = VaultShelf.open(context.applicationContext, RUNTIME_FILE)
        val json = runCatching {
            JSONObject(store.getString(PACK, null) ?: "{}")
        }.getOrDefault(JSONObject())
        json.put(H_TOK, token)
        persist(store, json)
    }

    private fun persist(store: android.content.SharedPreferences, json: JSONObject) {
        runCatching { store.edit().putString(PACK, json.toString()).apply() }
    }

    private fun readTok(app: Context, cached: String): String {
        val manCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(app)
        if (manCode != ConnectionResult.SUCCESS) return cached
        val fresh = runCatching {
            Tasks.await(FirebaseMessaging.getInstance().token, 12_000L, TimeUnit.MILLISECONDS)
        }.onFailure { SootChalk.w("token fetch failed: ${it.message}") }.getOrNull().orEmpty()
        val token = fresh.ifBlank { cached }
        if (token.isNotBlank()) cacheTok(token)
        return token
    }

    private suspend fun readAdv(app: Context): String = withContext(Dispatchers.IO) {
        runCatching {
            val info = AdvertisingIdClient.getAdvertisingIdInfo(app)
            if (info.isLimitAdTrackingEnabled) "" else info.id.orEmpty()
        }.getOrDefault("")
    }

    private suspend fun readRef(app: Context): String =
        suspendCancellableCoroutine { cont ->
            val client = InstallReferrerClient.newBuilder(app).build()
            cont.invokeOnCancellation { runCatching { client.endConnection() } }
            try {
                client.startConnection(object : InstallReferrerStateListener {
                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        val value =
                            if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                                runCatching { client.installReferrer.installReferrer }.getOrDefault("")
                            } else {
                                ""
                            }
                        runCatching { client.endConnection() }
                        if (cont.isActive) cont.resume(value)
                    }

                    override fun onInstallReferrerServiceDisconnected() {
                        if (cont.isActive) cont.resume("")
                    }
                })
            } catch (_: Exception) {
                if (cont.isActive) cont.resume("")
            }
        }

    companion object {
        const val RUNTIME_FILE = VaultShelf.RUNTIME
        private const val PACK = "pack"
        const val H_UID = "ridff31"
        const val H_ADV = "adv3118"
        const val H_REF = "iref18af"
        const val H_MDL = "mdlaf81"
        const val H_OSV = "osv819a"
        const val H_SDK = "sdk9a9f"
        const val H_LOC = "loc9f43"
        const val H_TZN = "tzn4366"
        const val H_AVN = "avn6663"
        const val H_BLD = "bld63e3"
        const val H_TOK = "ptke319"
        const val H_LNG = "lng19d9"
        const val H_CTY = "ctyd93e"
        private const val ZERO_GAID = "00000000-0000-0000-0000-000000000000"

        fun pickHeldUid(cached: String, gaid: String): String {
            if (cached.isNotBlank()) return cached
            if (isUsableGaid(gaid)) return gaid
            return UUID.randomUUID().toString()
        }

        fun isUsableGaid(gaid: String): Boolean =
            gaid.isNotBlank() && !gaid.equals(ZERO_GAID, ignoreCase = true)
    }
}
