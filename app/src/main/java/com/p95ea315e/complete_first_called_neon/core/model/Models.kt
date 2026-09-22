package com.p95ea315e.complete_first_called_neon.core.model

import androidx.compose.ui.graphics.Color

enum class CarCategory(val displayName: String) {
    STREET("Street"),
    JDM("JDM"),
    MUSCLE("Muscle"),
    HYPERCAR("Hypercar"),
    ELECTRIC("Electric"),
    CLASSIC("Classic"),
    DRIFT("Drift"),
    PROTOTYPE("Prototype")
}

enum class GlowRank(val displayName: String, val color: Color) {
    COMMON("Common", Color(0xFF9E9E9E)),
    UNCOMMON("Uncommon", Color(0xFF4CAF50)),
    RARE("Rare", Color(0xFF2196F3)),
    EPIC("Epic", Color(0xFF9C27B0)),
    LEGENDARY("Legendary", Color(0xFFFF9800))
}

enum class RaceType(val displayName: String, val icon: String) {
    SPRINT("Sprint", "⚡"),
    CIRCUIT("Circuit", "🔄"),
    DRIFT("Drift", "🌀"),
    TIME_ATTACK("Time Attack", "⏱"),
    NIGHT_RACE("Night Race", "🌙"),
    HIGHWAY_RUN("Highway Run", "🛣"),
    ELITE_TOURNAMENT("Elite Tournament", "🏆")
}

enum class CrewRole(val displayName: String, val description: String) {
    MECHANIC("Mechanic", "Reduces upgrade costs by 10%"),
    TUNER("Tuner", "Boosts race performance by 8%"),
    HACKER("Hacker", "Reveals hidden race events"),
    SCOUT("Scout", "Finds rare cars in marketplace"),
    DRIVER("Driver", "Adds 12% win chance in races"),
    MANAGER("Manager", "Increases all earnings by 15%")
}

enum class GarageBuilding(val displayName: String, val description: String, val baseCost: Long) {
    WORKSHOP("Workshop", "Enables car upgrades and repairs", 5_000L),
    PAINT_BOOTH("Paint Booth", "Unlocks visual customization", 8_000L),
    DYNO_ROOM("Dyno Room", "Reveals hidden car stats", 12_000L),
    TUNING_LAB("Tuning Lab", "Advanced performance tuning", 20_000L),
    PARTS_WAREHOUSE("Parts Warehouse", "Stores more upgrade parts", 15_000L),
    NITRO_STATION("Nitro Station", "Supercharges nitro capacity", 18_000L),
    SHOWCASE("Showcase", "Display and value your fleet", 10_000L)
}

data class Car(
    val id: Int,
    val name: String,
    val manufacturer: String,
    val category: CarCategory,
    val rarity: GlowRank,
    val horsepower: Int,
    val topSpeed: Int,
    val acceleration: Int,
    val grip: Int,
    val nitro: Int,
    val handling: Int,
    val level: Int,
    val value: Long,
    val accentColor: Long
)

data class RaceEvent(
    val id: Int,
    val name: String,
    val type: RaceType,
    val district: String,
    val cashReward: Long,
    val xpReward: Int,
    val reputationReward: Int,
    val entryFee: Long,
    val requiredReputation: Int,
    val difficulty: Int
)

data class CrewMember(
    val id: Int,
    val name: String,
    val role: CrewRole,
    val level: Int,
    val hiringCost: Long,
    val weeklyWage: Long,
    val bonusMultiplier: Float
)

enum class PartType(val displayName: String) {
    ENGINE("Engine"),
    TURBO("Turbo"),
    SUSPENSION("Suspension"),
    TIRES("Tires"),
    ECU("ECU"),
    TRANSMISSION("Transmission"),
    BRAKES("Brakes"),
    NITROUS("Nitrous"),
    VISUAL("Visual")
}

data class Part(
    val id: Int,
    val name: String,
    val type: PartType,
    val rarity: GlowRank,
    val statBoost: Int,
    val value: Long,
    val description: String
)

data class Achievement(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val category: String,
    val target: Int
)

data class RaceResult(
    val won: Boolean,
    val cashEarned: Long,
    val xpEarned: Int,
    val reputationEarned: Int,
    val message: String
)

data class SyndicateState(
    val credits: Long = 75_000L,
    val xp: Long = 0L,
    val level: Int = 1,
    val reputation: Int = 50,
    val totalRaces: Int = 0,
    val totalWins: Int = 0,
    val totalEarned: Long = 0L,
    val ownedCarIds: Set<Int> = setOf(1),
    val selectedCarId: Int = 1,
    val hiredCrewIds: Set<Int> = emptySet(),
    val completedRaceIds: Set<Int> = emptySet(),
    val unlockedAchievementIds: Set<Int> = emptySet(),
    val garageBuildings: Map<String, Int> = GarageBuilding.entries.associate { it.name to 0 },
    val ownedPartIds: Set<Int> = emptySet()
) {
    val garageLevel: Int get() = garageBuildings.values.sum()
    val winRate: Float get() = if (totalRaces == 0) 0f else totalWins.toFloat() / totalRaces
}
