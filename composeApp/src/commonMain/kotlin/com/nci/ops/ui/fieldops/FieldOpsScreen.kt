package com.nci.ops.ui.fieldops

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.nci.ops.data.FieldOpsCatalog
import com.nci.ops.model.CurriculumTrackId
import com.nci.ops.model.FieldMission
import com.nci.ops.model.FieldOpTier
import com.nci.ops.model.TargetBehaviorGuide
import com.nci.ops.platform.PlatformServices
import com.nci.ops.storage.OpsRepository
import com.nci.ops.storage.PersistedAppState
import com.nci.ops.ui.theme.*

@Composable
fun FieldOpsScreen(
    appState: PersistedAppState,
    initialStealthMode: Boolean = false,
    initialCueIndex: Int? = null,
    onNavigateToAcademyTrack: (CurriculumTrackId) -> Unit = {}
) {
    val activeMission = FieldOpsCatalog.missions.find { it.id == appState.activeMissionId }
        ?: FieldOpsCatalog.missions.first()

    var selectedTier by remember { mutableStateOf(activeMission.tier) }
    var stealthModeActive by remember(initialStealthMode) { mutableStateOf(initialStealthMode) }
    var showDebriefForm by remember { mutableStateOf(false) }

    // Synchronize selected tier with active mission whenever activeMission changes
    LaunchedEffect(activeMission.id) {
        selectedTier = activeMission.tier
    }

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
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // SCREEN TITLE
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
            fontSize = 12.sp,
            lineHeight = 17.sp
        )

        // ERGONOMIC HORIZONTAL TIER SELECTOR CAROUSEL
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "OPERATIONAL TIER LADDER:",
                color = TextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FieldOpTier.entries.forEach { tier ->
                    val isSelected = selectedTier == tier
                    val isUnlocked = OpsRepository.isTierUnlocked(tier, appState)
                    val missionsInTier = FieldOpsCatalog.missions.filter { it.tier == tier }
                    val completedInTier = missionsInTier.count { appState.completedMissionIds.contains(it.id) }
                    val isTierComplete = completedInTier == missionsInTier.size

                    Surface(
                        color = when {
                            isSelected && isUnlocked -> CyanPrimary.copy(alpha = 0.18f)
                            !isUnlocked -> TacticalObsidian
                            else -> TacticalCard
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            1.5.dp,
                            when {
                                isSelected && isUnlocked -> CyanPrimary
                                !isUnlocked -> TacticalBorder.copy(alpha = 0.5f)
                                else -> TacticalBorder
                            }
                        ),
                        modifier = Modifier
                            .clickable(enabled = isUnlocked) { selectedTier = tier }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = if (!isUnlocked) "🔒 T${tier.tierNumber}" else "T${tier.tierNumber}",
                                color = if (isSelected) CyanPrimary else if (!isUnlocked) TextSecondary else TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = tier.title.replace("Tier ${tier.tierNumber}: ", ""),
                                color = if (isSelected) CyanPrimary else if (!isUnlocked) TextSecondary else TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                            if (isUnlocked) {
                                Surface(
                                    color = if (isTierComplete) EmeraldComfort.copy(alpha = 0.2f) else AmberAlert.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "$completedInTier/${missionsInTier.size}",
                                        color = if (isTierComplete) EmeraldComfort else AmberAlert,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Compact Tier Subtitle & Unlock Requirement Bar
            val isCurrentTierUnlocked = OpsRepository.isTierUnlocked(selectedTier, appState)
            val unlockDescription = OpsRepository.getTierUnlockStatusDescription(selectedTier, appState)
            Surface(
                color = TacticalSurface,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, TacticalBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (!isCurrentTierUnlocked) "🔒 $unlockDescription" else selectedTier.subtitle,
                        color = if (!isCurrentTierUnlocked) AmberAlert else TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // MISSIONS IN SELECTED TIER CHIPS
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
                            isDone -> EmeraldComfort.copy(alpha = 0.8f)
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

        // ACTIVE MISSION BRIEFING & BEHAVIORAL FIELD GUIDE CARD
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, CyanPrimary.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header with codeName, duration, XP
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
                val matchingTrack = when {
                    activeMission.sourceBookOrCourse.contains("Six-Minute", ignoreCase = true) -> CurriculumTrackId.SIX_MINUTE_XRAY
                    activeMission.sourceBookOrCourse.contains("Ellipsis", ignoreCase = true) -> CurriculumTrackId.ELLIPSIS_MANUAL
                    activeMission.sourceBookOrCourse.contains("Trust", ignoreCase = true) -> CurriculumTrackId.TRUST_CODE
                    activeMission.sourceBookOrCourse.contains("Manual", ignoreCase = true) -> CurriculumTrackId.BEHAVIOR_OPS_MANUAL
                    else -> null
                }
                Surface(
                    color = PurpleEllipsis.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, PurpleEllipsis.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 10.dp)
                        .clickable(enabled = matchingTrack != null) {
                            matchingTrack?.let { onNavigateToAcademyTrack(it) }
                        }
                ) {
                    Text(
                        text = if (matchingTrack != null) "Source: ${activeMission.sourceBookOrCourse} • [Review in Academy Codex ↗]" else "Source: ${activeMission.sourceBookOrCourse}",
                        color = PurpleEllipsis,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // MISSION OBJECTIVE
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

                // PRE-FLIGHT TACTICAL BRIEFING
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

                // ==============================================================
                // BRAND NEW: BEHAVIOR OBSERVATION FIELD GUIDE (WHAT TO OBSERVE)
                // ==============================================================
                if (activeMission.targetBehaviors.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    BehaviorObservationFieldGuide(
                        behaviors = activeMission.targetBehaviors,
                        initialCueIndex = initialCueIndex
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
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
                            "Launch Stealth Tally",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp
                        )
                    }
                    OutlinedButton(
                        onClick = { showDebriefForm = !showDebriefForm },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, EmeraldComfort)
                    ) {
                        Text(
                            if (showDebriefForm) "Hide Debrief" else "Log Debrief",
                            color = EmeraldComfort,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
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

                    // Display active tallies with quick chips
                    Text(
                        text = "Session Observations Tally:",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val quadrantColors = listOf(CyanPrimary, AmberAlert, CrimsonThreshold, EmeraldComfort)
                        activeMission.stealthTallyLabels.forEachIndexed { idx, label ->
                            val count = appState.activeStealthTallies.getOrElse(idx) { 0 }
                            Surface(
                                color = quadrantColors[idx].copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, quadrantColors[idx].copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "$label: $count",
                                    color = quadrantColors[idx],
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

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
                        label = { Text("Baselines & Cues Observed (Where & What)") },
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
 * Interactive Behavior Observation Field Guide Component
 * Explains each target behavior with visual tells, real-world examples, limbic drivers, and pitfalls.
 */
@Composable
fun BehaviorObservationFieldGuide(
    behaviors: List<TargetBehaviorGuide>,
    initialCueIndex: Int? = null
) {
    var selectedCueIndex by remember(initialCueIndex) {
        mutableStateOf(initialCueIndex?.coerceIn(0, (behaviors.size - 1).coerceAtLeast(0)) ?: 0)
    }
    val quadrantColors = listOf(CyanPrimary, AmberAlert, CrimsonThreshold, EmeraldComfort)

    Surface(
        color = TacticalSurface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, CyanPrimary.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BEHAVIOR FIELD GUIDE: WHAT TO OBSERVE",
                    color = CyanPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.1.sp
                )
                Text(
                    text = "${behaviors.size} CUES DEFINED",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "Tap any cue below to review concrete physical tells, real-world scenes, and limbic drivers:",
                color = TextSecondary,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
            )

            // Horizontal Cue Selector Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                behaviors.forEachIndexed { idx, cue ->
                    val isSelected = selectedCueIndex == idx
                    val accent = quadrantColors.getOrElse(idx) { CyanPrimary }

                    Surface(
                        color = if (isSelected) accent.copy(alpha = 0.2f) else TacticalObsidian,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            1.5.dp,
                            if (isSelected) accent else TacticalBorder
                        ),
                        modifier = Modifier.clickable { selectedCueIndex = idx }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "[Q${idx + 1}]",
                                color = accent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = cue.cueName,
                                color = if (isSelected) TextPrimary else TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Detailed Card for Selected Behavior
            val activeCue = behaviors.getOrElse(selectedCueIndex) { behaviors.first() }
            val accentColor = quadrantColors.getOrElse(selectedCueIndex) { CyanPrimary }

            Surface(
                color = TacticalObsidian,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Cue Title & Hint
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Quadrant ${selectedCueIndex + 1}: ${activeCue.cueName}",
                            color = accentColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (activeCue.relatedBteSymbol != null) {
                            Surface(
                                color = PurpleEllipsis.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, PurpleEllipsis.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "Code: ${activeCue.relatedBteSymbol}",
                                    color = PurpleEllipsis,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "Quick Field Hint: ${activeCue.quickHint}",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    HorizontalDivider(color = TacticalBorder, thickness = 1.dp)

                    // 1. Physical Visual Tells
                    Text(
                        text = "👁️ EXACT PHYSICAL & ANATOMICAL TELLS",
                        color = CyanPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    activeCue.physicalTells.forEach { tell ->
                        Row(modifier = Modifier.padding(start = 4.dp)) {
                            Text("• ", color = CyanPrimary, fontSize = 12.sp)
                            Text(
                                text = tell,
                                color = TextPrimary,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // 2. Real-World Scenario
                    Text(
                        text = "🎬 REAL-WORLD FIELD SCENE",
                        color = AmberAlert,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Surface(
                        color = TacticalCard,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "\"${activeCue.realWorldExample}\"",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    // 3. Psychological & Limbic Why
                    Text(
                        text = "🧠 PSYCHOLOGICAL & LIMBIC MECHANISM",
                        color = EmeraldComfort,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = activeCue.psychologicalWhy,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    // 4. False Positives
                    Text(
                        text = "⚠️ FALSE POSITIVES / WATCH OUT FOR",
                        color = CrimsonThreshold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = activeCue.falsePositivesToAvoid,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Giant 4-Quadrant "No-Look" Haptic Stealth Tally
 * Upgraded with 1-line subtitle hints in each quadrant and an in-field "Cue Intel" sheet button.
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
    val pulseDescriptions = listOf("1x Tick", "2x Pulse", "2x Pulse", "Long Pulse")
    var showIntelSheet by remember { mutableStateOf(false) }

    // In-Field Cue Intel Modal Dialog so the operative can check behaviors mid-session
    if (showIntelSheet) {
        Dialog(onDismissRequest = { showIntelSheet = false }) {
            Surface(
                color = TacticalCard,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, CyanPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "FIELD CUE INTEL SHEET",
                            color = CyanPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        IconButton(onClick = { showIntelSheet = false }) {
                            Text("✕", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(
                        text = "Active Mission: ${mission.codeName} (${mission.title})",
                        color = AmberAlert,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        mission.targetBehaviors.forEachIndexed { idx, cue ->
                            val accent = quadrantColors.getOrElse(idx) { CyanPrimary }
                            Surface(
                                color = TacticalObsidian,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, accent.copy(alpha = 0.6f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "QUADRANT ${idx + 1}: ${cue.cueName}",
                                            color = accent,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = pulseDescriptions[idx],
                                            color = TextSecondary,
                                            fontSize = 10.sp
                                        )
                                    }
                                    Text(
                                        text = cue.quickHint,
                                        color = TextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                                    )
                                    Text(
                                        text = "Tells: ${cue.physicalTells.joinToString("; ")}",
                                        color = TextSecondary,
                                        fontSize = 11.sp,
                                        lineHeight = 15.sp
                                    )
                                    Text(
                                        text = "Example: ${cue.realWorldExample}",
                                        color = AmberAlert.copy(alpha = 0.85f),
                                        fontSize = 10.sp,
                                        lineHeight = 14.sp,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { showIntelSheet = false },
                        colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = TacticalObsidian),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Resume Stealth Tally", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

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
                        text = "NO-LOOK STEALTH MODE • ${mission.codeName}",
                        color = AmberAlert,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap quadrants by thumb feel without looking down.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                        onClick = { showIntelSheet = true },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        border = BorderStroke(1.dp, CyanPrimary)
                    ) {
                        Text("📖 Intel", color = CyanPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = onExitStealth,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Exit", color = TextPrimary, fontSize = 12.sp)
                    }
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
                        val cueGuide = mission.targetBehaviors.getOrNull(idx)
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
                                    .padding(14.dp),
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
                                    fontSize = 44.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = label,
                                        color = TextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    if (cueGuide != null) {
                                        Text(
                                            text = cueGuide.quickHint,
                                            color = accent.copy(alpha = 0.9f),
                                            fontSize = 10.sp,
                                            lineHeight = 13.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                }
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
                Text("Finish & Debrief", fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}
