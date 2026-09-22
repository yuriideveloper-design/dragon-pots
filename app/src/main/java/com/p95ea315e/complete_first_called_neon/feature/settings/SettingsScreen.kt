package com.p95ea315e.complete_first_called_neon.feature.settings

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader

@Composable
fun SettingsScreen(
    state: SyndicateState,
    onResetProgress: () -> Unit
) {
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var neonEffectsEnabled by remember { mutableStateOf(true) }
    var particlesEnabled by remember { mutableStateOf(true) }
    var showResetConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("SETTINGS", color = NeonColors.ElectricBlue, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)

        // Player info
        GlowCard(glowColor = NeonColors.ElectricBlue) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("PLAYER PROFILE", color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 1.5.sp)
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    ProfileStat("Level", "${state.level}")
                    ProfileStat("Reputation", "${state.reputation}")
                    ProfileStat("Cars", "${state.ownedCarIds.size}")
                    ProfileStat("Wins", "${state.totalWins}")
                }
            }
        }

        SectionHeader("Sound & Haptics")

        ToggleRow("Sound Effects", soundEnabled, "Engine, collision, UI sounds") { soundEnabled = it }
        ToggleRow("Vibration", vibrationEnabled, "Haptic feedback on actions") { vibrationEnabled = it }

        SectionHeader("Visual Effects")

        ToggleRow("Neon Glow Effects", neonEffectsEnabled, "Neon borders and lighting") { neonEffectsEnabled = it }
        ToggleRow("Particles", particlesEnabled, "Animated background particles") { particlesEnabled = it }

        SectionHeader("Game Info")

        InfoCard("Version", "1.0")
        InfoCard("Mode", "Offline Only")
        InfoCard("Data", "Stored Locally")
        InfoCard("Total Cars", "65 Vehicles")

        Spacer(Modifier.height(8.dp))

        SectionHeader("Danger Zone")

        if (showResetConfirm) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(NeonColors.NeonRed.copy(alpha = 0.1f))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "⚠ Reset all progress?",
                    color = NeonColors.NeonRed,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "This will delete all cars, crew, upgrades and credits. This action cannot be undone.",
                    color = NeonColors.TextSecondary,
                    fontSize = 13.sp
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            onResetProgress()
                            showResetConfirm = false
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonColors.NeonRed.copy(alpha = 0.25f))
                    ) {
                        Text("RESET", color = NeonColors.NeonRed, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { showResetConfirm = false },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonColors.SurfaceElevated)
                    ) {
                        Text("CANCEL", color = NeonColors.TextSecondary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            Button(
                onClick = { showResetConfirm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonColors.NeonRed.copy(alpha = 0.12f))
            ) {
                Text("RESET ALL PROGRESS", color = NeonColors.NeonRed, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun ToggleRow(title: String, enabled: Boolean, subtitle: String, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(NeonColors.SurfaceCard)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = NeonColors.TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, color = NeonColors.TextMuted, fontSize = 11.sp)
        }
        Switch(
            checked = enabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = NeonColors.Background,
                checkedTrackColor = NeonColors.ElectricBlue,
                uncheckedTrackColor = NeonColors.SurfaceElevated
            )
        )
    }
}

@Composable
private fun InfoCard(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(NeonColors.SurfaceCard)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = NeonColors.TextSecondary, fontSize = 13.sp)
        Text(value, color = NeonColors.TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = NeonColors.ElectricBlue, fontSize = 18.sp, fontWeight = FontWeight.Black)
        Text(label.uppercase(), color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 0.5.sp)
    }
}
