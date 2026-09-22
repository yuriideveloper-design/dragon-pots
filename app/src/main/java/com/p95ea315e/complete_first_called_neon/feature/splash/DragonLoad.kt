package com.p95ea315e.complete_first_called_neon.feature.splash

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p95ea315e.complete_first_called_neon.R
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

private val flapFrames = intArrayOf(
    R.drawable.dragon_flap_up,
    R.drawable.dragon_flap_mid,
    R.drawable.dragon_flap_down,
    R.drawable.dragon_flap_mid,
)

@Composable
fun DragonLoad(modifier: Modifier = Modifier) {
    var step by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (isActive) {
            delay(520)
            step = (step + 1) % flapFrames.size
        }
    }

    val motion = rememberInfiniteTransition(label = "dragon_load")
    val bob by motion.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "bob",
    )
    val drift by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8200, easing = LinearEasing),
        ),
        label = "drift",
    )

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .size(220.dp)
                .offset(y = bob.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            NeonColors.NeonGold.copy(alpha = 0.42f),
                            NeonColors.NeonOrange.copy(alpha = 0.10f),
                            Color.Transparent,
                        ),
                    ),
                ),
        )

        Image(
            painter = painterResource(flapFrames[(step + 2) % flapFrames.size]),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(108.dp)
                .offset(
                    x = (-120 + drift * (maxWidth.value + 80f)).dp,
                    y = 6.dp,
                )
                .graphicsLayer {
                    rotationZ = -12f
                    alpha = 0.92f
                },
        )

        Crossfade(
            targetState = step,
            animationSpec = tween(durationMillis = 380),
            label = "flap",
        ) { frame ->
            Image(
                painter = painterResource(flapFrames[frame]),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(300.dp)
                    .offset(y = (bob * 0.6f).dp)
                    .graphicsLayer { rotationZ = bob * 0.35f },
            )
        }

        repeat(6) { index ->
            val phase = (drift + index * 0.16f) % 1f
            val fade = (phase * (1f - phase) * 4.2f).coerceIn(0f, 1f)
            Image(
                painter = painterResource(R.drawable.dragon_ember),
                contentDescription = null,
                modifier = Modifier
                    .size((16 + (index % 3) * 7).dp)
                    .offset(
                        x = (36 + (index - 2) * 18).dp,
                        y = (28 - phase * 120).dp,
                    )
                    .graphicsLayer {
                        alpha = fade
                        rotationZ = phase * 80f
                    },
            )
        }
    }
}

@Composable
fun DragonLoadingLine() {
    Text(
        text = "Loading",
        color = NeonColors.NeonGold,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 2.sp,
    )
}
