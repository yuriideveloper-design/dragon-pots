package com.p95ea315e.complete_first_called_neon.hull

import android.webkit.CookieManager
import android.webkit.WebView

object SyrupVat {
    fun bind(view: WebView) {
        runCatching {
            val jar = CookieManager.getInstance()
            jar.setAcceptCookie(true)
            jar.setAcceptThirdPartyCookies(view, true)
            jar.flush()
        }
    }

    fun seal() {
        runCatching { CookieManager.getInstance().flush() }
    }
}
