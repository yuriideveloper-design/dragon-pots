package com.p95ea315e.complete_first_called_neon.hull

import android.net.Uri
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import androidx.compose.runtime.staticCompositionLocalOf

interface LintelSink {
    fun openFileChooser(
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: WebChromeClient.FileChooserParams?,
    ): Boolean

    fun onPermissionRequest(request: PermissionRequest)
}

val LocalLintel = staticCompositionLocalOf<LintelSink> {
    error("LintelSink not provided")
}
