package com.p95ea315e.complete_first_called_neon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SeedPrimary,
    background = SeedBackground,
    surface = SeedSurface,
    onPrimary = Color(0xFF0B1713),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun GeneratedTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
