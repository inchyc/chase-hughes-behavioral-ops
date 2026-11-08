package com.nci.ops.ui.fieldops

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nci.ops.data.FieldOpsCatalog
import com.nci.ops.model.FieldMission
import com.nci.ops.model.FieldOpTier
import com.nci.ops.platform.PlatformServices
import com.nci.ops.storage.OpsRepository
import com.nci.ops.storage.PersistedAppState
import com.nci.ops.ui.theme.*

@Composable
fun FieldOpsScreen(
    appState: PersistedAppState,
    initialStealthMode: Boolean = false
) {
    val activeMission = FieldOpsCatalog.missions.find { it.id == appState.activeMissionId }
        ?: FieldOpsCatalog.missions.first()

    var selectedTier by remember { mutableStateOf(activeMission.tier) }
    var stealthModeActive by remember(initialStealthMode) { mutableStateOf(initialStealthMode) }
    var showDebriefForm by remember { mutableStateOf(false) }

    // Keep screen awake while in active stealth mode
    DisposableEffect(stealthModeActive) {
        if (stealthModeActive) {
            PlatformServices.setKeepScreenAwake(true)
        }
        onDispose {
            PlatformServices.setKeepScreenAwake(false)
        }
    }

    // Android Back Handler: safely close stealth mode or debrief form before exiting
    BackHandler(enabled = stealthModeActive || showDebriefForm) {
        if (showDebriefForm) {
            showDebriefForm = false
        } else if (stealthModeActive) {
            stealthModeActive = false
        }
    }

    // Debrief form fields
    var confidenceRating by remember { mutableStateOf(7) }
    var baselineInput by remember { mutableStateOf("") }
    var harvestedInput by remember { mutableStateOf("") }
    var reflectionInput by remember { mutableStateOf("") }

    if (stealthModeActive) {
        NoLookStealthTallyView(
            mission = activeMission,
            tallies = appState.activeStealthTallies,
            onTapQuadrant = { idx -> OpsRepository.incrementStealthQuadrant(idx) },
            onReset = { OpsRepository.resetStealthTallies() },
            onFinishToDebrief = {
                stealthModeActive = false
                showDebriefForm = true
            },
            onExitStealth = { stealthModeActive = false }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // HEADER
        Text(
            text = "PROGRESSIVE FIELD OPERATIONS",
            color = CyanPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.3.sp
        )
        Text(
            text = "Confidence, Rapport & Profiling Ladder",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Progress from zero-contact passive observation outside of conversation to micro-interactions, elicitation without questions, and full Ellipsis mastery.",
            color = TextSecondary,
            fontSize = 13.sp
        )

        // TIER SELECTOR PILLS (TIERS 1 - 5) WITH PROGRESSIVE UNLOCKING
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            FieldOpTier.entries.forEach { tier ->
                val isSelected = selectedTier == tier
                val isUnlocked = OpsRepository.isTierUnlocked(tier, appState)
                val unlockDesc = OpsRepository.getTierUnlockStatusDescription(tier, appState)

                val missionsInTier = FieldOpsCatalog.missions.filter { it.tier == tier }
                val completedInTier = missionsInTier.count { appState.completedMissionIds.contains(it.id) }

                Surface(
                    color = when {
                        isSelected && isUnlocked -> CyanPrimary.copy(alpha = 0.16f)
                        !isUnlocked -> TacticalObsidian
                        else -> TacticalCard
                    },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        1.dp,
                        when {
                            isSelected && isUnlocked -> CyanPrimary
                            !isUnlocked -> TacticalBorder.copy(alpha = 0.6f)
                            else -> TacticalBorder
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = isUnlocked) { selectedTier = tier }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (!isUnlocked) {
                                    Text(
                                        text = "🔒 ",
                                        fontSize = 14.sp
                                    )
                                }
                                Text(
                                    text = tier.title,
                                    color = when {
                                        !isUnlocked -> TextSecondary
                                        isSelected -> CyanPrimary
                                        else -> TextPrimary
                                    },
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = if (!isUnlocked) unlockDesc else tier.subtitle,
                                color = if (!isUnlocked) AmberAlert else TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                        if (isUnlocked) {
                            Text(
                                text = "$completedInTier/${missionsInTier.size} Done",
                                color = if (completedInTier == missionsInTier.size) EmeraldComfort else AmberAlert,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // MISSIONS IN SELECTED TIER
        val tierMissions = FieldOpsCatalog.missions.filter { it.tier == selectedTier }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tierMissions.forEach { mission ->
                val isCurrent = mission.id == activeMission.id
                val isDone = appState.completedMissionIds.contains(mission.id)
                OutlinedButton(
                    onClick = {
                        OpsRepository.selectActiveMission(mission.id)
                        showDebriefForm = false
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        1.5.dp,
                        when {
                            isCurrent -> CyanPrimary
                            isDone -> EmeraldComfort.copy(alpha = 0.7f)
                            else -> TacticalBorder
                        }
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isCurrent) CyanPrimary.copy(alpha = 0.15f) else TacticalObsidian
                    )
                ) {
                    Text(
                        text = if (isDone) "✓ ${mission.codeName}" else mission.codeName,
                        color = if (isCurrent) CyanPrimary else if (isDone) EmeraldComfort else TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // ACTIVE MISSION BRIEFING CARD
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, CyanPrimary.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = activeMission.codeName,
                        color = CyanPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "${activeMission.durationEstimate} • +${activeMission.xpReward} XP",
                        color = AmberAlert,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = activeMission.title,
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Source: ${activeMission.sourceBookOrCourse}",
                    color = PurpleEllipsis,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                )

                Text(
                    text = "MISSION OBJECTIVE",
                    color = CyanPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = activeMission.objective,
                    color = TextPrimary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                Text(
                    text = "PRE-FLIGHT TACTICAL BRIEFING",
                    color = AmberAlert,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                activeMission.preFlightBriefing.forEachIndexed { idx, step ->
                    Text(
                        text = "${idx + 1}. $step",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = TacticalObsidian,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, EmeraldComfort.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Success Criteria: ${activeMission.successCriteria}",
                        color = EmeraldComfort,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { stealthModeActive = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyanPrimary,
                            contentColor = TacticalObsidian
                        )
                    ) {
                        Text(
                            "Enter No-Look Haptic Stealth Tally",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp
                        )
                    }
                    OutlinedButton(
                        onClick = { showDebriefForm = !showDebriefForm },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, EmeraldComfort)
                    ) {
                        Text("Log Debrief", color = EmeraldComfort, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // POST-OP DEBRIEF LOGGER
        if (showDebriefForm) {
            Surface(
                color = TacticalSurface,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, EmeraldComfort),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "POST-OP DEBRIEF & HARVEST LOG",
                        color = EmeraldComfort,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Debrief Prompt: ${activeMission.debriefPrompt}",
                        color = AmberAlert,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Stealth Tally Recorded: " + activeMission.stealthTallyLabels.mapIndexed { idx, label ->
                            "$label (${appState.activeStealthTallies.getOrElse(idx) { 0 }})"
                        }.joinToString(" • "),
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    // Confidence Rating 1 - 10
                    Text(
                        text = "Self-Calibrated Confidence & Comfort Score: $confidenceRating / 10",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        (1..10).forEach { score ->
                            Surface(
                                color = if (confidenceRating == score) CyanPrimary else TacticalObsidian,
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, TacticalBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { confidenceRating = score }
                            ) {
                                Text(
                                    text = "$score",
                                    color = if (confidenceRating == score) TacticalObsidian else TextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = baselineInput,
                        onValueChange = { baselineInput = it },
                        label = { Text("Baselines & BTE Shifts Observed") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )
                    OutlinedTextField(
                        value = harvestedInput,
                        onValueChange = { harvestedInput = it },
                        label = { Text("Harvested Linguistic Words / Social Need Clues") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )
                    OutlinedTextField(
                        value = reflectionInput,
                        onValueChange = { reflectionInput = it },
                        label = { Text("Debrief Reflection (What worked / What to adjust)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )

                    Button(
                        onClick = {
                            OpsRepository.submitFieldDebrief(
                                mission = activeMission,
                                confidenceScore = confidenceRating,
                                baselineObserved = baselineInput,
                                harvestedPhrases = harvestedInput,
                                reflectionNotes = reflectionInput
                            )
                            baselineInput = ""
                            harvestedInput = ""
                            reflectionInput = ""
                            showDebriefForm = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmeraldComfort,
                            contentColor = TacticalObsidian
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            "Complete Field Op & Claim +${activeMission.xpReward} XP",
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }

        // RECENT DEBRIEF LOGS
        if (appState.debriefLogs.isNotEmpty()) {
            Text(
                text = "RECENT FIELD DEBRIEF LOGS (${appState.debriefLogs.size})",
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            appState.debriefLogs.take(5).forEach { log ->
                Surface(
                    color = TacticalCard,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, TacticalBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(log.missionTitle, color = CyanPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Confidence: ${log.confidenceScore}/10", color = EmeraldComfort, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        if (log.baselineObserved.isNotBlank()) {
                            Text("Baseline/Cues: ${log.baselineObserved}", color = TextPrimary, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                        if (log.harvestedPhrases.isNotBlank()) {
                            Text("Harvested: ${log.harvestedPhrases}", color = AmberAlert, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp))
                        }
                        if (log.reflectionNotes.isNotBlank()) {
                            Text("Debrief: ${log.reflectionNotes}", color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Giant 4-Quadrant "No-Look" Haptic Stealth Tally so the operator can log behavioral cues
 * in the field with their thumb without staring at the screen.
 */
@Composable
private fun NoLookStealthTallyView(
    mission: FieldMission,
    tallies: List<Int>,
    onTapQuadrant: (Int) -> Unit,
    onReset: () -> Unit,
    onFinishToDebrief: () -> Unit,
    onExitStealth: () -> Unit
) {
    val quadrantColors = listOf(CyanPrimary, AmberAlert, CrimsonThreshold, EmeraldComfort)
    val pulseDescriptions = listOf("1x Tick Haptic", "2x Pulse Haptic", "2x Pulse Haptic", "Long Haptic")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Stealth Top Bar
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, TacticalBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "NO-LOOK HAPTIC STEALTH MODE • ${mission.codeName}",
                        color = AmberAlert,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap quadrants by thumb feel without looking down at your screen.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                OutlinedButton(
                    onClick = onExitStealth,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Back", color = TextPrimary, fontSize = 12.sp)
                }
            }
        }

        // 2x2 GIANT BLIND-TAP QUADRANTS
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (row in 0..1) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (col in 0..1) {
                        val idx = row * 2 + col
                        val label = mission.stealthTallyLabels.getOrElse(idx) { "Cue #${idx + 1}" }
                        val count = tallies.getOrElse(idx) { 0 }
                        val accent = quadrantColors[idx]

                        Surface(
                            color = TacticalSurface,
                            shape = RoundedCornerShape(18.dp),
                            border = BorderStroke(2.dp, accent.copy(alpha = 0.65f)),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { onTapQuadrant(idx) }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "QUADRANT ${idx + 1} (${pulseDescriptions[idx]})",
                                    color = TextSecondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$count",
                                    color = accent,
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = label,
                                    color = TextPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onReset,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Zero Tallies", color = TextSecondary)
            }
            Button(
                onClick = onFinishToDebrief,
                modifier = Modifier.weight(2f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldComfort,
                    contentColor = TacticalObsidian
                )
            ) {
                Text("Complete Op & Log Debrief", fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}
