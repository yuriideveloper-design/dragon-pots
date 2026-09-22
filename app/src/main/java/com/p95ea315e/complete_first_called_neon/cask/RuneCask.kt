package com.p95ea315e.complete_first_called_neon.cask

internal class RuneCask private constructor() {
    companion object {
        init {
            System.loadLibrary("runekelt")
        }

        private val bag: Map<String, String> by lazy { nativeRoster() }

        fun wakeRoster() {
            bag.size
        }

        fun unwind(id: String): String {
            return bag[id] ?: throw IllegalStateException("rune slot missing")
        }

        @JvmStatic
        external fun nativeRoster(): Map<String, String>
    }
}
