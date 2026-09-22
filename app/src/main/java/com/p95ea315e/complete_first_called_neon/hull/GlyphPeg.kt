package com.p95ea315e.complete_first_called_neon.hull

import android.content.Context
import android.webkit.JavascriptInterface

@androidx.annotation.Keep
class GlyphPeg(private val context: Context) {
    @androidx.annotation.Keep
    @JavascriptInterface
    fun putd9(b64: String, name: String, mime: String) {
        val dataUrl = "data:${mime.ifBlank { "application/octet-stream" }};base64,$b64"
        PluckBin.saveFromJs(context, dataUrl, mime)
    }

    @androidx.annotation.Keep
    @JavascriptInterface
    fun ping3e(dataUrl: String, mime: String) {
        PluckBin.saveFromJs(context, dataUrl, mime)
    }
}
