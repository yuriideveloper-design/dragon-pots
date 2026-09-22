package com.p95ea315e.complete_first_called_neon

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.p95ea315e.complete_first_called_neon.cask.SootChalk
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.design.NeonDriftTheme
import com.p95ea315e.complete_first_called_neon.feature.splash.DragonLoad
import com.p95ea315e.complete_first_called_neon.feature.splash.DragonLoadingLine
import com.p95ea315e.complete_first_called_neon.hull.HullMast
import com.p95ea315e.complete_first_called_neon.hull.LintelSink
import com.p95ea315e.complete_first_called_neon.hull.LocalLintel
import com.p95ea315e.complete_first_called_neon.hull.PickUrn
import com.p95ea315e.complete_first_called_neon.hull.SnapBolt
import com.p95ea315e.complete_first_called_neon.hull.VeneerDeck
import com.p95ea315e.complete_first_called_neon.hull.VeneerSpec
import com.p95ea315e.complete_first_called_neon.pin.RiftLane
import kotlinx.coroutines.delay
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

private sealed interface AppPhase {
    data object Loading : AppPhase
    data object FirstScreen : AppPhase
    data class SecondScreen(
        val url: String,
        val restoreHistory: Boolean,
        val persistOnPause: Boolean,
    ) : AppPhase
}

private fun isCaptureMode(context: Context): Boolean {
    val intent = (context as? Activity)?.intent ?: return false
    return intent.getBooleanExtra("asg_screenshot_mode", false) ||
        intent.getBooleanExtra("asg_screen", false)
}

class MainActivity : ComponentActivity(), LintelSink {
    companion object {
        const val EXTRA_FORK = "flint.fork"
        const val FORK_FIRST = "first"
        const val FORK_SECOND = "second"
        const val EXTRA_LEAP = "open_href"
        const val EXTRA_PUSH_LEAP = "flint.push_href"
        const val EXTRA_RESTORE = "flint.restore"
        const val EXTRA_CLIP = "flint.keep"

        @Volatile
        var currentInstance: MainActivity? = null
            private set

        @Volatile
        var activeHull: HullMast? = null

        @Volatile
        var webPaneShowing: Boolean = false

        fun relayHref(context: Context, url: String?): Boolean {
            val host = currentInstance ?: return false
            if (!webPaneShowing) return false
            if (!url.isNullOrBlank()) {
                host.runOnUiThread { activeHull?.loadHref(url) }
            }
            context.startActivity(
                Intent(context, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    if (!url.isNullOrBlank()) putExtra(EXTRA_PUSH_LEAP, url)
                },
            )
            return true
        }
    }

    private val _pushDestinations = MutableSharedFlow<PushOpen>(extraBufferCapacity = 1)
    val pushDestinations: SharedFlow<PushOpen> = _pushDestinations.asSharedFlow()

    data class PushOpen(
        val url: String,
        val restoreHistory: Boolean = false,
        val persistOnPause: Boolean = true,
    )

    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private var pendingFileChooserParams: WebChromeClient.FileChooserParams? = null
    private var cameraOutputUri: Uri? = null
    private var pendingPermissionRequest: PermissionRequest? = null

