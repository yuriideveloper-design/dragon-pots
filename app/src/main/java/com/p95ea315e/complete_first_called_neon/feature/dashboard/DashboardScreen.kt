package com.p95ea315e.complete_first_called_neon.feature.dashboard

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p95ea315e.complete_first_called_neon.core.data.MockData
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.Car
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.NeonCard
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@Composable
fun DashboardScreen(
    state: SyndicateState,
    onNavigateToRaces: () -> Unit,
    onNavigateToGarage: () -> Unit,
    onNavigateToCars: () -> Unit
) {
    val selectedCar = MockData.cars.find { it.id == state.selectedCarId }
    val xpToNext = calculateXpToNext(state.level)
    val xpProgress = (state.xp % xpToNext.toLong()).toFloat() / xpToNext

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Drаgоn",
                    color = NeonColors.NeonCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Pоts",
                    color = NeonColors.NeonPink,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "⭐ LEVEL ${state.level}",
                    color = NeonColors.NeonGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "REP ${state.reputation}",
                    color = NeonColors.NeonPurple,
                    fontSize = 11.sp
                )
            }
        }

        // Credits + XP bar
        GlowCard(glowColor = NeonColors.NeonCyan) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("CREDITS", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp)
                        Text(
                            text = "₿ ${formatCredits(state.credits)}",
                            color = NeonColors.NeonCyan,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("WINS / RACES", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp)
                        Text(
                            text = "${state.totalWins} / ${state.totalRaces}",
                            color = NeonColors.NeonPink,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("XP ${state.xp % xpToNext}/$xpToNext", color = NeonColors.TextMuted, fontSize = 10.sp)
                    Text("NEXT LV ${state.level + 1}", color = NeonColors.TextMuted, fontSize = 10.sp)
                }
                Spacer(Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { xpProgress.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = NeonColors.NeonPurple,
                    trackColor = NeonColors.BorderDefault
                )
            }
        }

        // Featured car
        SectionHeader("Featured Vehicle")
        if (selectedCar != null) {
            FeaturedCarCard(car = selectedCar)
        }

        // Quick stats
        SectionHeader("Syndicate Stats")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickStatCard(
                icon = Icons.Filled.DirectionsCar,
                label = "Cars",
                value = "${state.ownedCarIds.size}",
                color = NeonColors.NeonCyan,
                modifier = Modifier.weight(1f)
            )
            QuickStatCard(
                icon = Icons.Filled.EmojiEvents,
                label = "Wins",
                value = "${state.totalWins}",
                color = NeonColors.NeonGold,
                modifier = Modifier.weight(1f)
            )
            QuickStatCard(
                icon = Icons.Filled.Speed,
                label = "Crew",
                value = "${state.hiredCrewIds.size}",
                color = NeonColors.NeonPink,
                modifier = Modifier.weight(1f)
            )
        }

        // Quick actions
        SectionHeader("Quick Actions")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionButton(
                label = "RACE NOW",
                color = NeonColors.NeonPink,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToRaces
            )
            QuickActionButton(
                label = "GARAGE",
                color = NeonColors.NeonCyan,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToGarage
            )
            QuickActionButton(
                label = "CARS",
                color = NeonColors.NeonPurple,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToCars
            )
        }

        // Win rate card
        val winPct = if (state.totalRaces > 0) (state.totalWins * 100f / state.totalRaces).toInt() else 0
        NeonCard(borderColor = if (winPct > 50) NeonColors.BorderCyan else NeonColors.BorderNeon) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("WIN RATE", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp)
                    Text(
                        text = "$winPct%",
                        color = if (winPct >= 50) NeonColors.NeonCyan else NeonColors.NeonPink,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("TOTAL EARNED", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp)
                    Text(
                        text = "₿ ${formatCredits(state.totalEarned)}",
                        color = NeonColors.NeonGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun FeaturedCarCard(car: Car) {
    val accentColor = Color(car.accentColor)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(NeonColors.SurfaceElevated, accentColor.copy(alpha = 0.15f))
                )
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        car.manufacturer.uppercase(),
                        color = NeonColors.TextMuted,
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )
                    Text(
                        car.name,
                        color = NeonColors.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        car.category.displayName.uppercase(),
                        color = accentColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(accentColor.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🏎", fontSize = 28.sp)
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CarStat("HP", "${car.horsepower}", accentColor)
                CarStat("TOP", "${car.topSpeed}km/h", accentColor)
                CarStat("ACC", "${car.acceleration}", accentColor)
                CarStat("LVL", "${car.level}", accentColor)
            }
        }
    }
}

@Composable
private fun CarStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 0.5.sp)
        Text(value, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun QuickStatCard(icon: ImageVector, label: String, value: String, color: Color, modifier: Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(NeonColors.SurfaceCard)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
        Spacer(Modifier.height(4.dp))
        Text(value, color = NeonColors.TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Black)
        Text(label.uppercase(), color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 0.5.sp)
    }
}

@Composable
private fun QuickActionButton(label: String, color: Color, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color.copy(alpha = 0.15f)),
    ) {
        Text(label, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
    }
}

private fun calculateXpToNext(level: Int): Int {
    var threshold = 1000
    repeat(level - 1) { threshold = (threshold * 1.2).toInt() }
    return threshold
}
