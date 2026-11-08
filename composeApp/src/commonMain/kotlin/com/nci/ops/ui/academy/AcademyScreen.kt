package com.nci.ops.ui.academy

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nci.ops.data.AcademyCatalog
import com.nci.ops.model.CurriculumTrackId
import com.nci.ops.platform.PlatformServices
import com.nci.ops.storage.OpsRepository
import com.nci.ops.storage.PersistedAppState
import com.nci.ops.storage.RecallGrade
import com.nci.ops.ui.theme.*

enum class AcademySubTab(val label: String) {
    CURRICULUM("8-Track Academy"),
    SRS_FLASHCARDS("SM-2 Flashcards"),
    NOTE_VAULT("Custom Note Vault")
}

@Composable
fun AcademyScreen(
    appState: PersistedAppState,
    initialSubTab: AcademySubTab = AcademySubTab.CURRICULUM,
    initialTrackId: CurriculumTrackId? = null,
    onNavigateToFieldOps: () -> Unit = {}
) {
    var activeTab by remember(initialSubTab) { mutableStateOf(initialSubTab) }
    val now = PlatformServices.currentEpochMs()
    val dueCount = appState.flashcards.count { it.nextReviewEpochMs <= now }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "BOOKS, NCI LEVELS 1–4 & SPACED REPETITION",
            color = CyanPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.3.sp
        )
        Text(
            text = "Knowledge Codex & SM-2 Deck",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AcademySubTab.entries.forEach { tab ->
                val isSelected = activeTab == tab
                val tabText = if (tab == AcademySubTab.SRS_FLASHCARDS && dueCount > 0) {
                    "SRS (${dueCount} Due)"
                } else {
                    tab.label
                }

                Button(
                    onClick = { activeTab = tab },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) CyanPrimary else TacticalCard,
                        contentColor = if (isSelected) TacticalObsidian else TextPrimary
                    ),
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp)
                ) {
                    Text(tabText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        when (activeTab) {
            AcademySubTab.CURRICULUM -> CurriculumTracksView(
                appState = appState,
                initialTrackId = initialTrackId,
                onNavigateToFieldOps = onNavigateToFieldOps
            )
            AcademySubTab.SRS_FLASHCARDS -> SrsFlashcardTrainerView(appState = appState)
            AcademySubTab.NOTE_VAULT -> CustomNoteVaultView()
        }
    }
}

