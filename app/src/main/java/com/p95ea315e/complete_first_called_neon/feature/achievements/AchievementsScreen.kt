package com.p95ea315e.complete_first_called_neon.feature.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.p95ea315e.complete_first_called_neon.core.data.MockData
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.Achievement
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader

@Composable
fun AchievementsScreen(state: SyndicateState) {
    val unlocked = state.unlockedAchievementIds
    val categories = MockData.achievements.groupBy { it.category }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                "ACHIEVEMENTS",
                color = NeonColors.NeonGold,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(8.dp))
            GlowCard(glowColor = NeonColors.NeonGold) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("UNLOCKED", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp)
                        Text(
                            "${unlocked.size} / ${MockData.achievements.size}",
                            color = NeonColors.NeonGold,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Text("🏆", fontSize = 40.sp)
                }
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { unlocked.size.toFloat() / MockData.achievements.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = NeonColors.NeonGold,
                    trackColor = NeonColors.BorderDefault
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            categories.forEach { (category, achList) ->
                item {
                    SectionHeader(
                        title = category.uppercase(),
                        subtitle = "(${achList.count { it.id in unlocked }}/${achList.size})"
                    )
                }
                items(achList) { achievement ->
                    AchievementCard(
                        achievement = achievement,
                        isUnlocked = achievement.id in unlocked,
                        state = state
                    )
                }
                item { Spacer(Modifier.height(4.dp)) }
            }
        }
    }
}

@Composable
private fun AchievementCard(achievement: Achievement, isUnlocked: Boolean, state: SyndicateState) {
    val color = if (isUnlocked) NeonColors.NeonGold else NeonColors.TextMuted
    val progress = getProgress(achievement, state)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NeonColors.SurfaceCard)
            .border(
                1.dp,
                if (isUnlocked) NeonColors.NeonGold.copy(alpha = 0.4f) else NeonColors.BorderNeon,
                RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        if (isUnlocked) NeonColors.NeonGold.copy(alpha = 0.15f) else NeonColors.SurfaceElevated,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isUnlocked) achievement.icon else "🔒",
                    fontSize = 22.sp
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    achievement.title,
                    color = if (isUnlocked) NeonColors.TextPrimary else NeonColors.TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    achievement.description,
                    color = NeonColors.TextMuted,
                    fontSize = 11.sp
                )
                if (!isUnlocked && progress > 0f) {
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progress.coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = NeonColors.ElectricBlue,
                        trackColor = NeonColors.BorderDefault
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            if (isUnlocked) {
                Text("✓", color = NeonColors.NeonGold, fontSize = 20.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

private fun getProgress(achievement: Achievement, state: SyndicateState): Float {
    return when (achievement.id) {
        1 -> state.totalWins.toFloat() / 1f
        2 -> state.totalWins.toFloat() / 10f
        8 -> state.ownedCarIds.size.toFloat() / 5f
        9 -> state.ownedCarIds.size.toFloat() / 15f
        10 -> state.ownedCarIds.size.toFloat() / 30f
        14 -> state.garageBuildings.values.sum().toFloat() / 1f
        19 -> state.hiredCrewIds.size.toFloat() / 1f
        20 -> state.hiredCrewIds.size.toFloat() / 6f
        21 -> state.hiredCrewIds.size.toFloat() / 12f
        22 -> state.totalEarned.toFloat() / 1_000_000f
        23 -> state.credits.toFloat() / 500_000f
        25 -> state.level.toFloat() / 25f
        else -> 0f
    }
}
