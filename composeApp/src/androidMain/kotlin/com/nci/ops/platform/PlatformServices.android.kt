package com.nci.ops.platform

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PlatformServices {
    private var appContext: Context? = null
    private var activeActivity: Activity? = null

    fun init(context: Context) {
        appContext = context.applicationContext
        if (context is Activity) {
            activeActivity = context
        }
    }

    fun setActivity(activity: Activity?) {
        activeActivity = activity
    }

    fun currentEpochMs(): Long = System.currentTimeMillis()

    fun currentDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun setKeepScreenAwake(enable: Boolean) {
        try {
            val act = activeActivity ?: return
            act.runOnUiThread {
                if (enable) {
                    act.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                } else {
                    act.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        } catch (_: Throwable) {
        }
    }

    fun triggerHaptic(type: HapticPulseType) {
        val ctx = appContext ?: return
        try {
            val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = ctx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                ctx.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            if (vibrator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = when (type) {
                    HapticPulseType.LIGHT_TICK ->
                        VibrationEffect.createOneShot(25L, VibrationEffect.DEFAULT_AMPLITUDE)
                    HapticPulseType.DOUBLE_PULSE ->
                        VibrationEffect.createWaveform(longArrayOf(0L, 35L, 55L, 45L), -1)
                    HapticPulseType.HEAVY_ALERT ->
                        VibrationEffect.createWaveform(longArrayOf(0L, 90L, 50L, 120L), -1)
                }
                vibrator.vibrate(effect)
            }
        } catch (_: Throwable) {
        }
    }

    fun saveString(key: String, value: String) {
        val prefs = appContext?.getSharedPreferences("nci_ops_prefs", Context.MODE_PRIVATE) ?: return
        prefs.edit().putString(key, value).apply()
    }

    fun loadString(key: String): String? {
        val prefs = appContext?.getSharedPreferences("nci_ops_prefs", Context.MODE_PRIVATE) ?: return null
        return prefs.getString(key, null)
    }
}
