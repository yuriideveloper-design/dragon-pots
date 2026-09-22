package com.p95ea315e.complete_first_called_neon.core.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val NeonColorScheme = darkColorScheme(
    primary = NeonColors.NeonCyan,
    onPrimary = Color(0xFF000000),
    secondary = NeonColors.NeonPink,
    onSecondary = Color(0xFF000000),
    tertiary = NeonColors.NeonPurple,
    background = NeonColors.Background,
    surface = NeonColors.SurfaceCard,
    onBackground = NeonColors.TextPrimary,
    onSurface = NeonColors.TextPrimary,
    error = NeonColors.NeonRed,
    outline = NeonColors.BorderNeon
)

@Composable
fun NeonDriftTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NeonColorScheme,
        typography = MaterialTheme.typography.copy(
            displayLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Black,
                fontSize = 36.sp,
                color = NeonColors.TextPrimary
            ),
            headlineLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = NeonColors.TextPrimary
            ),
            headlineMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = NeonColors.TextPrimary
            ),
            titleLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                color = NeonColors.TextPrimary
            ),
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = NeonColors.TextSecondary
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = NeonColors.TextSecondary
            ),
            labelSmall = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = NeonColors.TextMuted
            )
        ),
        content = content
    )
}
