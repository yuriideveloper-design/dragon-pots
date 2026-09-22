package com.p95ea315e.complete_first_called_neon.cask

import com.p95ea315e.complete_first_called_neon.pin.GustParse
import com.p95ea315e.complete_first_called_neon.pin.RiftClip
import com.p95ea315e.complete_first_called_neon.pin.RiftLane
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import org.json.JSONObject

class FlintJudge(
    private val bag: StoveBag,
    private val hop: AshWire,
    private val latch: RiftClip,
) {
    suspend fun sortAnvil(minWarmupMs: Long = WARMUP_MS): RiftLane = withContext(Dispatchers.IO) {
        val started = System.currentTimeMillis()
        RuneCask.wakeRoster()
        val sticky = latch.peekClip()
        if (sticky != null) {
            SootChalk.i("sticky already held")
            waitAnvil(started, minWarmupMs)
            return@withContext RiftLane.OfferGlaze(
                href = sticky.href,
                restoreHistory = true,
                persistOnPause = true,
            )
        }
        val marked = bag.packBlips()
        val raw = withTimeoutOrNull(14_000) { hopOnce(marked) }
        val fork = decide(raw)
        waitAnvil(started, minWarmupMs)
        fork
    }

    private suspend fun hopOnce(marked: JSONObject): String? {
        val result = hop.castRush(marked) ?: return null
        val (status, text) = result
        if (status !in 200..299) return null
        return text
    }

    private fun decide(raw: String?): RiftLane {
        if (raw == null) {
            return latch.peekClip()?.let {
                RiftLane.OfferGlaze(it.href, restoreHistory = true, persistOnPause = true)
            } ?: RiftLane.PaleSlab
        }
        return GustParse.parseGust(raw, latch)
    }

    private suspend fun waitAnvil(started: Long, minMs: Long) {
        val elapsed = System.currentTimeMillis() - started
        if (elapsed < minMs) delay((minMs - elapsed).milliseconds)
    }

    companion object {
        const val WARMUP_MS = 2152L
    }
}