    private val fileChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            deliverFileChooserResult(result.resultCode, result.data)
        }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            val params = pendingFileChooserParams
            pendingFileChooserParams = null
            if (granted && params != null) {
                openFileChooser(filePathCallback, params, resumeAfterCameraPermission = true)
            } else {
                filePathCallback?.onReceiveValue(null)
                filePathCallback = null
            }
        }

    private val webPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grants ->
            val req = pendingPermissionRequest
            pendingPermissionRequest = null
            if (req == null) return@registerForActivityResult
            if (grants.values.any { it }) {
                req.grant(req.resources)
            } else {
                req.deny()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        currentInstance = this
        enableEdgeToEdge()
        enterImmersiveMode()
        setContent {
            CompositionLocalProvider(LocalLintel provides this@MainActivity) {
                AppEntry()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        absorbHref(intent)
    }

    override fun onResume() {
        super.onResume()
        if (!webPaneShowing) enterImmersiveMode()
        tryDeliverPendingCameraCapture()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && !webPaneShowing) enterImmersiveMode()
    }

    fun leaveImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.show(WindowInsetsCompat.Type.systemBars())
    }

    fun enterImmersiveMode() {
        if (webPaneShowing) return
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onDestroy() {
        if (currentInstance === this) currentInstance = null
        filePathCallback?.onReceiveValue(null)
        filePathCallback = null
        super.onDestroy()
    }

    fun absorbHref(intent: Intent?) {
        if (intent == null) return
        val url = intent.getStringExtra(EXTRA_PUSH_LEAP)
            ?: intent.getStringExtra(EXTRA_LEAP)
            ?: return
        if (url.isBlank()) return
        SootChalk.d("MainActivity push intent url=$url webDeck=$webPaneShowing")
        if (webPaneShowing) {
            runOnUiThread { activeHull?.loadHref(url) }
            return
        }
        val restore = intent.getBooleanExtra(EXTRA_RESTORE, false)
        val retain = intent.getBooleanExtra(EXTRA_CLIP, false)
        _pushDestinations.tryEmit(
            PushOpen(
                url = url,
                restoreHistory = restore,
                persistOnPause = retain || restore,
            ),
        )
    }

    override fun openFileChooser(
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: WebChromeClient.FileChooserParams?,
    ): Boolean = openFileChooser(filePathCallback, fileChooserParams, resumeAfterCameraPermission = false)

    private fun openFileChooser(
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: WebChromeClient.FileChooserParams?,
        resumeAfterCameraPermission: Boolean,
    ): Boolean {
        if (!resumeAfterCameraPermission) {
            if (this.filePathCallback != null) {
                try {
                    this.filePathCallback?.onReceiveValue(null)
                } catch (_: Exception) {
                }
                this.filePathCallback = null
                cameraOutputUri = null
                SnapBolt.clearPersistedCameraUri(this)
            }
            this.filePathCallback = filePathCallback
            val orphan = SnapBolt.loadPersistedCameraUri(this)
            if (orphan != null && SnapBolt.uriHasContent(this, orphan)) {
                cameraOutputUri = orphan
                tryDeliverPendingCameraCapture()
                return true
            }
            cameraOutputUri = null
        }

        return try {
            val acceptTypes = fileChooserParams?.acceptTypes ?: arrayOf()
            val isImageCapture = acceptTypes.any {
                it.startsWith("image/") || it == "image/*" || it.isEmpty()
            }
            val isCaptureEnabled = fileChooserParams?.isCaptureEnabled == true
            val needsCamera = isImageCapture || isCaptureEnabled

            if (needsCamera &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                pendingFileChooserParams = fileChooserParams
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                return true
            }

            val intents = mutableListOf<Intent>()
            if (needsCamera) {
                val holder = arrayOfNulls<Uri>(1)
                val cameraIntent = SnapBolt.buildCameraIntent(this, holder)
                if (cameraIntent != null) {
                    cameraOutputUri = holder[0]
                    intents.add(cameraIntent)
                }
            }

            val contentSelectionIntent = PickUrn.contentPickIntent(
                acceptTypes,
                fileChooserParams?.mode == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE,
            )
            fileChooserLauncher.launch(PickUrn.chooserIntent(contentSelectionIntent, intents))
            true
        } catch (_: Exception) {
            this.filePathCallback?.onReceiveValue(null)
            this.filePathCallback = null
            true
        }
    }

    override fun onPermissionRequest(request: PermissionRequest) {
        val needed = mutableListOf<String>()
        for (res in request.resources) {
            when (res) {
                PermissionRequest.RESOURCE_VIDEO_CAPTURE ->
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        needed.add(Manifest.permission.CAMERA)
                    }
                PermissionRequest.RESOURCE_AUDIO_CAPTURE ->
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        needed.add(Manifest.permission.RECORD_AUDIO)
                    }
            }
        }
        if (needed.isEmpty()) {
            request.grant(request.resources)
            return
        }
        pendingPermissionRequest = request
        webPermissionLauncher.launch(needed.toTypedArray())
    }

    private fun deliverFileChooserResult(resultCode: Int, data: Intent?) {
        if (filePathCallback == null) {
            cameraOutputUri = null
            SnapBolt.clearPersistedCameraUri(this)
            return
        }
        val results = SnapBolt.parseResult(this, resultCode, data, cameraOutputUri)
        if (results === SnapBolt.PENDING_CAMERA) {
            return
        }
        val callback = filePathCallback
        filePathCallback = null
        cameraOutputUri = null
        try {
            callback?.onReceiveValue(results)
        } catch (_: Exception) {
        }
    }

    private fun tryDeliverPendingCameraCapture() {
        if (filePathCallback == null) return
        val uri = cameraOutputUri ?: SnapBolt.loadPersistedCameraUri(this) ?: return
        if (!SnapBolt.uriHasContent(this, uri)) return
        val published = SnapBolt.publishForWebView(this, uri) ?: uri
        val callback = filePathCallback
        filePathCallback = null
        cameraOutputUri = null
        SnapBolt.clearPersistedCameraUri(this)
        try {
            callback?.onReceiveValue(arrayOf(published))
        } catch (_: Exception) {
        }
    }
}

