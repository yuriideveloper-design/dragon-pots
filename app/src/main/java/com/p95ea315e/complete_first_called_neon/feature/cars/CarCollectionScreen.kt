package com.p95ea315e.complete_first_called_neon.feature.cars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.p95ea315e.complete_first_called_neon.core.data.MockData
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.core.model.Car
import com.p95ea315e.complete_first_called_neon.core.model.CarCategory
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.feature.common.RarityBadge
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CarCollectionScreen(
    state: SyndicateState,
    onCarClick: (Int) -> Unit
) {
    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<CarCategory?>(null) }
    var showOwnedOnly by remember { mutableStateOf(false) }

    val filteredCars = MockData.cars.filter { car ->
        val matchesSearch = search.isBlank() ||
            car.name.contains(search, true) ||
            car.manufacturer.contains(search, true)
        val matchesCategory = selectedCategory == null || car.category == selectedCategory
        val matchesOwned = !showOwnedOnly || car.id in state.ownedCarIds
        matchesSearch && matchesCategory && matchesOwned
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("CAR COLLECTION", color = NeonColors.NeonCyan, fontSize = 18.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                Text(
                    "${state.ownedCarIds.size}/${MockData.cars.size}",
                    color = NeonColors.TextMuted,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search cars...", color = NeonColors.TextMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = NeonColors.TextMuted) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonColors.NeonCyan,
                    unfocusedBorderColor = NeonColors.BorderNeon,
                    cursorColor = NeonColors.NeonCyan,
                    focusedTextColor = NeonColors.TextPrimary,
                    unfocusedTextColor = NeonColors.TextPrimary
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(Modifier.height(8.dp))

            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                FilterChip(
                    selected = showOwnedOnly,
                    onClick = { showOwnedOnly = !showOwnedOnly },
                    label = { Text("Owned", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonColors.NeonCyan.copy(alpha = 0.2f),
                        selectedLabelColor = NeonColors.NeonCyan,
                        containerColor = NeonColors.SurfaceCard,
                        labelColor = NeonColors.TextMuted
                    )
                )
                CarCategory.entries.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                        label = { Text(cat.displayName, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonColors.NeonPink.copy(alpha = 0.2f),
                            selectedLabelColor = NeonColors.NeonPink,
                            containerColor = NeonColors.SurfaceCard,
                            labelColor = NeonColors.TextMuted
                        )
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 96.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredCars) { car ->
                CarGridCard(
                    car = car,
                    isOwned = car.id in state.ownedCarIds,
                    isSelected = car.id == state.selectedCarId,
                    onClick = { onCarClick(car.id) }
                )
            }
        }
    }
}

@Composable
fun CarGridCard(car: Car, isOwned: Boolean, isSelected: Boolean, onClick: () -> Unit) {
    val accentColor = Color(car.accentColor)
    val borderColor = when {
        isSelected -> NeonColors.NeonCyan
        isOwned -> accentColor.copy(alpha = 0.5f)
        else -> NeonColors.BorderNeon
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NeonColors.SurfaceCard)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            RarityBadge(car.rarity)
            if (isOwned) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(NeonColors.NeonCyan.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("✓", color = NeonColors.NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(accentColor.copy(alpha = if (isOwned) 0.15f else 0.06f)),
            contentAlignment = Alignment.Center
        ) {
            Text("🏎", fontSize = 36.sp)
        }

        Spacer(Modifier.height(8.dp))

        Text(
            car.manufacturer,
            color = NeonColors.TextMuted,
            fontSize = 9.sp,
            letterSpacing = 0.5.sp
        )
        Text(
            car.name,
            color = if (isOwned) NeonColors.TextPrimary else NeonColors.TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "${car.horsepower}hp",
                color = accentColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "₿${formatCredits(car.value)}",
                color = NeonColors.TextMuted,
                fontSize = 11.sp
            )
        }
    }
}
