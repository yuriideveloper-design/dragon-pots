package com.p95ea315e.complete_first_called_neon.feature.cars

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p95ea315e.complete_first_called_neon.core.data.MockData
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.Car
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.GlowCard
import com.p95ea315e.complete_first_called_neon.feature.common.NeonCard
import com.p95ea315e.complete_first_called_neon.feature.common.RarityBadge
import com.p95ea315e.complete_first_called_neon.feature.common.SectionHeader
import com.p95ea315e.complete_first_called_neon.feature.common.StatBar
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@Composable
fun CarDetailScreen(
    carId: Int,
    state: SyndicateState,
    onBack: () -> Unit,
    onBuyCar: (Car) -> Unit,
    onSellCar: (Car) -> Unit,
    onSelectCar: (Int) -> Unit
) {
    val car = MockData.cars.find { it.id == carId } ?: run {
        onBack()
        return
    }
    val isOwned = car.id in state.ownedCarIds
    val isSelected = car.id == state.selectedCarId
    val accentColor = Color(car.accentColor)
    val canBuy = state.credits >= car.value
    val sellPrice = (car.value * 0.7).toLong()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header hero
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(accentColor.copy(alpha = 0.15f), NeonColors.Background)
                    )
                )
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = NeonColors.TextPrimary)
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🏎", fontSize = 72.sp)
                Text(
                    car.name,
                    color = NeonColors.TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NeonColors.NeonCyan.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("ACTIVE", color = NeonColors.NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                RarityBadge(car.rarity)
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Car identity
            GlowCard(glowColor = accentColor) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(car.manufacturer.uppercase(), color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 2.sp)
                            Text(car.name, color = NeonColors.TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Black)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(car.category.displayName.uppercase(), color = accentColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text("Level ${car.level}", color = NeonColors.TextMuted, fontSize = 12.sp)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DetailStat("HORSEPOWER", "${car.horsepower} hp", accentColor, Modifier.weight(1f))
                        DetailStat("TOP SPEED", "${car.topSpeed} km/h", accentColor, Modifier.weight(1f))
                        DetailStat("VALUE", "₿${formatCredits(car.value)}", NeonColors.NeonGold, Modifier.weight(1f))
                    }
                }
            }

            // Performance bars
            SectionHeader("Performance")
            NeonCard(borderColor = NeonColors.BorderNeon) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatBar("ACCEL", car.acceleration, color = NeonColors.NeonCyan)
                    StatBar("GRIP", car.grip, color = NeonColors.NeonPink)
                    StatBar("NITRO", car.nitro, color = NeonColors.NeonPurple)
                    StatBar("HANDLING", car.handling, color = NeonColors.ElectricBlue)
                    StatBar("TOP SPD", car.topSpeed, maxValue = 450, color = NeonColors.NeonGold)
                }
            }

            // Action buttons
            if (isOwned) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onSelectCar(car.id) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        enabled = !isSelected,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonColors.NeonCyan.copy(alpha = 0.2f),
                            disabledContainerColor = NeonColors.SurfaceElevated
                        )
                    ) {
                        Text(
                            if (isSelected) "✓ ACTIVE" else "SELECT",
                            color = if (isSelected) NeonColors.TextMuted else NeonColors.NeonCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { onSellCar(car) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonColors.NeonRed.copy(alpha = 0.15f))
                    ) {
                        Text(
                            "SELL ₿${formatCredits(sellPrice)}",
                            color = NeonColors.NeonRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Button(
                    onClick = { onBuyCar(car) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = canBuy,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonColors.NeonGold.copy(alpha = 0.2f),
                        disabledContainerColor = NeonColors.SurfaceElevated
                    )
                ) {
                    Text(
                        if (canBuy) "BUY  ₿${formatCredits(car.value)}" else "NEED ₿${formatCredits(car.value - state.credits)} MORE",
                        color = if (canBuy) NeonColors.NeonGold else NeonColors.TextMuted,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
private fun DetailStat(label: String, value: String, color: Color, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(label, color = NeonColors.TextMuted, fontSize = 9.sp, letterSpacing = 0.5.sp)
        Text(value, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}
