package com.nci.ops.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nci.ops.data.FieldOpsCatalog
import com.nci.ops.model.MicroHabit
import com.nci.ops.model.SkillAxis
import com.nci.ops.storage.OpsRepository
import com.nci.ops.storage.PersistedAppState
import com.nci.ops.ui.theme.*

@Composable
fun DashboardScreen(
    appState: PersistedAppState,
    onNavigateToFieldOps: (openStealthMode: Boolean) -> Unit,
    onNavigateToFieldOpCue: (cueIndex: Int) -> Unit = {},
    onNavigateToSkillAxis: (axis: SkillAxis) -> Unit = {},
    onNavigateToMicroHabitReview: (habitId: String) -> Unit = {},
    onNavigateToBteScorer: () -> Unit,
    onNavigateToCueCards: () -> Unit,
    onNavigateToSrs: () -> Unit
) {
    val activeMission = FieldOpsCatalog.missions.find { it.id == appState.activeMissionId }
        ?: FieldOpsCatalog.missions.first()

    val rankTitle = when {
        appState.totalXp >= 2500 -> "NCI Level 4: Behavioral Master"
        appState.totalXp >= 1500 -> "NCI Level 3: Ellipsis Specialist"
        appState.totalXp >= 800 -> "NCI Level 2: 6MX Practitioner"
        appState.totalXp >= 400 -> "NCI Level 1: Baseline Analyst"
        else -> "Field Initiate (Recon Tier)"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TOP OPERATOR STATUS HEADER
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, TacticalBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "BEHAVIORAL OPS COMMAND",
                            color = CyanPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = rankTitle,
                            color = TextPrimary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Surface(
                        color = CyanPrimary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = "${appState.totalXp} XP • ${appState.streakDays}d Streak",
                            color = CyanPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 3 RAPID TACTICAL SHORTCUTS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onNavigateToCueCards,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, AmberAlert.copy(alpha = 0.6f))
                    ) {
                        Text("30s Cue Cards", color = AmberAlert, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = onNavigateToBteScorer,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, CrimsonThreshold.copy(alpha = 0.6f))
                    ) {
                        Text("Live BTE Scorer", color = CrimsonThreshold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = onNavigateToSrs,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, PurpleEllipsis.copy(alpha = 0.6f))
                    ) {
                        Text("SRS Deck (${appState.flashcards.size})", color = PurpleEllipsis, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // ACTIVE FIELD MISSION BANNER (CLICKABLE TO REVIEW FULL BRIEFING & INSTRUCTIONS)
        Surface(
            color = TacticalSurface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, CyanPrimary.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToFieldOps(false) }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "ACTIVE FIELD OP • ${activeMission.tier.title.uppercase()}",
                            color = CyanPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Surface(
                            color = CyanPrimary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "TAP TO REVIEW ↗",
                                color = CyanPrimary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "+${activeMission.xpReward} XP",
                        color = EmeraldComfort,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${activeMission.codeName}: ${activeMission.title}",
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${activeMission.sourceBookOrCourse} • ${activeMission.durationEstimate}",
                    color = AmberAlert,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                )
                Text(
                    text = activeMission.objective,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                if (activeMission.targetBehaviors.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = TacticalObsidian,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, TacticalBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "TARGET BEHAVIOR INTEL PREVIEW (TAP TO INSPECT):",
                                    color = CyanPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "${activeMission.targetBehaviors.size} CUES",
                                    color = TextSecondary,
                                    fontSize = 9.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val quadrantColors = listOf(CyanPrimary, AmberAlert, CrimsonThreshold, EmeraldComfort)
                                activeMission.targetBehaviors.forEachIndexed { idx, cue ->
                                    val accent = quadrantColors.getOrElse(idx) { CyanPrimary }
                                    Surface(
                                        color = accent.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.dp, accent.copy(alpha = 0.6f)),
                                        modifier = Modifier.clickable { onNavigateToFieldOpCue(idx) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text("[Q${idx + 1}]", color = accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                            Text(cue.cueName, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                            Text("↗", color = accent, fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onNavigateToFieldOps(true) },
                        modifier = Modifier.weight(1.2f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = TacticalObsidian)
                    ) {
                        Text("Launch Stealth Tally", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    }
                    Button(
                        onClick = { onNavigateToFieldOps(false) },
                        modifier = Modifier.weight(1.2f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TacticalCard, contentColor = CyanPrimary),
                        border = BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.7f))
                    ) {
                        Text("Review Briefing", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    OutlinedButton(
                        onClick = { onNavigateToFieldOps(false) },
                        modifier = Modifier.weight(0.9f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, TacticalBorder)
                    ) {
                        Text("Switch Op", color = TextPrimary, fontSize = 12.sp)
                    }
                }
            }
        }

        // AUTOMATICITY / MICRO-HABIT CONDITIONING TRACKER
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, TacticalBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "AUTOMATICITY & MICRO-HABIT REPS",
                    color = EmeraldComfort,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "Under social pressure, you do not rise to your knowledge—you fall to your conditioned behavioral habits.",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    appState.microHabits.forEach { habit ->
                        MicroHabitRow(
                            habit = habit,
                            onIncrement = { OpsRepository.incrementMicroHabitRep(habit.id) },
                            onReviewTechnique = { onNavigateToMicroHabitReview(habit.id) }
                        )
                    }
                }
            }
        }

        // 6-AXIS BEHAVIORAL SKILL PROFILE (TAP AXIS TO TRAIN)
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, TacticalBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "6-AXIS NCI & ELLIPSIS SKILL PROFILE",
                        color = AmberAlert,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "TAP AXIS TO TRAIN ↗",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                SkillAxis.entries.forEach { axis ->
                    val score = appState.skillScores[axis] ?: 20
                    val trainTarget = when (axis) {
                        SkillAxis.BASELINE_RECON -> "BTE Periodic Table"
                        SkillAxis.NONVERBAL_AUTHORITY -> "Academy NCI-1"
                        SkillAxis.TRUST_RAPPORT -> "Trust Code Cards"
                        SkillAxis.SIX_MX_PROFILING -> "6MX Human Compass"
                        SkillAxis.BTE_DETECTION -> "Live BTE Scorer"
                        SkillAxis.ELLIPSIS_ELICITATION -> "Cue Cards & Scripts"
                    }
                    Surface(
                        color = TacticalObsidian.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, TacticalBorder.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onNavigateToSkillAxis(axis) }
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(axis.displayName, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("• $trainTarget ↗", color = AmberAlert.copy(alpha = 0.85f), fontSize = 10.sp)
                                }
                                Text("$score / 100", color = CyanPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(TacticalObsidian)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = (score / 100f).coerceIn(0.05f, 1f))
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (score >= 60) EmeraldComfort else CyanPrimary)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MicroHabitRow(
    habit: MicroHabit,
    onIncrement: () -> Unit,
    onReviewTechnique: () -> Unit
) {
    val isComplete = habit.currentRepsToday >= habit.dailyTargetReps
    Surface(
        color = TacticalObsidian,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            1.dp,
            if (isComplete) EmeraldComfort.copy(alpha = 0.6f) else TacticalBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
                    .clickable { onReviewTechnique() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = habit.title,
                        color = if (isComplete) EmeraldComfort else TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "[Guide ↗]",
                        color = CyanPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = habit.whyItMatters,
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = "Today: ${habit.currentRepsToday}/${habit.dailyTargetReps} • Lifetime: ${habit.totalLifetimeReps} reps",
                    color = AmberAlert,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Button(
                onClick = onIncrement,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isComplete) EmeraldComfort else CyanPrimary,
                    contentColor = TacticalObsidian
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text("+1 Rep", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
            }
        }
    }
}
