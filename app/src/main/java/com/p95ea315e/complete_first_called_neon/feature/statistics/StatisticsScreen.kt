package com.p95ea315e.complete_first_called_neon.feature.statistics

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p95ea315e.complete_first_called_neon.core.data.MockData
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.CarCategory
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@Composable
fun StatisticsScreen(state: SyndicateState) {
    val ownedCars = MockData.cars.filter { it.id in state.ownedCarIds }
    val bestCar = ownedCars.maxByOrNull { it.topSpeed + it.acceleration }
    val favManufacturer = ownedCars
        .groupBy { it.manufacturer }
        .maxByOrNull { it.value.size }
        ?.key ?: "—"
    val winRate = if (state.totalRaces > 0) (state.totalWins * 100f / state.totalRaces).toInt() else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("STATISTICS", color = NeonColors.NeonCyan, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)

        // Hero stats
        GlowCard(glowColor = NeonColors.NeonCyan) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    BigStat("LEVEL", "${state.level}", NeonColors.NeonGold)
                    BigStat("REPUTATION", "${state.reputation}", NeonColors.NeonPurple)
                    BigStat("WIN RATE", "$winRate%", NeonColors.NeonCyan)
                }
            }
        }

        // Racing stats
        SectionHeader("Racing")
        StatGrid(
            listOf(
                StatItem("Total Races", "${state.totalRaces}", NeonColors.NeonPink),
                StatItem("Total Wins", "${state.totalWins}", NeonColors.NeonCyan),
                StatItem("Total Losses", "${state.totalRaces - state.totalWins}", NeonColors.NeonRed),
                StatItem("Events Won", "${state.completedRaceIds.size}", NeonColors.NeonGold),
                StatItem("Win Streak", "--", NeonColors.ElectricBlue),
                StatItem("Best Finish", if (state.totalWins > 0) "1st" else "--", NeonColors.NeonGold)
            )
        )

        // Collection stats
        SectionHeader("Collection")
        StatGrid(
            listOf(
                StatItem("Cars Owned", "${state.ownedCarIds.size}", NeonColors.NeonCyan),
                StatItem("Parts Owned", "${state.ownedPartIds.size}", NeonColors.NeonPurple),
                StatItem("Crew Hired", "${state.hiredCrewIds.size}", NeonColors.NeonPink),
                StatItem("Achievements", "${state.unlockedAchievementIds.size}", NeonColors.NeonGold),
                StatItem("Garage Level", "${state.garageLevel}", NeonColors.NeonGreen),
                StatItem("Buildings Max", "${state.garageBuildings.values.count { it >= 5 }}", NeonColors.ElectricBlue)
            )
        )

        // Economy stats
        SectionHeader("Economy")
        StatGrid(
            listOf(
                StatItem("Credits", "₿${formatCredits(state.credits)}", NeonColors.NeonGold),
                StatItem("Total Earned", "₿${formatCredits(state.totalEarned)}", NeonColors.NeonGold),
                StatItem("XP", "${state.xp}", NeonColors.NeonPurple),
                StatItem("Fleet Value", "₿${formatCredits(ownedCars.sumOf { it.value })}", NeonColors.NeonCyan)
            )
        )

        // Fleet breakdown
        SectionHeader("Fleet by Category")
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            CarCategory.entries.forEach { cat ->
                val count = ownedCars.count { it.category == cat }
                val total = MockData.cars.count { it.category == cat }
                CategoryBar(cat.displayName, count, total)
            }
        }

        // Best vehicles
        SectionHeader("Best Vehicles")
        if (bestCar != null) {
            GlowCard(glowColor = Color(bestCar.accentColor)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("TOP PERFORMER", color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 1.sp)
                        Text(bestCar.name, color = NeonColors.TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Text("${bestCar.topSpeed} km/h  •  ${bestCar.horsepower} hp", color = Color(bestCar.accentColor), fontSize = 12.sp)
                    }
                    Text("🏎", fontSize = 40.sp)
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoRow("Favorite Manufacturer", favManufacturer)
            InfoRow("Total XP", "${state.xp}")
            InfoRow("Races Per Win", if (state.totalWins > 0) "%.1f".format(state.totalRaces.toFloat() / state.totalWins) else "--")
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun BigStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontSize = 22.sp, fontWeight = FontWeight.Black)
        Text(label.uppercase(), color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 0.5.sp)
    }
}

data class StatItem(val label: String, val value: String, val color: Color)

@Composable
private fun StatGrid(items: List<StatItem>) {
    val rows = items.chunked(2)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { item ->
                    StatCard(item.label, item.value, item.color, Modifier.weight(1f))
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, color: Color, modifier: Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(NeonColors.SurfaceCard)
            .padding(12.dp)
    ) {
        Text(label.uppercase(), color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 0.5.sp)
        Spacer(Modifier.height(4.dp))
        Text(value, color = color, fontSize = 16.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun CategoryBar(name: String, count: Int, total: Int) {
    val fraction = if (total > 0) count.toFloat() / total else 0f
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, color = NeonColors.TextSecondary, fontSize = 12.sp, modifier = Modifier.width(80.dp))
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(NeonColors.BorderDefault)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(NeonColors.NeonCyan)
            )
        }
        Spacer(Modifier.width(8.dp))
        Text("$count/$total", color = NeonColors.TextMuted, fontSize = 11.sp, modifier = Modifier.width(36.dp))
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(NeonColors.SurfaceCard)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = NeonColors.TextSecondary, fontSize = 13.sp)
        Text(value, color = NeonColors.TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}
