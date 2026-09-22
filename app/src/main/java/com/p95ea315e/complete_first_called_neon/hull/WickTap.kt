package com.p95ea315e.complete_first_called_neon.hull

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.p95ea315e.complete_first_called_neon.MainActivity
import com.p95ea315e.complete_first_called_neon.pin.GustParse
import com.p95ea315e.complete_first_called_neon.pin.RiftClip
import com.p95ea315e.complete_first_called_neon.cask.SootChalk
import com.p95ea315e.complete_first_called_neon.cask.AshWire
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class WickTap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pushId = intent.getStringExtra(ChimeHorn.NOTE_KEY)
        if (!pushId.isNullOrBlank()) {
            CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                runCatching {
                    AshWire.from(this@WickTap).reportPulse(pushId, opened = true)
                }.onFailure { SootChalk.w("WickTap opened report failed", it) }
            }
        }

        val href = intent.getStringExtra(ChimeHorn.NAV_HREF)?.takeIf { GustParse.isHttpsHref(it) }
        SootChalk.d("WickTap tap")

        if (MainActivity.relayHref(this, href)) {
            finish()
            return
        }

        when {
            !href.isNullOrBlank() -> {
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .putExtra(MainActivity.EXTRA_LEAP, href)
                        .addFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP,
                        ),
                )
            }
            else -> {
                val sticky = RiftClip(this).peekClip()
                if (sticky != null) {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                            .putExtra(MainActivity.EXTRA_LEAP, sticky.href)
                            .putExtra(MainActivity.EXTRA_RESTORE, true)
                            .putExtra(MainActivity.EXTRA_CLIP, true)
                            .addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP,
                            ),
                    )
                }
            }
        }
        finish()
    }
}
