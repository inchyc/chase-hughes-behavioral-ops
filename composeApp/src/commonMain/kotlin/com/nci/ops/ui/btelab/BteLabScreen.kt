package com.nci.ops.ui.btelab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nci.ops.data.BteCatalog
import com.nci.ops.model.BaselineSnapshot
import com.nci.ops.model.BteCategory
import com.nci.ops.model.BteElement
import com.nci.ops.model.CuePolarity
import com.nci.ops.storage.OpsRepository
import com.nci.ops.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

enum class BteSubTab(val label: String) {
    LIVE_SCORER("Live 7s Scorer"),
    PERIODIC_TABLE("BTE Table (22)"),
    TIMED_DRILLS("7s Drills")
}

@Composable
fun BteLabScreen() {
    val bteSession by OpsRepository.bteSession.collectAsState()
    var activeSubTab by remember { mutableStateOf(BteSubTab.LIVE_SCORER) }
    var selectedElement by remember { mutableStateOf<BteElement?>(BteCatalog.elements.first()) }
    var selectedCategoryFilter by remember { mutableStateOf<BteCategory?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "BEHAVIORAL TABLE OF ELEMENTS (BTE) LAB",
            color = CyanPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.3.sp
        )
        Text(
            text = "7-Second Cluster Scorer & Periodic Grid",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )

        // SUB-TAB SELECTOR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BteSubTab.entries.forEach { tab ->
                val isSelected = activeSubTab == tab
                Button(
                    onClick = { activeSubTab = tab },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) CyanPrimary else TacticalCard,
                        contentColor = if (isSelected) TacticalObsidian else TextPrimary
                    ),
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
                ) {
                    Text(tab.label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        when (activeSubTab) {
            BteSubTab.LIVE_SCORER -> LiveBteScorerSection(bteSession = bteSession)
            BteSubTab.PERIODIC_TABLE -> BtePeriodicGridSection(
                selectedCategoryFilter = selectedCategoryFilter,
                onSelectCategory = { selectedCategoryFilter = it },
                selectedElement = selectedElement,
                onSelectElement = { selectedElement = it }
            )
            BteSubTab.TIMED_DRILLS -> BteTimedDrillsSection()
        }
    }
}

