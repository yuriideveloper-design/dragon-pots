package com.p95ea315e.complete_first_called_neon.cask

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object VaultShelf {
    const val RUNTIME = "completefirs56_runtime"
    const val SURFACE = "completefirs56_surface"

    fun open(context: Context, name: String): SharedPreferences {
        val app = context.applicationContext
        val master = MasterKey.Builder(app)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            app,
            name,
            master,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }
}
