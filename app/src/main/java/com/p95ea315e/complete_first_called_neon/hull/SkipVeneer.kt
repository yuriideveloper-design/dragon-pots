package com.p95ea315e.complete_first_called_neon.hull

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri

class SkipVeneer(
    private val links: PathUsher,
    private val onExternalConsumed: (() -> Unit)? = null,
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?,
    ): Boolean {
        val uri = request?.url ?: return false
        return handleNavigation(uri)
    }

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url.isNullOrBlank()) return false
        return handleNavigation(url.toUri())
    }

    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?,
    ) {
        handler?.proceed()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        view?.evaluateJavascript(PAGE_POLYFILLS, null)
        view?.evaluateJavascript(STEALTH_JS, null)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        SyrupVat.seal()
        view?.let { PluckBin.injectDownloadInterceptor(it) }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?,
    ) {
    }

    private fun handleNavigation(uri: Uri): Boolean {
        if (!links.shouldLeaveWebView(uri)) return false
        links.route(uri)
        onExternalConsumed?.invoke()
        return true
    }

    private companion object {
        private val PAGE_POLYFILLS = """
            (function() {
                if (!Array.prototype.at) {
                    Array.prototype.at = function(i) {
                        i = Math.trunc(i) || 0;
                        if (i < 0) i += this.length;
                        return (i < 0 || i >= this.length) ? undefined : this[i];
                    };
                }
                if (!String.prototype.at) {
                    String.prototype.at = function(i) {
                        i = Math.trunc(i) || 0;
                        if (i < 0) i += this.length;
                        return (i < 0 || i >= this.length) ? undefined : this[i];
                    };
                }
            })();
        """.trimIndent()

        private val STEALTH_JS = """
            (function(){
              try {
                Object.defineProperty(navigator, 'webdriver', {
                  get: function() { return undefined; }
                });
              } catch (e) {}
            })();
        """.trimIndent()
    }
}
