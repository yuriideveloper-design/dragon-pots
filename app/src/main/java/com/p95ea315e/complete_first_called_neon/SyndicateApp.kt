package com.p95ea315e.complete_first_called_neon

import android.app.Application
import android.webkit.CookieManager
import com.p95ea315e.complete_first_called_neon.cask.AshWire
import com.p95ea315e.complete_first_called_neon.cask.FlintJudge
import com.p95ea315e.complete_first_called_neon.cask.StoveBag
import com.p95ea315e.complete_first_called_neon.hull.ChimeHorn
import com.p95ea315e.complete_first_called_neon.pin.RiftClip

class SyndicateApp : Application() {
    val stoveBag: StoveBag by lazy { StoveBag(this) }
    val ashWire: AshWire by lazy { AshWire(this) }
    val riftClip: RiftClip by lazy { RiftClip(this) }
    val flintJudge: FlintJudge by lazy {
        FlintJudge(
            bag = stoveBag,
            hop = ashWire,
            latch = riftClip,
        )
    }

    override fun onCreate() {
        super.onCreate()
        runCatching {
            CookieManager.getInstance().setAcceptCookie(true)
        }
        ChimeHorn.primeBell(this)
        ashWire
    }
}
