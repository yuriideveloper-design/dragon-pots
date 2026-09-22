package com.p95ea315e.complete_first_called_neon.feature.crew

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.p95ea315e.complete_first_called_neon.core.model.CrewMember
import com.p95ea315e.complete_first_called_neon.core.model.CrewRole
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@Composable
fun CrewScreen(
    state: SyndicateState,
    onHire: (Int) -> Unit,
    onFire: (Int) -> Unit
) {
    val hiredCrew = MockData.crewMembers.filter { it.id in state.hiredCrewIds }
    val availableCrew = MockData.crewMembers.filter { it.id !in state.hiredCrewIds }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text("CREW", color = NeonColors.NeonPink, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
            Spacer(Modifier.height(4.dp))
            Text("${hiredCrew.size} / ${MockData.crewMembers.size} recruited", color = NeonColors.TextMuted, fontSize = 12.sp)
        }

        // Active crew bonuses
        if (hiredCrew.isNotEmpty()) {
            GlowCard(
                glowColor = NeonColors.NeonPink,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("ACTIVE BONUSES", color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 1.5.sp)
                    Spacer(Modifier.height(6.dp))
                    CrewRole.entries.forEach { role ->
                        val count = hiredCrew.count { it.role == role }
                        if (count > 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "${roleIcon(role)} ${role.displayName} x$count",
                                    color = roleColor(role),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    role.description,
                                    color = NeonColors.TextMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (hiredCrew.isNotEmpty()) {
                item { SectionHeader("My Crew", "(${hiredCrew.size} active)") }
                items(hiredCrew) { member ->
                    CrewCard(
                        member = member,
                        isHired = true,
                        canAfford = true,
                        onAction = { onFire(member.id) }
                    )
                }
                item { Spacer(Modifier.height(4.dp)) }
            }

            item { SectionHeader("Available Crew", "(${availableCrew.size} recruitable)") }
            items(availableCrew) { member ->
                CrewCard(
                    member = member,
                    isHired = false,
                    canAfford = state.credits >= member.hiringCost,
                    onAction = { onHire(member.id) }
                )
            }
        }
    }
}

@Composable
private fun CrewCard(
    member: CrewMember,
    isHired: Boolean,
    canAfford: Boolean,
    onAction: () -> Unit
) {
    val color = roleColor(member.role)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NeonColors.SurfaceCard)
            .border(1.dp, if (isHired) color.copy(alpha = 0.4f) else NeonColors.BorderNeon, RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(roleIcon(member.role), fontSize = 22.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(member.name, color = NeonColors.TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    if (isHired) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(color.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                        ) {
                            Text("HIRED", color = color, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Text(
                    "${member.role.displayName}  •  Lv ${member.level}",
                    color = color,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "+${((member.bonusMultiplier - 1f) * 100).toInt()}% bonus",
                    color = NeonColors.TextMuted,
                    fontSize = 11.sp
                )
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                if (isHired) {
                    Button(
                        onClick = onAction,
                        modifier = Modifier.height(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonColors.NeonRed.copy(alpha = 0.15f))
                    ) {
                        Text("FIRE", color = NeonColors.NeonRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text(
                        "₿${formatCredits(member.hiringCost)}",
                        color = if (canAfford) NeonColors.NeonGold else NeonColors.NeonRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Button(
                        onClick = onAction,
                        modifier = Modifier.height(36.dp),
                        enabled = canAfford,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = color.copy(alpha = 0.2f),
                            disabledContainerColor = NeonColors.SurfaceElevated
                        )
                    ) {
                        Text(
                            if (canAfford) "HIRE" else "BROKE",
                            color = if (canAfford) color else NeonColors.TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun roleIcon(role: CrewRole): String = when (role) {
    CrewRole.MECHANIC -> "🔧"
    CrewRole.TUNER -> "⚙"
    CrewRole.HACKER -> "💻"
    CrewRole.SCOUT -> "👁"
    CrewRole.DRIVER -> "🏎"
    CrewRole.MANAGER -> "📋"
}

private fun roleColor(role: CrewRole): Color = when (role) {
    CrewRole.MECHANIC -> NeonColors.NeonCyan
    CrewRole.TUNER -> NeonColors.ElectricBlue
    CrewRole.HACKER -> NeonColors.NeonPurple
    CrewRole.SCOUT -> NeonColors.NeonGreen
    CrewRole.DRIVER -> NeonColors.NeonPink
    CrewRole.MANAGER -> NeonColors.NeonGold
}
