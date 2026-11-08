package com.nci.ops.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TacticalObsidian = Color(0xFF0A0E14)
val TacticalSurface = Color(0xFF121822)
val TacticalCard = Color(0xFF18202C)
val TacticalBorder = Color(0xFF263244)

val CyanPrimary = Color(0xFF00E5FF)
val AmberAlert = Color(0xFFFFB300)
val CrimsonThreshold = Color(0xFFFF5252)
val EmeraldComfort = Color(0xFF00E676)
val PurpleEllipsis = Color(0xFFB388FF)
val TextPrimary = Color(0xFFECEFF4)
val TextSecondary = Color(0xFF94A3B8)

private val DarkOpsColorScheme = darkColorScheme(
    primary = CyanPrimary,
    onPrimary = Color(0xFF001F24),
    primaryContainer = Color(0xFF00363F),
    onPrimaryContainer = Color(0xFFB2F5FF),
    secondary = AmberAlert,
    onSecondary = Color(0xFF261900),
    secondaryContainer = Color(0xFF3E2D00),
    onSecondaryContainer = Color(0xFFFFE082),
    tertiary = EmeraldComfort,
    background = TacticalObsidian,
    onBackground = TextPrimary,
    surface = TacticalSurface,
    onSurface = TextPrimary,
    surfaceVariant = TacticalCard,
    onSurfaceVariant = TextSecondary,
    error = CrimsonThreshold,
    outline = TacticalBorder
)

@Composable
fun NciTacticalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkOpsColorScheme,
        content = content
    )
}
