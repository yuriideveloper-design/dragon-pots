package com.p95ea315e.complete_first_called_neon.cask

import android.util.Log
import com.p95ea315e.complete_first_called_neon.BuildConfig

internal object SootChalk {
    private const val TAG = "SootChalk"

    fun i(message: String) {
        Log.i(TAG, message)
    }

    fun d(message: String) {
        if (BuildConfig.DEBUG) Log.d(TAG, message)
    }

    fun w(message: String, error: Throwable? = null) {
        if (!BuildConfig.DEBUG) return
        if (error != null) Log.w(TAG, message, error) else Log.w(TAG, message)
    }

    fun e(message: String, error: Throwable? = null) {
        if (!BuildConfig.DEBUG) return
        if (error != null) Log.e(TAG, message, error) else Log.e(TAG, message)
    }
}
