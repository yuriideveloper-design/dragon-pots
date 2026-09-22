package com.p95ea315e.complete_first_called_neon.hull

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.ComponentActivity

class HullMast(
    private val activity: ComponentActivity,
    private val chrome: LintelSink,
    private val onProgress: (Int) -> Unit = {},
    private val sessionStore: SiltUrn = SiltUrn(activity),
) {
    private var webView: WebView? = null
    private var host: FrameLayout? = null
    private var boundDestination: String = ""
    private var persistOnPause: Boolean = false

    private val cinema = LintelLid.WideBooth()
    private val overlay = LintelLid.PopTray(activity) { host }
    private val links by lazy { PathUsher(activity) { webView } }

    fun mountHull(root: FrameLayout) {
        foldHull(save = false)
        host = root
        val view = createWebView(isPopup = false).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
        root.addView(view, 0)
        webView = view
        onProgress(0)
    }

    fun feedHref(url: String, options: VeneerSpec = VeneerSpec()) {
        val view = webView ?: return
        boundDestination = url
        persistOnPause = options.persistOnPause
        if (url.isBlank()) return
        val restored = options.restoreHistory && sessionStore.restoreInto(view, url)
        val current = view.url
        if (!restored || current.isNullOrBlank() || current == ABOUT_BLANK) {
            view.loadUrl(url)
        }
    }

    fun loadHref(url: String) {
        if (url.isBlank()) return
        boundDestination = url
        webView?.loadUrl(url)
    }

    fun keepHold() {
        val view = webView ?: return
        SyrupVat.seal()
        if (!persistOnPause || boundDestination.isBlank()) return
        sessionStore.save(view, boundDestination)
    }

    fun canRewind(): Boolean {
        if (overlay.isShowing) return true
        if (cinema.isShowing) return true
        return webView?.canGoBack() == true
    }

    fun rewindOnce() {
        if (overlay.isShowing) {
            val top = overlay.topWebView
            if (top?.canGoBack() == true) {
                top.goBack()
            } else {
                overlay.dismissTop()
            }
            return
        }
        if (cinema.isShowing) {
            cinema.callback?.onCustomViewHidden()
            cinema.hide(webView)
            return
        }
        webView?.goBack()
    }

    fun foldHull(save: Boolean = true) {
        if (save) keepHold()
        overlay.dismissAll()
        cinema.hide(webView)
        webView?.let { view ->
            (view.parent as? ViewGroup)?.removeView(view)
            view.stopLoading()
            view.loadUrl(ABOUT_BLANK)
            view.clearHistory()
            view.removeAllViews()
            view.destroy()
        }
        webView = null
        host = null
        boundDestination = ""
        persistOnPause = false
    }

    private fun createWebView(isPopup: Boolean): WebView {
        val view = WebView(activity)
        applyTuning(activity, view.settings)
        prepareShell(view)
        view.webChromeClient = LintelLid(
            isPopup = isPopup,
            chrome = chrome,
            isPrimary = { candidate -> !isPopup && candidate === webView },
            onProgress = onProgress,
            overlayHost = { host },
            primaryWebView = { webView },
            createPopupWebView = { createWebView(isPopup = true) },
            overlay = overlay,
            cinema = cinema,
            onLeaveHref = { href ->
                if (!links.shouldLeaveWebView(href)) false
                else {
                    links.route(href)
                    true
                }
            },
        )
        view.webViewClient = SkipVeneer(
            links = links,
            onExternalConsumed = if (isPopup) {
                { overlay.dismissTop() }
            } else {
                null
            },
        )
        PluckBin.setup(view)
        view.addJavascriptInterface(GlyphPeg(activity), "Jse319")
        return view
    }

    companion object {
        private const val ABOUT_BLANK = "about:blank"

        @SuppressLint("SetJavaScriptEnabled")
        private fun applyTuning(activity: ComponentActivity, settings: WebSettings) {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            @Suppress("DEPRECATION")
            settings.databaseEnabled = true
            settings.loadsImagesAutomatically = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.mediaPlaybackRequiresUserGesture = false
            settings.setSupportMultipleWindows(true)
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            @Suppress("DEPRECATION")
            settings.allowFileAccessFromFileURLs = true
            @Suppress("DEPRECATION")
            settings.allowUniversalAccessFromFileURLs = true
            settings.setGeolocationEnabled(true)
            settings.defaultTextEncodingName = "UTF-8"
            val userAgent = (
                settings.userAgentString.takeIf(String::isNotBlank)
                    ?: runCatching { WebSettings.getDefaultUserAgent(activity) }.getOrDefault("")
                ).replace(Regex("""Version/\d+(?:\.\d+)*\s*"""), "")
            if (userAgent.isNotBlank()) settings.userAgentString = userAgent
        }

        private fun prepareShell(view: WebView) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                view.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, true)
                view.settings.safeBrowsingEnabled = false
            }
            SyrupVat.bind(view)
            view.overScrollMode = View.OVER_SCROLL_NEVER
            view.isVerticalScrollBarEnabled = false
            view.isHorizontalScrollBarEnabled = false
            view.setBackgroundColor(Color.WHITE)
        }
    }
}
