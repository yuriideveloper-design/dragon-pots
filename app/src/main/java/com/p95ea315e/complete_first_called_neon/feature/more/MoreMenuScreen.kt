package com.p95ea315e.complete_first_called_neon.feature.more

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors

data class MenuEntry(
    val icon: String,
    val title: String,
    val subtitle: String,
    val route: String,
    val color: Color
)

@Composable
fun MoreMenuScreen(onNavigateTo: (String) -> Unit) {
    val entries = listOf(
        MenuEntry("🔧", "Garage", "Upgrade buildings & facilities", "garage", NeonColors.NeonPurple),
        MenuEntry("👥", "Crew", "Recruit your racing team", "crew", NeonColors.NeonPink),
        MenuEntry("🛒", "Marketplace", "Buy and sell cars & parts", "marketplace", NeonColors.NeonGold),
        MenuEntry("📊", "Statistics", "Your racing performance", "statistics", NeonColors.NeonCyan),
        MenuEntry("⚙", "Settings", "Game preferences & reset", "settings", NeonColors.ElectricBlue)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "MORE",
            color = NeonColors.ElectricBlue,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 3.sp
        )
        Text(
            "Expand your syndicate empire",
            color = NeonColors.TextMuted,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(8.dp))

        entries.forEach { entry ->
            MenuCard(entry = entry, onClick = { onNavigateTo(entry.route) })
        }
    }
}

@Composable
private fun MenuCard(entry: MenuEntry, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NeonColors.SurfaceCard)
            .border(1.dp, entry.color.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(entry.color.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(entry.icon, fontSize = 26.sp)
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                entry.title.uppercase(),
                color = NeonColors.TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            Text(
                entry.subtitle,
                color = NeonColors.TextMuted,
                fontSize = 12.sp
            )
        }
        Text("›", color = entry.color, fontSize = 22.sp, fontWeight = FontWeight.Light)
    }
}
