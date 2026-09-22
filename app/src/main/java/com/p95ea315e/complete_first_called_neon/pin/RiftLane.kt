package com.p95ea315e.complete_first_called_neon.pin

sealed interface RiftLane {
    data object PaleSlab : RiftLane

    data class OfferGlaze(
        val href: String,
        val restoreHistory: Boolean = false,
        val persistOnPause: Boolean = false,
    ) : RiftLane
}
