package com.p95ea315e.complete_first_called_neon.feature.races

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.p95ea315e.complete_first_called_neon.core.data.MockData
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.core.model.RaceEvent
import com.p95ea315e.complete_first_called_neon.core.model.RaceResult
import com.p95ea315e.complete_first_called_neon.core.model.RaceType
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@Composable
fun RaceEventsScreen(
    state: SyndicateState,
    lastResult: RaceResult?,
    onRace: (RaceEvent) -> Unit,
    onDismissResult: () -> Unit
) {
    var selectedType by remember { mutableStateOf<RaceType?>(null) }

    val filtered = MockData.raceEvents.filter { event ->
        selectedType == null || event.type == selectedType
    }

    if (lastResult != null) {
        RaceResultDialog(result = lastResult, onDismiss = onDismissResult)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                "RACE EVENTS",
                color = NeonColors.NeonPink,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "${state.completedRaceIds.size} / ${MockData.raceEvents.size} completed",
                color = NeonColors.TextMuted,
                fontSize = 12.sp
            )
        }

        // Type filter chips
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                TypeChip(
                    label = "ALL",
                    icon = "🏁",
                    selected = selectedType == null,
                    color = NeonColors.NeonCyan,
                    onClick = { selectedType = null }
                )
            }
            items(RaceType.entries) { type ->
                TypeChip(
                    label = type.displayName.uppercase(),
                    icon = type.icon,
                    selected = selectedType == type,
                    color = raceTypeColor(type),
                    onClick = { selectedType = if (selectedType == type) null else type }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filtered) { event ->
                RaceEventCard(
                    event = event,
                    state = state,
                    onRace = { onRace(event) }
                )
            }
        }
    }
}

@Composable
private fun TypeChip(label: String, icon: String, selected: Boolean, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) color.copy(alpha = 0.25f) else NeonColors.SurfaceCard)
            .border(1.dp, if (selected) color else NeonColors.BorderNeon, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 12.sp)
            Spacer(Modifier.width(4.dp))
            Text(label, color = if (selected) color else NeonColors.TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun RaceEventCard(event: RaceEvent, state: SyndicateState, onRace: () -> Unit) {
    val isCompleted = event.id in state.completedRaceIds
    val canAfford = state.credits >= event.entryFee
    val meetsRep = state.reputation >= event.requiredReputation
    val color = raceTypeColor(event.type)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NeonColors.SurfaceCard)
            .border(1.dp, if (isCompleted) color.copy(alpha = 0.3f) else NeonColors.BorderNeon, RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(event.type.icon, fontSize = 16.sp)
                        Spacer(Modifier.width(6.dp))
                        Text(
                            event.type.displayName.uppercase(),
                            color = color,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(event.name, color = NeonColors.TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(event.district, color = NeonColors.TextMuted, fontSize = 11.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    if (isCompleted) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(color.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("✓ WON", color = color, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    repeat(event.difficulty.coerceAtMost(5)) {
                        Text("●", color = color, fontSize = 8.sp)
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RewardChip("₿ ${formatCredits(event.cashReward)}", "REWARD", NeonColors.NeonGold)
                RewardChip("+${event.xpReward} XP", "XP", NeonColors.NeonPurple)
                RewardChip("+${event.reputationReward} REP", "REP", NeonColors.ElectricBlue)
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Entry: ₿${formatCredits(event.entryFee)}",
                        color = if (canAfford) NeonColors.TextMuted else NeonColors.NeonRed,
                        fontSize = 11.sp
                    )
                    if (event.requiredReputation > 0) {
                        Text(
                            "Rep: ${event.requiredReputation} req.",
                            color = if (meetsRep) NeonColors.TextMuted else NeonColors.NeonRed,
                            fontSize = 11.sp
                        )
                    }
                }
                Button(
                    onClick = onRace,
                    modifier = Modifier.height(38.dp),
                    enabled = canAfford && meetsRep,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color.copy(alpha = 0.2f),
                        disabledContainerColor = NeonColors.SurfaceElevated
                    )
                ) {
                    Text(
                        if (!canAfford) "NO FUNDS" else if (!meetsRep) "LOW REP" else "RACE ▶",
                        color = if (canAfford && meetsRep) color else NeonColors.TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun RewardChip(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(label, color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 0.5.sp)
    }
}

@Composable
fun RaceResultDialog(result: RaceResult, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        GlowCard(
            glowColor = if (result.won) NeonColors.NeonCyan else NeonColors.NeonPink,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (result.won) "VICTORY!" else "DEFEAT",
                    color = if (result.won) NeonColors.NeonCyan else NeonColors.NeonPink,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp
                )
                Text(
                    text = if (result.won) "🏆" else "💀",
                    fontSize = 52.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(result.message, color = NeonColors.TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                if (result.won) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ResultStat("₿ ${formatCredits(result.cashEarned)}", "EARNED", NeonColors.NeonGold)
                        ResultStat("+${result.xpEarned}", "XP", NeonColors.NeonPurple)
                        ResultStat("+${result.reputationEarned}", "REP", NeonColors.ElectricBlue)
                    }
                    Spacer(Modifier.height(16.dp))
                }
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (result.won) NeonColors.NeonCyan.copy(alpha = 0.2f) else NeonColors.NeonPink.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        "CONTINUE",
                        color = if (result.won) NeonColors.NeonCyan else NeonColors.NeonPink,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultStat(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontSize = 16.sp, fontWeight = FontWeight.Black)
        Text(label, color = NeonColors.TextMuted, fontSize = 10.sp)
    }
}

private fun raceTypeColor(type: RaceType): Color = when (type) {
    RaceType.SPRINT -> NeonColors.NeonCyan
    RaceType.CIRCUIT -> NeonColors.ElectricBlue
    RaceType.DRIFT -> NeonColors.NeonPurple
    RaceType.TIME_ATTACK -> NeonColors.NeonGold
    RaceType.NIGHT_RACE -> NeonColors.NeonPink
    RaceType.HIGHWAY_RUN -> NeonColors.NeonGreen
    RaceType.ELITE_TOURNAMENT -> NeonColors.NeonOrange
}
