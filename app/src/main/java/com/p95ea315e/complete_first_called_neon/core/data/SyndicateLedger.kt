package com.p95ea315e.complete_first_called_neon.core.data

import android.content.SharedPreferences
import com.p95ea315e.complete_first_called_neon.core.model.GarageBuilding
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import androidx.compose.ui.unit.sp

class SyndicateLedger(private val prefs: SharedPreferences) {

    fun loadState(): SyndicateState {
        val ownedCarIds = loadIntSet("ownedCarIds", setOf(1))
        val hiredCrewIds = loadIntSet("hiredCrewIds", emptySet())
        val completedRaceIds = loadIntSet("completedRaceIds", emptySet())
        val unlockedAchievementIds = loadIntSet("unlockedAchievementIds", emptySet())
        val ownedPartIds = loadIntSet("ownedPartIds", emptySet())
        val garageBuildings = loadBuildingMap()

        return SyndicateState(
            credits = prefs.getLong("credits", 75_000L),
            xp = prefs.getLong("xp", 0L),
            level = prefs.getInt("level", 1),
            reputation = prefs.getInt("reputation", 50),
            totalRaces = prefs.getInt("totalRaces", 0),
            totalWins = prefs.getInt("totalWins", 0),
            totalEarned = prefs.getLong("totalEarned", 0L),
            ownedCarIds = ownedCarIds,
            selectedCarId = prefs.getInt("selectedCarId", 1),
            hiredCrewIds = hiredCrewIds,
            completedRaceIds = completedRaceIds,
            unlockedAchievementIds = unlockedAchievementIds,
            garageBuildings = garageBuildings,
            ownedPartIds = ownedPartIds
        )
    }

    fun saveState(state: SyndicateState) {
        prefs.edit()
            .putLong("credits", state.credits)
            .putLong("xp", state.xp)
            .putInt("level", state.level)
            .putInt("reputation", state.reputation)
            .putInt("totalRaces", state.totalRaces)
            .putInt("totalWins", state.totalWins)
            .putLong("totalEarned", state.totalEarned)
            .putString("ownedCarIds", state.ownedCarIds.joinToString(","))
            .putInt("selectedCarId", state.selectedCarId)
            .putString("hiredCrewIds", state.hiredCrewIds.joinToString(","))
            .putString("completedRaceIds", state.completedRaceIds.joinToString(","))
            .putString("unlockedAchievementIds", state.unlockedAchievementIds.joinToString(","))
            .putString("ownedPartIds", state.ownedPartIds.joinToString(","))
            .putString("garageBuildings", encodeBuildingMap(state.garageBuildings))
            .apply()
    }

    private fun loadIntSet(key: String, default: Set<Int>): Set<Int> {
        val str = prefs.getString(key, null) ?: return default
        if (str.isBlank()) return emptySet()
        return str.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
    }

    private fun loadBuildingMap(): Map<String, Int> {
        val str = prefs.getString("garageBuildings", null)
        val default = GarageBuilding.entries.associate { it.name to 0 }
        if (str.isNullOrBlank()) return default
        val result = default.toMutableMap()
        str.split(";").forEach { entry ->
            val parts = entry.split(":")
            if (parts.size == 2) {
                result[parts[0]] = parts[1].toIntOrNull() ?: 0
            }
        }
        return result
    }

    private fun encodeBuildingMap(map: Map<String, Int>): String =
        map.entries.joinToString(";") { "${it.key}:${it.value}" }
}