@Composable
private fun LiveBteScorerSection(
    bteSession: OpsRepository.LiveBteSession
) {
    var blinkSelection by remember { mutableStateOf("Normal (12-18/min)") }
    var handSelection by remember { mutableStateOf("Relaxed / Open Palms") }
    var torsoSelection by remember { mutableStateOf("Ventral Fronting") }
    var vocalSelection by remember { mutableStateOf("Steady Chest Voice") }
    var stimulusProbeText by remember { mutableStateOf("") }

    // Real-time ticking remaining millis for active 7-second stimulus countdown
    var remainingMillis by remember { mutableStateOf(0L) }
    LaunchedEffect(bteSession.stimulusDeliveredEpochMs, bteSession.stimulusActiveUntilEpochMs) {
        while (bteSession.isStimulusWindowActive) {
            remainingMillis = bteSession.remainingStimulusMillis
            if (remainingMillis <= 0L) break
            delay(100L)
        }
        remainingMillis = 0L
    }

    val isWindowActive = bteSession.isStimulusWindowActive

    // STEP 1: BASELINE-FIRST GATE (WITH PROMINENT ONE-TAP HOT-START SKIP)
    if (!bteSession.baselineLocked) {
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, AmberAlert),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STEP 1: 30-SECOND BASELINE CALIBRATION",
                        color = AmberAlert,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "NCI PROTOCOL",
                        color = CyanPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "In The Behavior Operations Manual, isolated body language cues mean nothing without a calibrated baseline. Lock the resting baseline below, or tap Hot Start to begin immediately if already mid-conversation.",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )

                // PROMINENT ONE-TAP SKIP BUTTON FOR FAST/UNEXPECTED ENCOUNTERS
                Button(
                    onClick = { OpsRepository.skipBaselineHotStart() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmberAlert,
                        contentColor = TacticalObsidian
                    )
                ) {
                    Text(
                        "⚡ Skip Baseline (Hot Start / Immediate Scoring)",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp
                    )
                }

                HorizontalDivider(color = TacticalBorder)

                // Quick Baseline Selectors
                BaselineSelectorRow(
                    label = "Resting Blink Rate",
                    options = listOf("Normal (12-18/min)", "Slow / Focused", "Already Elevated"),
                    selected = blinkSelection,
                    onSelect = { blinkSelection = it }
                )
                BaselineSelectorRow(
                    label = "Resting Hand Posture",
                    options = listOf("Relaxed / Open Palms", "Expressive Gestures", "Folded / On Lap"),
                    selected = handSelection,
                    onSelect = { handSelection = it }
                )
                BaselineSelectorRow(
                    label = "Resting Torso & Arms",
                    options = listOf("Ventral Fronting", "Arms Crossed (Cold/Habit)", "Angled / Bladed"),
                    selected = torsoSelection,
                    onSelect = { torsoSelection = it }
                )
                BaselineSelectorRow(
                    label = "Resting Vocal Cadence",
                    options = listOf("Steady Chest Voice", "Fast / Animated", "Soft / Hesitant"),
                    selected = vocalSelection,
                    onSelect = { vocalSelection = it }
                )

                Button(
                    onClick = {
                        OpsRepository.lockBaseline(
                            BaselineSnapshot(
                                skipped = false,
                                blinkRate = blinkSelection,
                                handPosture = handSelection,
                                torsoOrientation = torsoSelection,
                                vocalPacePitch = vocalSelection
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldComfort,
                        contentColor = TacticalObsidian
                    )
                ) {
                    Text(
                        "✓ Lock Calibrated Baseline & Open BTE Scorer",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
        return
    }

    // STEP 2: ACTIVE LIVE BTE CLUSTER SCORER
    // Only cues observed within the 7-second window contribute to the cluster score
    val windowObservedIds = bteSession.observedElementIdsInWindow
    val allObservedIds = bteSession.allObservedElementIds

    val elementsInWindow = BteCatalog.elements.filter { windowObservedIds.contains(it.id) }
    val stressScore = elementsInWindow
        .filter { it.polarity == CuePolarity.DISCOMFORT_STRESS }
        .sumOf { it.pointValue }
    val comfortCount = elementsInWindow.count { it.polarity == CuePolarity.COMFORT_RAPPORT }
    val thresholdCrossed = stressScore >= 11

    Surface(
        color = if (thresholdCrossed) CrimsonThreshold.copy(alpha = 0.14f) else TacticalCard,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            2.dp,
            if (thresholdCrossed) CrimsonThreshold else if (isWindowActive) AmberAlert else CyanPrimary
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (bteSession.baselineSnapshot.skipped) {
                            "⚠️ HOT START MODE (UNCALIBRATED BASELINE)"
                        } else {
                            "✓ CALIBRATED BASELINE LOCKED"
                        },
                        color = if (bteSession.baselineSnapshot.skipped) AmberAlert else EmeraldComfort,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = if (bteSession.baselineSnapshot.skipped) {
                            "Baseline skipped — require multi-channel cluster within 7s of stimulus."
                        } else {
                            "${bteSession.baselineSnapshot.blinkRate} • ${bteSession.baselineSnapshot.torsoOrientation}"
                        },
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                OutlinedButton(
                    onClick = { OpsRepository.resetBteSession() },
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Re-Baseline", color = TextPrimary, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 7-SECOND STIMULUS CONTROLLER
            Surface(
                color = if (isWindowActive) AmberAlert.copy(alpha = 0.18f) else TacticalObsidian,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, if (isWindowActive) AmberAlert else TacticalBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    if (isWindowActive) {
                        val secondsLeft = (remainingMillis / 100.0).roundToInt() / 10.0
                        val progressFraction = (remainingMillis / 7000f).coerceIn(0f, 1f)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⏱️ 7s STIMULUS WINDOW ACTIVE",
                                color = AmberAlert,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "${secondsLeft}s",
                                color = AmberAlert,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(TacticalBorder)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = progressFraction)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(AmberAlert)
                            )
                        }
                        Text(
                            text = "Tapping deviations right now links them directly to the stimulus probe.",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    } else {
                        Text(
                            text = "7-SECOND RULE STIMULUS TRIGGER",
                            color = CyanPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "In Chase Hughes' method, cues outside the 7-second post-stimulus window cannot be scored as deception/stress. Deliver your probe, then tap start:",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { OpsRepository.start7SecondStimulusWindow(stimulusProbeText) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AmberAlert,
                                    contentColor = TacticalObsidian
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("⚡ Deliver Stimulus (Start 7s Timer)", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // SCORE DISPLAY BANNER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "POST-STIMULUS CLUSTER SCORE",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$stressScore / 11 pts",
                        color = if (thresholdCrossed) CrimsonThreshold else CyanPrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Surface(
                        color = if (thresholdCrossed) CrimsonThreshold else TacticalObsidian,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, if (thresholdCrossed) CrimsonThreshold else TacticalBorder)
                    ) {
                        Text(
                            text = if (thresholdCrossed) {
                                "🚨 >= 11 THRESHOLD CROSSED\n(HIGH DECEPTION / STRESS CLUSTER)"
                            } else {
                                "BELOW 11 THRESHOLD\n(${11 - stressScore} pts to threshold)"
                            },
                            color = if (thresholdCrossed) TacticalObsidian else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                    if (comfortCount > 0) {
                        Text(
                            text = "+$comfortCount Comfort/Rapport Cues Active",
                            color = EmeraldComfort,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            if (elementsInWindow.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Stimulus Cluster (7s): " + elementsInWindow.joinToString(" + ") { "${it.symbol} (${it.pointValue}p)" },
                    color = AmberAlert,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // TAP-TO-SCORE BTE ELEMENTS BY ZONE
    Text(
        text = if (isWindowActive) "TAP CUES OBSERVED IN 7-SECOND WINDOW:" else "TAP CUES (DELIVER STIMULUS ABOVE FOR 7s WINDOW):",
        color = if (isWindowActive) AmberAlert else TextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )

    BteCategory.entries.forEach { category ->
        val categoryElements = BteCatalog.elements.filter { it.category == category }
        Text(
            text = "${category.displayName} (${category.zoneCode})",
            color = CyanPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            categoryElements.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { el ->
                        val isObserved = allObservedIds.contains(el.id)
                        val isInStimulusWindow = windowObservedIds.contains(el.id)
                        val isComfort = el.polarity == CuePolarity.COMFORT_RAPPORT

                        val badgeColor = when {
                            isComfort -> EmeraldComfort
                            el.pointValue >= 4 -> CrimsonThreshold
                            else -> AmberAlert
                        }

                        Surface(
                            color = if (isObserved) badgeColor.copy(alpha = 0.2f) else TacticalCard,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(
                                if (isObserved) 2.dp else 1.dp,
                                if (isInStimulusWindow) AmberAlert else if (isObserved) badgeColor else TacticalBorder
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { OpsRepository.toggleBteObservation(el) }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    color = badgeColor.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp),
                                    border = BorderStroke(1.dp, badgeColor)
                                ) {
                                    Text(
                                        text = el.symbol,
                                        color = badgeColor,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = el.name,
                                        color = TextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2
                                    )
                                    Text(
                                        text = if (isComfort) "Comfort Cue"
                                        else if (isInStimulusWindow) "+${el.pointValue}p [7s STIMULUS]"
                                        else if (isObserved) "Baseline (Outside 7s)"
                                        else "+${el.pointValue} BTE pts",
                                        color = if (isInStimulusWindow) AmberAlert else badgeColor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun BaselineSelectorRow(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.forEach { option ->
                val isSelected = selected == option
                Surface(
                    color = if (isSelected) CyanPrimary.copy(alpha = 0.2f) else TacticalObsidian,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, if (isSelected) CyanPrimary else TacticalBorder),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(option) }
                ) {
                    Text(
                        text = option,
                        color = if (isSelected) CyanPrimary else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BtePeriodicGridSection(
    selectedCategoryFilter: BteCategory?,
    onSelectCategory: (BteCategory?) -> Unit,
    selectedElement: BteElement?,
    onSelectElement: (BteElement) -> Unit
) {
    // Category Filter Chips
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        OutlinedButton(
            onClick = { onSelectCategory(null) },
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            border = BorderStroke(1.dp, if (selectedCategoryFilter == null) CyanPrimary else TacticalBorder)
        ) {
            Text("All (22)", color = if (selectedCategoryFilter == null) CyanPrimary else TextSecondary, fontSize = 11.sp)
        }
        BteCategory.entries.take(4).forEach { cat ->
            OutlinedButton(
                onClick = { onSelectCategory(if (selectedCategoryFilter == cat) null else cat) },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                border = BorderStroke(1.dp, if (selectedCategoryFilter == cat) CyanPrimary else TacticalBorder)
            ) {
                Text(cat.zoneCode, color = if (selectedCategoryFilter == cat) CyanPrimary else TextSecondary, fontSize = 11.sp)
            }
        }
    }

    val filteredElements = if (selectedCategoryFilter == null) {
        BteCatalog.elements
    } else {
        BteCatalog.elements.filter { it.category == selectedCategoryFilter }
    }

    // Periodic Table Tiles (4 per row)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        filteredElements.chunked(4).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { el ->
                    val isSelected = selectedElement?.id == el.id
                    val accent = when {
                        el.polarity == CuePolarity.COMFORT_RAPPORT -> EmeraldComfort
                        el.pointValue >= 4 -> CrimsonThreshold
                        else -> AmberAlert
                    }
                    Surface(
                        color = if (isSelected) accent.copy(alpha = 0.22f) else TacticalCard,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) accent else TacticalBorder),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectElement(el) }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("#${el.atomicNumber}", color = TextSecondary, fontSize = 9.sp)
                                Text(
                                    text = if (el.pointValue == 0) "CMF" else "${el.pointValue}pt",
                                    color = accent,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = el.symbol,
                                color = accent,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                            Text(
                                text = el.name,
                                color = TextPrimary,
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
                repeat(4 - row.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

    // SELECTED ELEMENT INSPECTOR CARD
    selectedElement?.let { el ->
        Surface(
            color = TacticalSurface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, CyanPrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "[${el.symbol}] ${el.name}",
                        color = CyanPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = if (el.pointValue == 0) "Comfort Indicator" else "Weight: +${el.pointValue} pts",
                        color = if (el.pointValue == 0) EmeraldComfort else CrimsonThreshold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Zone: ${el.category.displayName} • Source: ${el.bookSource}",
                    color = AmberAlert,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Resting Baseline: ${el.baselineNote}",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "Post-Stimulus Deviation: ${el.deviationMeaning}",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
                Text(
                    text = "Neuro-Mechanism: ${el.psychologyMechanism}",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "High-Probability Cluster Synergies: ${el.commonClusters.joinToString(", ")}",
                    color = EmeraldComfort,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun BteTimedDrillsSection() {
    var scenarioIdx by remember { mutableStateOf(0) }
    var userGuessCrossed by remember { mutableStateOf<Boolean?>(null) }

    val scenario = BteCatalog.timedScenarios[scenarioIdx % BteCatalog.timedScenarios.size]

    Surface(
        color = TacticalCard,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, TacticalBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "DRILL ${scenarioIdx + 1} OF ${BteCatalog.timedScenarios.size}",
                    color = CyanPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "7-Second Threshold Analysis",
                    color = AmberAlert,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = scenario.title,
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Context: ${scenario.context}",
                color = TextSecondary,
                fontSize = 13.sp
            )
            Surface(
                color = TacticalObsidian,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, EmeraldComfort.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Established Baseline: ${scenario.baselineDescription}",
                    color = EmeraldComfort,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Text(
                text = "Stimulus Delivered: ${scenario.stimulusQuestion}",
                color = AmberAlert,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Cues Observed Within 7 Seconds: ${scenario.observedCueSymbols.joinToString(" -> ")}",
                color = CyanPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { userGuessCrossed = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CrimsonThreshold,
                        contentColor = TacticalObsidian
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(">= 11 Threshold Crossed", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                }
                Button(
                    onClick = { userGuessCrossed = false },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldComfort,
                        contentColor = TacticalObsidian
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Below 11 / Benign Shift", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                }
            }

            userGuessCrossed?.let { guess ->
                val isCorrect = guess == scenario.crossesThreshold
                Surface(
                    color = TacticalObsidian,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.5.dp, if (isCorrect) EmeraldComfort else CrimsonThreshold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (isCorrect) "✓ CORRECT READ" else "✗ INCORRECT READ",
                            color = if (isCorrect) EmeraldComfort else CrimsonThreshold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = scenario.analysisExplanation,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                        Button(
                            onClick = {
                                userGuessCrossed = null
                                scenarioIdx = (scenarioIdx + 1) % BteCatalog.timedScenarios.size
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyanPrimary,
                                contentColor = TacticalObsidian
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Next Timed Scenario ->", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