@Composable
private fun AppEntry() {
    val context = LocalContext.current
    val activity = context as? MainActivity
    val judge = (context.applicationContext as SyndicateApp).flintJudge
    val skipGate = remember { isCaptureMode(context) }
    val handedFork = remember {
        val intent = activity?.intent ?: return@remember null
        when (intent.getStringExtra(MainActivity.EXTRA_FORK)) {
            MainActivity.FORK_FIRST -> AppPhase.FirstScreen
            MainActivity.FORK_SECOND -> {
                val url = intent.getStringExtra(MainActivity.EXTRA_LEAP).orEmpty()
                if (url.isBlank()) AppPhase.FirstScreen
                else AppPhase.SecondScreen(
                    url = url,
                    restoreHistory = intent.getBooleanExtra(MainActivity.EXTRA_RESTORE, false),
                    persistOnPause = intent.getBooleanExtra(MainActivity.EXTRA_CLIP, false),
                )
            }
            else -> null
        }
    }
    val coldPush = remember {
        if (handedFork != null) return@remember null
        val intent = activity?.intent ?: return@remember null
        val url = intent.getStringExtra(MainActivity.EXTRA_LEAP)
            ?: intent.getStringExtra(MainActivity.EXTRA_PUSH_LEAP)
        url?.takeIf { it.isNotBlank() }?.let { u ->
            MainActivity.PushOpen(
                url = u,
                restoreHistory = intent.getBooleanExtra(MainActivity.EXTRA_RESTORE, false),
                persistOnPause = intent.getBooleanExtra(MainActivity.EXTRA_CLIP, false) ||
                    intent.getBooleanExtra(MainActivity.EXTRA_RESTORE, false),
            )
        }
    }
    var phase by remember {
        mutableStateOf<AppPhase>(
            when {
                skipGate -> AppPhase.FirstScreen
                handedFork != null -> handedFork
                coldPush != null -> AppPhase.SecondScreen(
                    url = coldPush.url,
                    restoreHistory = coldPush.restoreHistory,
                    persistOnPause = coldPush.persistOnPause,
                )
                else -> AppPhase.Loading
            },
        )
    }
    var gateSuppressed by remember { mutableStateOf(coldPush != null || handedFork != null) }
    var pendingLane by remember { mutableStateOf<RiftLane?>(null) }

    fun applyLane(lane: RiftLane) {
        phase = when (lane) {
            RiftLane.PaleSlab -> AppPhase.FirstScreen
            is RiftLane.OfferGlaze -> AppPhase.SecondScreen(
                url = lane.href,
                restoreHistory = lane.restoreHistory,
                persistOnPause = lane.persistOnPause,
            )
        }
    }

    val notificationPermissionResult = remember { mutableStateOf<CompletableDeferred<Boolean>?>(null) }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        SootChalk.d("POST_NOTIFICATIONS granted=$granted")
        notificationPermissionResult.value?.complete(granted)
        notificationPermissionResult.value = null
    }

    LaunchedEffect(activity) {
        val host = activity ?: return@LaunchedEffect
        host.pushDestinations.collect { open ->
            SootChalk.d("AppEntry pushDestinations → SecondScreen url=${open.url}")
            gateSuppressed = true
            phase = AppPhase.SecondScreen(
                url = open.url,
                restoreHistory = open.restoreHistory,
                persistOnPause = open.persistOnPause,
            )
        }
    }

    LaunchedEffect(skipGate, coldPush, handedFork) {
        if (!skipGate && Build.VERSION.SDK_INT >= 33) {
            val already = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
            if (already) {
                SootChalk.d("POST_NOTIFICATIONS already granted")
            } else {
                SootChalk.d("Requesting POST_NOTIFICATIONS…")
                val deferred = CompletableDeferred<Boolean>()
                notificationPermissionResult.value = deferred
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                withTimeoutOrNull(8_000) { deferred.await() }
            }
        } else if (skipGate) {
            SootChalk.d("AppEntry skipGate=true → skip notify prompt")
        } else {
            SootChalk.d("POST_NOTIFICATIONS not required (API < 33)")
        }

        if (skipGate) {
            SootChalk.d("AppEntry skipGate=true → FirstScreen")
            return@LaunchedEffect
        }
        if (handedFork != null) {
            SootChalk.d("AppEntry handed fork")
            return@LaunchedEffect
        }
        if (coldPush != null) {
            SootChalk.d("AppEntry coldPush → SecondScreen url=${coldPush.url} (skip gate)")
            return@LaunchedEffect
        }

        if (gateSuppressed) {
            SootChalk.d("AppEntry gate suppressed by push — skip sortAnvil result")
            return@LaunchedEffect
        }

        SootChalk.d("AppEntry sortAnvil fallback…")
        val result = runCatching {
            withContext(Dispatchers.IO) {
                withTimeoutOrNull(20_000) { judge.sortAnvil() } ?: RiftLane.PaleSlab
            }
        }
            .onFailure { SootChalk.e("sortAnvil threw → FirstScreen", it) }
            .getOrDefault(RiftLane.PaleSlab)
        if (gateSuppressed) {
            SootChalk.d("AppEntry gate suppressed after sortAnvil — keep push SecondScreen")
            return@LaunchedEffect
        }
        SootChalk.d("AppEntry pendingLane=$result")
        pendingLane = result
        if (result is RiftLane.OfferGlaze) {
            applyLane(result)
        }
    }

    when (val current = phase) {
        AppPhase.Loading -> {
            val lane = pendingLane
            AnvilBootSplash(
                readyToLeave = lane != null,
                onFinished = { if (lane != null) applyLane(lane) },
            )
        }
        AppPhase.FirstScreen -> NeonFirstScreen(skipSplash = !skipGate)
        is AppPhase.SecondScreen -> {
            Box(Modifier.fillMaxSize()) {
                VeneerDeck(
                    href = current.url,
                    options = VeneerSpec(
                        restoreHistory = current.restoreHistory,
                        persistOnPause = current.persistOnPause,
                    ),
                )
            }
        }
    }
}

@Composable
private fun AnvilBootSplash(
    readyToLeave: Boolean,
    onFinished: () -> Unit,
) {
    LaunchedEffect(readyToLeave) {
        if (readyToLeave) {
            delay(400)
            onFinished()
        }
    }
    NeonDriftTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(NeonColors.Background),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DragonLoad(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp),
                )
                DragonLoadingLine()
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Dr\u0430g\u043En",
                    color = NeonColors.NeonCyan,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 6.sp,
                )
                Text(
                    text = "P\u043Ets",
                    color = NeonColors.NeonPink,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 8.sp,
                )
            }
        }
    }
}

@Composable
private fun NeonFirstScreen(skipSplash: Boolean) {
    NeonDriftTheme {
        NeonDriftApp(skipSplash = skipSplash)
    }
}