@Composable
private fun CurriculumTracksView(
    appState: PersistedAppState,
    initialTrackId: CurriculumTrackId? = null,
    onNavigateToFieldOps: () -> Unit = {}
) {
    var selectedTrack by remember(initialTrackId) {
        mutableStateOf(initialTrackId ?: CurriculumTrackId.SIX_MINUTE_XRAY)
    }

    // 8 Track Selector Grid (2 per row)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CurriculumTrackId.entries.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { track ->
                    val isSelected = selectedTrack == track
                    Surface(
                        color = if (isSelected) CyanPrimary.copy(alpha = 0.2f) else TacticalCard,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) CyanPrimary else TacticalBorder
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTrack = track }
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = track.shortBadge,
                                    color = AmberAlert,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = track.categoryType,
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = track.title,
                                color = if (isSelected) CyanPrimary else TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }

    // Track Description Header
    Surface(
        color = TacticalObsidian,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, TacticalBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(selectedTrack.title, color = CyanPrimary, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            Text(selectedTrack.description, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }

    // Lessons in Track
    val lessons = AcademyCatalog.lessons.filter { it.trackId == selectedTrack }
    lessons.forEach { lesson ->
        val isCompleted = appState.completedLessonIds.contains(lesson.id)
        Surface(
            color = TacticalSurface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(
                1.5.dp,
                if (isCompleted) EmeraldComfort.copy(alpha = 0.6f) else CyanPrimary.copy(alpha = 0.5f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${selectedTrack.shortBadge} • ${lesson.readTimeMinutes} MIN BRIEF",
                        color = AmberAlert,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isCompleted) "✓ MASTERED" else "+60 XP",
                        color = EmeraldComfort,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Text(
                    text = lesson.title,
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                lesson.corePrinciples.forEachIndexed { idx, principle ->
                    Text(
                        text = "${idx + 1}. $principle",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }

                Surface(
                    color = TacticalCard,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, TacticalBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("TACTICAL FIELD APPLICATION", color = CyanPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(lesson.tacticalApplication, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp))
                    }
                }

                Surface(
                    color = TacticalObsidian,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, EmeraldComfort.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                            Text(
                                text = "IMMEDIATE MICRO-DRILL:",
                                color = EmeraldComfort,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = lesson.fieldDrillPrompt,
                                color = TextPrimary,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                        OutlinedButton(
                            onClick = onNavigateToFieldOps,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, EmeraldComfort),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text("Field Ops ↗", color = EmeraldComfort, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                if (!isCompleted) {
                    Button(
                        onClick = { OpsRepository.markLessonCompleted(lesson.id) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmeraldComfort,
                            contentColor = TacticalObsidian
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Mark Module Mastered (+60 XP)", fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SrsFlashcardTrainerView(appState: PersistedAppState) {
    val now = PlatformServices.currentEpochMs()
    val allCards = appState.flashcards
    val dueCards = allCards.filter { it.nextReviewEpochMs <= now }

    var studyDueOnly by remember { mutableStateOf(dueCards.isNotEmpty()) }
    val activeDeck = if (studyDueOnly && dueCards.isNotEmpty()) dueCards else allCards

    var currentIndex by remember { mutableStateOf(0) }
    var revealed by remember { mutableStateOf(false) }

    // Mode Toggle: Due Only vs Study Ahead
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                studyDueOnly = true
                currentIndex = 0
                revealed = false
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (studyDueOnly) CyanPrimary else TacticalCard,
                contentColor = if (studyDueOnly) TacticalObsidian else TextPrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Due Today (${dueCards.size})", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
        Button(
            onClick = {
                studyDueOnly = false
                currentIndex = 0
                revealed = false
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!studyDueOnly) CyanPrimary else TacticalCard,
                contentColor = if (!studyDueOnly) TacticalObsidian else TextPrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Study Ahead / All (${allCards.size})", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }

    if (activeDeck.isEmpty()) {
        Surface(
            color = TacticalSurface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, EmeraldComfort),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("✓ All Caught Up!", color = EmeraldComfort, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text(
                    "You have 0 flashcards currently due for review according to your SM-2 intervals.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
                Button(
                    onClick = { studyDueOnly = false },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = TacticalObsidian),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Study Ahead (Review All Cards)", fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    val card = activeDeck[currentIndex % activeDeck.size]
    val isCardDueNow = card.nextReviewEpochMs <= now

    Surface(
        color = TacticalSurface,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, PurpleEllipsis),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${card.trackId.shortBadge} • #${(currentIndex % activeDeck.size) + 1}/${activeDeck.size}",
                    color = PurpleEllipsis,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                // Verification Pill (Tap to toggle)
                Surface(
                    color = if (card.isVerified) EmeraldComfort.copy(alpha = 0.2f) else AmberAlert.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, if (card.isVerified) EmeraldComfort else AmberAlert),
                    modifier = Modifier.clickable { OpsRepository.toggleCardVerified(card.id) }
                ) {
                    Text(
                        text = if (card.isVerified) "✓ VERIFIED FROM BOOK" else "⚠️ UNVERIFIED (TAP TO VERIFY)",
                        color = if (card.isVerified) EmeraldComfort else AmberAlert,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = card.frontPrompt,
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 25.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isCardDueNow) "Status: Due for review now" else "Next due in ${card.intervalDays}d",
                    color = if (isCardDueNow) AmberAlert else CyanPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Ease: ${(card.easeFactor * 10).toInt() / 10.0} • Reps: ${card.repetitions}",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }

            if (!revealed) {
                Button(
                    onClick = { revealed = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyanPrimary,
                        contentColor = TacticalObsidian
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Reveal Answer & Tactical Tip", fontWeight = FontWeight.ExtraBold)
                }
            } else {
                HorizontalDivider(color = TacticalBorder)
                Text(
                    text = card.backAnswer,
                    color = EmeraldComfort,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 22.sp
                )
                Surface(
                    color = TacticalObsidian,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, AmberAlert.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💡 Field Tip: ${card.tacticalTip}",
                        color = AmberAlert,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Text(
                    text = "GRADE RECALL (SM-2 INTERVAL SCHEDULER):",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RecallGrade.entries.forEach { grade ->
                        val btnColor = when (grade) {
                            RecallGrade.AGAIN -> CrimsonThreshold
                            RecallGrade.HARD -> AmberAlert
                            RecallGrade.GOOD -> CyanPrimary
                            RecallGrade.EASY -> EmeraldComfort
                        }
                        Button(
                            onClick = {
                                OpsRepository.gradeFlashcard(card.id, grade)
                                revealed = false
                                currentIndex = (currentIndex + 1) % activeDeck.size
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = btnColor,
                                contentColor = TacticalObsidian
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(grade.label, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                                Text(grade.subtitle, fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomNoteVaultView() {
    var selectedTrack by remember { mutableStateOf(CurriculumTrackId.ELLIPSIS_MANUAL) }
    var rawNotes by remember {
        mutableStateOf(
            "Mammalian Override: When you validate someone's core Social Need, the prefrontal defense shield drops within seconds.\n" +
            "Behavioral Momentum: Getting 3 minor agreements in a row increases compliance on the 4th request by over 60%."
        )
    }
    var statusMessage by remember { mutableStateOf("") }

    Surface(
        color = TacticalCard,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, TacticalBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "BOOK & COURSE NOTE IMPORTER (AUTO-VERIFIED)",
                color = CyanPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Paste text, transcripts, or personal notes directly from your Chase Hughes books or NCI course workbooks. Each line or 'Concept: Definition' pair becomes an active SM-2 Flashcard marked as [✓ BOOK VERIFIED].",
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )

            Text("Target Curriculum Track:", color = AmberAlert, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                CurriculumTrackId.entries.take(4).forEach { track ->
                    val isSelected = selectedTrack == track
                    Surface(
                        color = if (isSelected) CyanPrimary else TacticalObsidian,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, TacticalBorder),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTrack = track }
                    ) {
                        Text(
                            text = track.shortBadge,
                            color = if (isSelected) TacticalObsidian else TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = rawNotes,
                onValueChange = { rawNotes = it },
                label = { Text("Paste Book / Course Notes (1 concept per line)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Button(
                onClick = {
                    val added = OpsRepository.importCustomNotesToDeck(rawNotes, selectedTrack)
                    statusMessage = "✓ Generated $added new Verified SM-2 Flashcards into ${selectedTrack.shortBadge} (+${added * 25} XP)!"
                    rawNotes = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldComfort,
                    contentColor = TacticalObsidian
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Convert Notes into Verified SM-2 Flashcards", fontWeight = FontWeight.ExtraBold)
            }

            if (statusMessage.isNotBlank()) {
                Text(
                    text = statusMessage,
                    color = EmeraldComfort,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
