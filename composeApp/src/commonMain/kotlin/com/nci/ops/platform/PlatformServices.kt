package com.nci.ops.platform

enum class HapticPulseType {
    LIGHT_TICK,         // Baseline or Comfort cue logged
    DOUBLE_PULSE,       // Stress / Discomfort cue logged
    HEAVY_ALERT         // >= 11 BTE Threshold crossed or Mission Completed
}
