package com.p95ea315e.complete_first_called_neon.feature.marketplace

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.core.model.Part
import com.p95ea315e.complete_first_called_neon.feature.common.RarityBadge
import com.p95ea315e.complete_first_called_neon.feature.common.formatCredits

@Composable
fun MarketplaceScreen(
    state: SyndicateState,
    onBuyCar: (Car) -> Unit,
    onSellCar: (Car) -> Unit,
    onBuyPart: (Int) -> Unit,
    onSellPart: (Int) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonColors.Background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text("MARKETPLACE", color = NeonColors.NeonGold, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search marketplace...", color = NeonColors.TextMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = NeonColors.TextMuted) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonColors.NeonGold,
                    unfocusedBorderColor = NeonColors.BorderNeon,
                    cursorColor = NeonColors.NeonGold,
                    focusedTextColor = NeonColors.TextPrimary,
                    unfocusedTextColor = NeonColors.TextPrimary
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.padding(horizontal = 16.dp),
            containerColor = NeonColors.SurfaceCard,
            contentColor = NeonColors.NeonGold
        ) {
            listOf("Cars", "Parts").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            title.uppercase(),
                            color = if (selectedTab == index) NeonColors.NeonGold else NeonColors.TextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        when (selectedTab) {
            0 -> CarsTab(state = state, search = search, onBuyCar = onBuyCar, onSellCar = onSellCar)
            1 -> PartsTab(state = state, search = search, onBuyPart = onBuyPart, onSellPart = onSellPart)
        }
    }
}

@Composable
private fun CarsTab(state: SyndicateState, search: String, onBuyCar: (Car) -> Unit, onSellCar: (Car) -> Unit) {
    val unowned = MockData.cars.filter {
        it.id !in state.ownedCarIds &&
            (search.isBlank() || it.name.contains(search, true) || it.manufacturer.contains(search, true))
    }
    val owned = MockData.cars.filter {
        it.id in state.ownedCarIds && state.ownedCarIds.size > 1 &&
            (search.isBlank() || it.name.contains(search, true) || it.manufacturer.contains(search, true))
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (owned.isNotEmpty()) {
            item {
                Text("YOUR CARS FOR SALE", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
            }
            items(owned) { car ->
                MarketplaceCarRow(
                    car = car,
                    isOwned = true,
                    canAfford = false,
                    price = (car.value * 0.7).toLong(),
                    onAction = { onSellCar(car) }
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
        if (unowned.isNotEmpty()) {
            item {
                Text("AVAILABLE TO BUY", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
            }
            items(unowned) { car ->
                MarketplaceCarRow(
                    car = car,
                    isOwned = false,
                    canAfford = state.credits >= car.value,
                    price = car.value,
                    onAction = { onBuyCar(car) }
                )
            }
        }
    }
}

@Composable
private fun MarketplaceCarRow(
    car: Car,
    isOwned: Boolean,
    canAfford: Boolean,
    price: Long,
    onAction: () -> Unit
) {
    val accentColor = Color(car.accentColor)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(NeonColors.SurfaceCard)
            .border(1.dp, NeonColors.BorderNeon, RoundedCornerShape(10.dp))
            .clickable(onClick = onAction)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("🏎", fontSize = 26.sp)
        }
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(car.name, color = NeonColors.TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(car.manufacturer, color = NeonColors.TextMuted, fontSize = 10.sp)
                Spacer(Modifier.width(6.dp))
                RarityBadge(car.rarity)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                "₿${formatCredits(price)}",
                color = if (isOwned) NeonColors.NeonGold else if (canAfford) NeonColors.NeonGreen else NeonColors.NeonRed,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                if (isOwned) "SELL" else if (canAfford) "BUY" else "COSTLY",
                color = if (isOwned) NeonColors.NeonGold else if (canAfford) NeonColors.NeonGreen else NeonColors.NeonRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PartsTab(state: SyndicateState, search: String, onBuyPart: (Int) -> Unit, onSellPart: (Int) -> Unit) {
    val unowned = MockData.parts.filter {
        it.id !in state.ownedPartIds &&
            (search.isBlank() || it.name.contains(search, true) || it.type.displayName.contains(search, true))
    }
    val owned = MockData.parts.filter {
        it.id in state.ownedPartIds &&
            (search.isBlank() || it.name.contains(search, true))
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (owned.isNotEmpty()) {
            item { Text("YOUR PARTS", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold) }
            items(owned) { part ->
                PartRow(part = part, isOwned = true, canAfford = false, onAction = { onSellPart(part.id) })
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
        if (unowned.isNotEmpty()) {
            item { Text("AVAILABLE PARTS", color = NeonColors.TextMuted, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold) }
            items(unowned) { part ->
                PartRow(part = part, isOwned = false, canAfford = state.credits >= part.value, onAction = { onBuyPart(part.id) })
            }
        }
    }
}

@Composable
private fun PartRow(part: Part, isOwned: Boolean, canAfford: Boolean, onAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(NeonColors.SurfaceCard)
            .border(1.dp, NeonColors.BorderNeon, RoundedCornerShape(10.dp))
            .clickable(onClick = onAction)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(NeonColors.NeonPurple.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(partIcon(part.type.displayName), fontSize = 20.sp)
        }
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(part.name, color = NeonColors.TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(part.type.displayName, color = NeonColors.TextMuted, fontSize = 10.sp)
                if (part.statBoost > 0) {
                    Spacer(Modifier.width(6.dp))
                    Text("+${part.statBoost} BOOST", color = NeonColors.NeonGreen, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            val price = if (isOwned) (part.value * 0.6).toLong() else part.value
            Text(
                "₿${formatCredits(price)}",
                color = if (isOwned) NeonColors.NeonGold else if (canAfford) NeonColors.NeonGreen else NeonColors.NeonRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            RarityBadge(part.rarity)
        }
    }
}

private fun partIcon(type: String): String = when (type) {
    "Engine" -> "🔥"
    "Turbo" -> "💨"
    "Suspension" -> "🔩"
    "Tires" -> "⚙"
    "ECU" -> "💻"
    "Transmission" -> "⚡"
    "Brakes" -> "🛑"
    "Nitrous" -> "💥"
    else -> "✨"
}
