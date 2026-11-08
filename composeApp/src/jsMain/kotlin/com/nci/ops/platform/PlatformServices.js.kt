package com.nci.ops.platform

import kotlinx.browser.localStorage
import kotlinx.browser.window

actual object PlatformServices {

    actual fun triggerHaptic(type: HapticPulseType) {
        try {
            val nav = window.navigator.asDynamic()
            if (nav.vibrate != undefined) {
                when (type) {
                    HapticPulseType.LIGHT_TICK -> nav.vibrate(25)
                    HapticPulseType.DOUBLE_PULSE -> nav.vibrate(arrayOf(35, 50, 45))
                    HapticPulseType.HEAVY_ALERT -> nav.vibrate(arrayOf(90, 50, 120))
                }
            }
        } catch (_: Throwable) {
        }
    }

    actual fun saveString(key: String, value: String) {
        try {
            localStorage.setItem(key, value)
        } catch (_: Throwable) {
        }
    }

    actual fun loadString(key: String): String? {
        return try {
            localStorage.getItem(key)
        } catch (_: Throwable) {
            null
        }
    }
}
