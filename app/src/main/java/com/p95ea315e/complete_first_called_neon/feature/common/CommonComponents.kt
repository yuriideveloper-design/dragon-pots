package com.p95ea315e.complete_first_called_neon.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.GlowRank

@Composable
fun NeonCard(
    modifier: Modifier = Modifier,
    borderColor: Color = NeonColors.BorderNeon,
    cornerRadius: Dp = 12.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(NeonColors.SurfaceCard)
            .border(1.dp, borderColor, RoundedCornerShape(cornerRadius))
            .padding(16.dp),
        content = content
    )
}

@Composable
fun GlowCard(
    modifier: Modifier = Modifier,
    glowColor: Color = NeonColors.NeonCyan,
    cornerRadius: Dp = 12.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                Brush.verticalGradient(
                    listOf(
                        NeonColors.SurfaceElevated,
                        NeonColors.SurfaceCard
                    )
                )
            )
            .border(
                1.dp,
                Brush.linearGradient(listOf(glowColor.copy(alpha = 0.8f), glowColor.copy(alpha = 0.2f))),
                RoundedCornerShape(cornerRadius)
            )
            .padding(16.dp),
        content = content
    )
}

@Composable
fun RarityBadge(rarity: GlowRank) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(rarity.color.copy(alpha = 0.2f))
            .border(0.5.dp, rarity.color, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rarity.displayName.uppercase(),
            color = rarity.color,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
fun StatBar(
    label: String,
    value: Int,
    maxValue: Int = 100,
    color: Color = NeonColors.NeonCyan,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = NeonColors.TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(72.dp)
        )
        Spacer(Modifier.width(8.dp))
        LinearProgressIndicator(
            progress = { value.toFloat() / maxValue },
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = NeonColors.BorderDefault
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value.toString(),
            color = NeonColors.TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(28.dp)
        )
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(20.dp)
                .background(NeonColors.NeonCyan, RoundedCornerShape(2.dp))
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = title.uppercase(),
            color = NeonColors.NeonCyan,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
        if (subtitle != null) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = subtitle,
                color = NeonColors.TextMuted,
                fontSize = 11.sp
            )
        }
    }
}

fun formatCredits(amount: Long): String = when {
    amount >= 1_000_000 -> "%.1fM".format(amount / 1_000_000.0)
    amount >= 1_000 -> "%.1fK".format(amount / 1_000.0)
    else -> amount.toString()
}
