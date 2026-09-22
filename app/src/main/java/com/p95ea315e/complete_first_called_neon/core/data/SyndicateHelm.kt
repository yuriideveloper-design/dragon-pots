package com.p95ea315e.complete_first_called_neon.core.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.p95ea315e.complete_first_called_neon.core.model.Car
import com.p95ea315e.complete_first_called_neon.core.model.CrewRole
import com.p95ea315e.complete_first_called_neon.core.model.GarageBuilding
import com.p95ea315e.complete_first_called_neon.core.model.SyndicateState
import com.p95ea315e.complete_first_called_neon.core.model.RaceEvent
import com.p95ea315e.complete_first_called_neon.core.model.RaceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class SyndicateHelm(app: Application) : AndroidViewModel(app) {

    private val prefs = app.getSharedPreferences("neon_drift_game", Context.MODE_PRIVATE)
    private val repository = SyndicateLedger(prefs)

    private val _state = MutableStateFlow(repository.loadState())
    val state: StateFlow<SyndicateState> = _state.asStateFlow()

    private val _lastRaceResult = MutableStateFlow<RaceResult?>(null)
    val lastRaceResult: StateFlow<RaceResult?> = _lastRaceResult.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _marketplaceTransactions = MutableStateFlow(0)
    val marketplaceTransactions: StateFlow<Int> = _marketplaceTransactions.asStateFlow()

    fun selectCar(carId: Int) {
        val current = _state.value
        if (carId in current.ownedCarIds) {
            save(current.copy(selectedCarId = carId))
        }
    }

    fun buyCar(car: Car) {
        val current = _state.value
        if (car.id in current.ownedCarIds) {
            showToast("Already own this car")
            return
        }
        if (current.credits < car.value) {
            showToast("Not enough credits")
            return
        }
        val newState = current.copy(
            credits = current.credits - car.value,
            ownedCarIds = current.ownedCarIds + car.id
        )
        save(checkAchievements(newState))
        showToast("${car.name} added to garage!")
        _marketplaceTransactions.value++
    }

    fun sellCar(car: Car) {
        val current = _state.value
        if (car.id !in current.ownedCarIds) return
        if (current.ownedCarIds.size <= 1) {
            showToast("Keep at least one car")
            return
        }
        val sellPrice = (car.value * 0.7).toLong()
        var newState = current.copy(
            credits = current.credits + sellPrice,
            totalEarned = current.totalEarned + sellPrice,
            ownedCarIds = current.ownedCarIds - car.id
        )
        if (newState.selectedCarId == car.id) {
            newState = newState.copy(selectedCarId = newState.ownedCarIds.first())
        }
        save(checkAchievements(newState))
        showToast("Sold for ${formatCredits(sellPrice)}")
        _marketplaceTransactions.value++
    }

    fun race(event: RaceEvent) {
        val current = _state.value
        if (current.credits < event.entryFee) {
            showToast("Not enough credits for entry fee")
            return
        }
        if (current.reputation < event.requiredReputation) {
            showToast("Need ${event.requiredReputation} reputation")
            return
        }

        val bestCar = getBestCarForRace(current)
        val crewBonus = getCrewRaceBonus(current)
        val managerBonus = getManagerBonus(current)
        val winChance = calculateWinChance(bestCar, crewBonus, event.difficulty)
        val won = Random.nextFloat() < winChance

        val cashEarned = if (won) (event.cashReward * managerBonus).toLong() else (event.entryFee * 0.2).toLong()
        val xpEarned = if (won) event.xpReward else event.xpReward / 4
        val repEarned = if (won) event.reputationReward else -5

        val newCredits = current.credits - event.entryFee + if (won) cashEarned else 0L
        val newXp = current.xp + xpEarned
        val newLevel = calculateLevel(newXp)
        val newRep = (current.reputation + repEarned).coerceAtLeast(0)

        val newState = current.copy(
            credits = newCredits.coerceAtLeast(0L),
            xp = newXp,
            level = newLevel,
            reputation = newRep,
            totalRaces = current.totalRaces + 1,
            totalWins = current.totalWins + if (won) 1 else 0,
            totalEarned = current.totalEarned + if (won) cashEarned else 0L,
            completedRaceIds = if (won) current.completedRaceIds + event.id else current.completedRaceIds
        )
        save(checkAchievements(newState))

        val msg = if (won) "Victory! +${formatCredits(cashEarned)}" else "Defeat! Keep training."
        _lastRaceResult.value = RaceResult(won, cashEarned, xpEarned, repEarned.coerceAtLeast(0), msg)
    }

    fun dismissRaceResult() {
        _lastRaceResult.value = null
    }

    fun upgradeBuilding(building: GarageBuilding) {
        val current = _state.value
        val currentLevel = current.garageBuildings[building.name] ?: 0
        if (currentLevel >= 5) {
            showToast("Already at max level")
            return
        }
        val cost = building.baseCost * (currentLevel + 1)
        val mechBonus = getMechanicBonus(current)
        val discountedCost = (cost * mechBonus).toLong()
        if (current.credits < discountedCost) {
            showToast("Need ${formatCredits(discountedCost)}")
            return
        }
        val newBuildings = current.garageBuildings.toMutableMap()
        newBuildings[building.name] = currentLevel + 1
        val newState = current.copy(
            credits = current.credits - discountedCost,
            garageBuildings = newBuildings
        )
        save(checkAchievements(newState))
        showToast("${building.displayName} upgraded to level ${currentLevel + 1}")
    }

    fun hireCrew(crewId: Int) {
        val current = _state.value
        if (crewId in current.hiredCrewIds) {
            showToast("Already hired")
            return
        }
        val member = MockData.crewMembers.find { it.id == crewId } ?: return
        if (current.credits < member.hiringCost) {
            showToast("Need ${formatCredits(member.hiringCost)}")
            return
        }
        val newState = current.copy(
            credits = current.credits - member.hiringCost,
            hiredCrewIds = current.hiredCrewIds + crewId
        )
        save(checkAchievements(newState))
        showToast("${member.name} joined the crew!")
    }

    fun fireCrew(crewId: Int) {
        val current = _state.value
        val member = MockData.crewMembers.find { it.id == crewId } ?: return
        save(current.copy(hiredCrewIds = current.hiredCrewIds - crewId))
        showToast("${member.name} left the crew")
    }

    fun buyPart(partId: Int) {
        val current = _state.value
        val part = MockData.parts.find { it.id == partId } ?: return
        if (partId in current.ownedPartIds) {
            showToast("Already have this part")
            return
        }
        if (current.credits < part.value) {
            showToast("Not enough credits")
            return
        }
        val newState = current.copy(
            credits = current.credits - part.value,
            ownedPartIds = current.ownedPartIds + partId
        )
        save(checkAchievements(newState))
        showToast("${part.name} acquired!")
        _marketplaceTransactions.value++
    }

    fun sellPart(partId: Int) {
        val current = _state.value
        val part = MockData.parts.find { it.id == partId } ?: return
        val sellPrice = (part.value * 0.6).toLong()
        save(current.copy(
            credits = current.credits + sellPrice,
            totalEarned = current.totalEarned + sellPrice,
            ownedPartIds = current.ownedPartIds - partId
        ))
        showToast("Sold for ${formatCredits(sellPrice)}")
        _marketplaceTransactions.value++
    }

    fun resetProgress() {
        val fresh = SyndicateState()
        save(fresh)
        _marketplaceTransactions.value = 0
        showToast("Progress reset")
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    private fun save(state: SyndicateState) {
        _state.value = state
        repository.saveState(state)
    }

    private fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    private fun getBestCarForRace(state: SyndicateState): Car? {
        return MockData.cars
            .filter { it.id in state.ownedCarIds }
            .maxByOrNull { it.topSpeed + it.acceleration + it.handling }
    }

    private fun getCrewRaceBonus(state: SyndicateState): Float {
        return MockData.crewMembers
            .filter { it.id in state.hiredCrewIds && it.role == CrewRole.DRIVER }
            .sumOf { (it.bonusMultiplier - 1.0).toDouble() }.toFloat()
    }

    private fun getManagerBonus(state: SyndicateState): Float {
        return MockData.crewMembers
            .filter { it.id in state.hiredCrewIds && it.role == CrewRole.MANAGER }
            .fold(1.0f) { acc, member -> acc * member.bonusMultiplier }
    }

    private fun getMechanicBonus(state: SyndicateState): Float {
        val discount = MockData.crewMembers
            .filter { it.id in state.hiredCrewIds && it.role == CrewRole.MECHANIC }
            .sumOf { 0.05 }.toFloat()
        return (1.0f - discount).coerceAtLeast(0.5f)
    }

    private fun calculateWinChance(car: Car?, crewBonus: Float, difficulty: Int): Float {
        val carScore = car?.let {
            (it.topSpeed + it.acceleration + it.handling + it.grip) / 4f / 100f
        } ?: 0.3f
        val base = (carScore * 0.7f + crewBonus).coerceIn(0.15f, 0.95f)
        val difficultyPenalty = difficulty * 0.04f
        return (base - difficultyPenalty).coerceIn(0.1f, 0.92f)
    }

    private fun calculateLevel(xp: Long): Int {
        var level = 1
        var threshold = 1000L
        var totalXp = xp
        while (totalXp >= threshold && level < 50) {
            totalXp -= threshold
            level++
            threshold = (threshold * 1.2).toLong()
        }
        return level
    }

    private fun checkAchievements(state: SyndicateState): SyndicateState {
        val unlocked = state.unlockedAchievementIds.toMutableSet()
        val cars = MockData.cars

        // Racing achievements
        if (state.totalWins >= 1) unlocked.add(1)
        if (state.totalWins >= 10) unlocked.add(2)
        val nightWins = MockData.raceEvents.count { it.id in state.completedRaceIds && it.type.name == "NIGHT_RACE" }
        if (nightWins >= 25) unlocked.add(3)
        val circuitWins = MockData.raceEvents.count { it.id in state.completedRaceIds && it.type.name == "CIRCUIT" }
        if (circuitWins >= 20) unlocked.add(4)
        val driftWins = MockData.raceEvents.count { it.id in state.completedRaceIds && it.type.name == "DRIFT" }
        if (driftWins >= 15) unlocked.add(5)
        val hwWins = MockData.raceEvents.count { it.id in state.completedRaceIds && it.type.name == "HIGHWAY_RUN" }
        if (hwWins >= 10) unlocked.add(6)
        val eliteWins = MockData.raceEvents.count { it.id in state.completedRaceIds && it.type.name == "ELITE_TOURNAMENT" }
        if (eliteWins >= 1) unlocked.add(7)

        // Collection achievements
        if (state.ownedCarIds.size >= 5) unlocked.add(8)
        if (state.ownedCarIds.size >= 15) unlocked.add(9)
        if (state.ownedCarIds.size >= 30) unlocked.add(10)
        val jdmOwned = cars.count { it.id in state.ownedCarIds && it.category.name == "JDM" }
        if (jdmOwned >= 10) unlocked.add(11)
        val hyperOwned = cars.count { it.id in state.ownedCarIds && it.category.name == "HYPERCAR" }
        if (hyperOwned >= 5) unlocked.add(12)
        val legendaryOwned = cars.count { it.id in state.ownedCarIds && it.rarity.name == "LEGENDARY" }
        if (legendaryOwned >= 3) unlocked.add(13)

        // Garage achievements
        val totalUpgrades = state.garageBuildings.values.sum()
        if (totalUpgrades >= 1) unlocked.add(14)
        if ((state.garageBuildings["WORKSHOP"] ?: 0) >= 5) unlocked.add(15)
        if (state.garageBuildings.values.all { it >= 3 }) unlocked.add(16)
        if (state.garageBuildings.values.all { it >= 5 }) unlocked.add(17)
        if (state.ownedPartIds.size >= 20) unlocked.add(18)

        // Crew achievements
        if (state.hiredCrewIds.isNotEmpty()) unlocked.add(19)
        if (state.hiredCrewIds.size >= 6) unlocked.add(20)
        if (state.hiredCrewIds.size >= 12) unlocked.add(21)

        // Economy achievements
        if (state.totalEarned >= 1_000_000L) unlocked.add(22)
        if (state.credits >= 500_000L) unlocked.add(23)
        val marketActivity = state.ownedPartIds.size + (state.ownedCarIds.size - 1).coerceAtLeast(0)
        if (marketActivity >= 10) unlocked.add(24)
        if (state.level >= 25) unlocked.add(25)

        return state.copy(unlockedAchievementIds = unlocked)
    }

    private fun formatCredits(amount: Long): String {
        return when {
            amount >= 1_000_000 -> "%.1fM".format(amount / 1_000_000.0)
            amount >= 1_000 -> "%.1fK".format(amount / 1_000.0)
            else -> amount.toString()
        }
    }
}
