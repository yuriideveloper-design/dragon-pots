package com.p95ea315e.complete_first_called_neon.feature.garage

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import com.p95ea315e.complete_first_called_neon.core.model.GarageBuilding
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@Composable
fun GarageScreen(
    state: SyndicateState,
    onUpgradeBuilding: (GarageBuilding) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "GARAGE HQ",
            color = NeonColors.NeonCyan,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        // Overall garage level
        GlowCard(glowColor = NeonColors.NeonPurple) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("GARAGE POWER", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp)
                        Text(
                            text = "Level ${state.garageLevel}",
                            color = NeonColors.NeonPurple,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Text("⚙", fontSize = 40.sp)
                }
                Spacer(Modifier.height(8.dp))
                val maxTotal = GarageBuilding.entries.size * 5
                LinearProgressIndicator(
                    progress = { state.garageLevel.toFloat() / maxTotal },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = NeonColors.NeonPurple,
                    trackColor = NeonColors.BorderDefault
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${state.garageLevel} / $maxTotal upgrades total",
                    color = NeonColors.TextMuted,
                    fontSize = 10.sp
                )
            }
        }

        SectionHeader("Buildings", "(${state.garageBuildings.values.count { it > 0 }}/${GarageBuilding.entries.size} upgraded)")

        GarageBuilding.entries.forEach { building ->
            val currentLevel = state.garageBuildings[building.name] ?: 0
            val isMaxed = currentLevel >= 5
            val nextCost = if (!isMaxed) building.baseCost * (currentLevel + 1) else 0L
            val canAfford = state.credits >= nextCost

            BuildingCard(
                building = building,
                currentLevel = currentLevel,
                upgradeCost = nextCost,
                canAfford = canAfford,
                isMaxed = isMaxed,
                onUpgrade = { onUpgradeBuilding(building) }
            )
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun BuildingCard(
    building: GarageBuilding,
    currentLevel: Int,
    upgradeCost: Long,
    canAfford: Boolean,
    isMaxed: Boolean,
    onUpgrade: () -> Unit
) {
    val accentColor = when (building) {
        GarageBuilding.WORKSHOP -> NeonColors.NeonCyan
        GarageBuilding.PAINT_BOOTH -> NeonColors.NeonPink
        GarageBuilding.DYNO_ROOM -> NeonColors.ElectricBlue
        GarageBuilding.TUNING_LAB -> NeonColors.NeonPurple
        GarageBuilding.PARTS_WAREHOUSE -> NeonColors.NeonGold
        GarageBuilding.NITRO_STATION -> NeonColors.NeonGreen
        GarageBuilding.SHOWCASE -> NeonColors.NeonOrange
    }
    val icon = when (building) {
        GarageBuilding.WORKSHOP -> "🔧"
        GarageBuilding.PAINT_BOOTH -> "🎨"
        GarageBuilding.DYNO_ROOM -> "📊"
        GarageBuilding.TUNING_LAB -> "🔬"
        GarageBuilding.PARTS_WAREHOUSE -> "📦"
        GarageBuilding.NITRO_STATION -> "💨"
        GarageBuilding.SHOWCASE -> "🏆"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NeonColors.SurfaceCard)
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(icon, fontSize = 24.sp)
                    Spacer(Modifier.size(10.dp))
                    Column {
                        Text(
                            building.displayName,
                            color = NeonColors.TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            building.description,
                            color = NeonColors.TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    if (isMaxed) {
                        Text(
                            "MAX",
                            color = NeonColors.NeonGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    } else {
                        Text(
                            "Lv $currentLevel / 5",
                            color = accentColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Level indicators
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(5) { i ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (i < currentLevel) accentColor else NeonColors.BorderDefault)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            if (!isMaxed) {
                Button(
                    onClick = onUpgrade,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (canAfford) accentColor.copy(alpha = 0.2f) else NeonColors.SurfaceElevated,
                        disabledContainerColor = NeonColors.SurfaceElevated
                    ),
                    enabled = canAfford
                ) {
                    Text(
                        text = if (canAfford) "UPGRADE  ₿${formatCredits(upgradeCost)}" else "NEED ₿${formatCredits(upgradeCost)}",
                        color = if (canAfford) accentColor else NeonColors.TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(NeonColors.NeonGold.copy(alpha = 0.1f))
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "✓ MAXED OUT",
                        color = NeonColors.NeonGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
