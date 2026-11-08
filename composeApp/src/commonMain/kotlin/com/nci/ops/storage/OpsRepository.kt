package com.nci.ops.storage

import com.nci.ops.data.AcademyCatalog
import com.nci.ops.data.FieldOpsCatalog
import com.nci.ops.model.*
import com.nci.ops.platform.HapticPulseType
import com.nci.ops.platform.PlatformServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class PersistedAppState(
    val totalXp: Int = 0,
    val streakDays: Int = 0,
    val lastActiveDate: String = "",
    val completedMissionIds: Set<String> = emptySet(),
    val completedLessonIds: Set<String> = emptySet(),
    val activeMissionId: String = "op_t1_01",
    val activeStealthTallies: List<Int> = listOf(0, 0, 0, 0),
    val debriefLogs: List<FieldDebriefLog> = emptyList(),
    val microHabits: List<MicroHabit> = FieldOpsCatalog.defaultMicroHabits,
    val skillScores: Map<SkillAxis, Int> = mapOf(
        SkillAxis.BASELINE_RECON to 0,
        SkillAxis.NONVERBAL_AUTHORITY to 0,
        SkillAxis.TRUST_RAPPORT to 0,
        SkillAxis.SIX_MX_PROFILING to 0,
        SkillAxis.BTE_DETECTION to 0,
        SkillAxis.ELLIPSIS_ELICITATION to 0
    ),
    val flashcards: List<SrsFlashcard> = AcademyCatalog.initialFlashcards,
    val dossiers: List<SubjectDossier> = emptyList()
)

object OpsRepository {
    private const val STORAGE_KEY = "nci_behavioral_ops_state_v2"

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val _state = MutableStateFlow(loadAndSanitizeInitialState())
    val state: StateFlow<PersistedAppState> = _state.asStateFlow()

    // Live BTE Scorer session state (in-memory during active observation)
    data class LiveBteSession(
        val baselineLocked: Boolean = false,
        val baselineSnapshot: BaselineSnapshot = BaselineSnapshot(),
        val stimulusNote: String = "",
        val stimulusDeliveredEpochMs: Long = 0L,
        val stimulusActiveUntilEpochMs: Long = 0L,
        val timedObservations: List<TimedCueObservation> = emptyList()
    ) {
        val isStimulusWindowActive: Boolean
            get() {
                val now = PlatformServices.currentEpochMs()
                return now in stimulusDeliveredEpochMs..stimulusActiveUntilEpochMs
            }

        val remainingStimulusMillis: Long
            get() {
                val now = PlatformServices.currentEpochMs()
                return (stimulusActiveUntilEpochMs - now).coerceAtLeast(0L)
            }

        val observedElementIdsInWindow: List<String>
            get() = timedObservations.filter { it.withinStimulusWindow }.map { it.elementId }

        val allObservedElementIds: List<String>
            get() = timedObservations.map { it.elementId }
    }

    private val _bteSession = MutableStateFlow(LiveBteSession())
    val bteSession: StateFlow<LiveBteSession> = _bteSession.asStateFlow()

    private fun loadAndSanitizeInitialState(): PersistedAppState {
        val loaded = try {
            val raw = PlatformServices.loadString(STORAGE_KEY)
            if (!raw.isNullOrBlank()) {
                json.decodeFromString<PersistedAppState>(raw)
            } else {
                PersistedAppState()
            }
        } catch (_: Throwable) {
            PersistedAppState()
        }

        // Daily Midnight Reset Check & Streak Validation
        val today = PlatformServices.currentDateString()
        return if (loaded.lastActiveDate.isNotEmpty() && loaded.lastActiveDate != today) {
            // New calendar day: reset daily micro-habit reps
            val resetHabits = loaded.microHabits.map { it.copy(currentRepsToday = 0) }
            val updated = loaded.copy(
                lastActiveDate = today,
                microHabits = resetHabits
            )
            persist(updated)
            updated
        } else if (loaded.lastActiveDate.isEmpty()) {
            loaded.copy(lastActiveDate = today)
        } else {
            loaded
        }
    }

    private fun persist(newState: PersistedAppState) {
        try {
            PlatformServices.saveString(STORAGE_KEY, json.encodeToString(newState))
        } catch (_: Throwable) {
        }
    }

    private fun updateAndPersist(transform: (PersistedAppState) -> PersistedAppState) {
        _state.update { current ->
            val updated = transform(current)
            persist(updated)
            updated
        }
    }

    // ==========================================
    // PROGRESSIVE TIER LOCKING & GATING LOGIC
    // ==========================================
    fun isTierUnlocked(tier: FieldOpTier, appState: PersistedAppState = _state.value): Boolean {
        if (tier.tierNumber == 1) return true
        val prereqTier = FieldOpTier.entries.find { it.tierNumber == tier.tierNumber - 1 } ?: return true
        val prereqMissions = FieldOpsCatalog.missions.filter { it.tier == prereqTier }.map { it.id }.toSet()
        val completedPrereqLogs = appState.debriefLogs.filter { prereqMissions.contains(it.missionId) }

        if (completedPrereqLogs.size < tier.requiredPrereqCompletions) return false
        val avgConfidence = completedPrereqLogs.map { it.confidenceScore }.average()
        return avgConfidence >= tier.requiredAvgConfidence
    }

    fun getTierUnlockStatusDescription(tier: FieldOpTier, appState: PersistedAppState = _state.value): String {
        if (tier.tierNumber == 1) return "Unlocked"
        val prereqTier = FieldOpTier.entries.find { it.tierNumber == tier.tierNumber - 1 } ?: return "Unlocked"
        val prereqMissions = FieldOpsCatalog.missions.filter { it.tier == prereqTier }.map { it.id }.toSet()
        val completedPrereqLogs = appState.debriefLogs.filter { prereqMissions.contains(it.missionId) }

        val doneCount = completedPrereqLogs.size
        val avgConfidence = if (doneCount > 0) completedPrereqLogs.map { it.confidenceScore }.average() else 0.0
        val formattedAvg = (avgConfidence * 10).toInt() / 10.0

        return if (isTierUnlocked(tier, appState)) {
            "✓ Unlocked ($doneCount ops completed, Avg Conf: $formattedAvg/10)"
        } else {
            "🔒 Locked: Complete ${tier.requiredPrereqCompletions} ${prereqTier.title.substringBefore(":")} ops with ≥${tier.requiredAvgConfidence}/10 confidence (Current: $doneCount completed, Avg Conf: $formattedAvg/10)"
        }
    }

    // ==========================================
    // FIELD OPS & STEALTH HAPTIC TALLY
    // ==========================================
    fun selectActiveMission(missionId: String) {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        updateAndPersist {
            it.copy(
                activeMissionId = missionId,
                activeStealthTallies = listOf(0, 0, 0, 0)
            )
        }
    }

    fun incrementStealthQuadrant(quadrantIndex: Int) {
        val pulse = when (quadrantIndex) {
            0 -> HapticPulseType.LIGHT_TICK
            1, 2 -> HapticPulseType.DOUBLE_PULSE
            else -> HapticPulseType.HEAVY_ALERT
        }
        PlatformServices.triggerHaptic(pulse)
        updateAndPersist { current ->
            val list = current.activeStealthTallies.toMutableList()
            if (quadrantIndex in list.indices) {
                list[quadrantIndex] = list[quadrantIndex] + 1
            }
            current.copy(activeStealthTallies = list)
        }
    }

    fun resetStealthTallies() {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        updateAndPersist {
            it.copy(activeStealthTallies = listOf(0, 0, 0, 0))
        }
    }

    fun submitFieldDebrief(
        mission: FieldMission,
        confidenceScore: Int,
        baselineObserved: String,
        harvestedPhrases: String,
        reflectionNotes: String
    ) {
        PlatformServices.triggerHaptic(HapticPulseType.HEAVY_ALERT)
        val now = PlatformServices.currentEpochMs()
        val today = PlatformServices.currentDateString()

        updateAndPersist { current ->
            val newLog = FieldDebriefLog(
                id = "log_${current.debriefLogs.size + 1}_$now",
                missionId = mission.id,
                missionTitle = "${mission.codeName}: ${mission.title}",
                timestampLabel = "Field Op #${current.debriefLogs.size + 1}",
                timestampEpochMs = now,
                quadrantCounts = current.activeStealthTallies,
                confidenceScore = confidenceScore,
                baselineObserved = baselineObserved.ifBlank { "Standard environmental baseline logged" },
                harvestedPhrases = harvestedPhrases,
                reflectionNotes = reflectionNotes
            )
            val updatedSkills = current.skillScores.toMutableMap()
            val prevSkill = updatedSkills[mission.skillAxisAffected] ?: 0
            updatedSkills[mission.skillAxisAffected] = (prevSkill + 15).coerceAtMost(100)

            val updatedStreak = if (current.lastActiveDate == today && current.streakDays > 0) {
                current.streakDays
            } else {
                current.streakDays + 1
            }

            current.copy(
                totalXp = current.totalXp + mission.xpReward,
                streakDays = updatedStreak,
                lastActiveDate = today,
                completedMissionIds = current.completedMissionIds + mission.id,
                activeStealthTallies = listOf(0, 0, 0, 0),
                debriefLogs = listOf(newLog) + current.debriefLogs,
                skillScores = updatedSkills
            )
        }
    }

    // ==========================================
    // AUTOMATICITY / MICRO-HABIT REPS
    // ==========================================
    fun incrementMicroHabitRep(habitId: String) {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        val today = PlatformServices.currentDateString()

        updateAndPersist { current ->
            var bonusXp = 15
            val updatedHabits = current.microHabits.map { habit ->
                if (habit.id == habitId) {
                    val nextToday = habit.currentRepsToday + 1
                    if (nextToday == habit.dailyTargetReps) {
                        PlatformServices.triggerHaptic(HapticPulseType.HEAVY_ALERT)
                        bonusXp = 50
                    }
                    habit.copy(
                        currentRepsToday = nextToday,
                        totalLifetimeReps = habit.totalLifetimeReps + 1,
                        lastCompletedDateString = today
                    )
                } else {
                    habit
                }
            }
            val updatedSkills = current.skillScores.toMutableMap()
            updatedSkills[SkillAxis.NONVERBAL_AUTHORITY] =
                ((updatedSkills[SkillAxis.NONVERBAL_AUTHORITY] ?: 0) + 2).coerceAtMost(100)

            current.copy(
                totalXp = current.totalXp + bonusXp,
                lastActiveDate = today,
                microHabits = updatedHabits,
                skillScores = updatedSkills
            )
        }
    }

    // ==========================================
    // RIGOROUS 7-SECOND BTE SCORER ENGINE
    // ==========================================
    fun lockBaseline(snapshot: BaselineSnapshot) {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        _bteSession.update {
            it.copy(
                baselineLocked = true,
                baselineSnapshot = snapshot.copy(skipped = false)
            )
        }
    }

    fun skipBaselineHotStart() {
        PlatformServices.triggerHaptic(HapticPulseType.DOUBLE_PULSE)
        _bteSession.update {
            it.copy(
                baselineLocked = true,
                baselineSnapshot = BaselineSnapshot(
                    skipped = true,
                    blinkRate = "Uncalibrated (Hot Start)",
                    handPosture = "Uncalibrated",
                    torsoOrientation = "Uncalibrated",
                    vocalPacePitch = "Uncalibrated"
                )
            )
        }
    }

    fun start7SecondStimulusWindow(stimulusProbeNote: String = "") {
        val now = PlatformServices.currentEpochMs()
        PlatformServices.triggerHaptic(HapticPulseType.DOUBLE_PULSE)
        _bteSession.update {
            it.copy(
                stimulusNote = stimulusProbeNote,
                stimulusDeliveredEpochMs = now,
                stimulusActiveUntilEpochMs = now + 7000L
            )
        }
    }

    fun cancelStimulusWindow() {
        _bteSession.update {
            it.copy(
                stimulusActiveUntilEpochMs = 0L
            )
        }
    }

    fun toggleBteObservation(element: BteElement) {
        val now = PlatformServices.currentEpochMs()
        _bteSession.update { session ->
            val isDuringStimulus = session.isStimulusWindowActive
            val existingObs = session.timedObservations.find { it.elementId == element.id }

            val nextObservations = if (existingObs != null) {
                PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
                session.timedObservations.filterNot { it.elementId == element.id }
            } else {
                val newObs = TimedCueObservation(
                    elementId = element.id,
                    observedEpochMs = now,
                    withinStimulusWindow = isDuringStimulus
                )
                val pulse = if (element.pointValue >= 4) HapticPulseType.HEAVY_ALERT else HapticPulseType.DOUBLE_PULSE
                PlatformServices.triggerHaptic(pulse)
                session.timedObservations + newObs
            }

            session.copy(timedObservations = nextObservations)
        }
    }

    fun resetBteSession() {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        _bteSession.value = LiveBteSession()
    }

    // ==========================================
    // SUBJECT DOSSIERS
    // ==========================================
    fun saveDossier(dossier: SubjectDossier) {
        PlatformServices.triggerHaptic(HapticPulseType.HEAVY_ALERT)
        val now = PlatformServices.currentEpochMs()
        updateAndPersist { current ->
            val updatedSkills = current.skillScores.toMutableMap()
            updatedSkills[SkillAxis.SIX_MX_PROFILING] =
                ((updatedSkills[SkillAxis.SIX_MX_PROFILING] ?: 0) + 10).coerceAtMost(100)

            current.copy(
                totalXp = current.totalXp + 100,
                dossiers = listOf(dossier.copy(createdEpochMs = now)) + current.dossiers.filterNot { it.id == dossier.id },
                skillScores = updatedSkills
            )
        }
    }

    fun deleteDossier(dossierId: String) {
        updateAndPersist { current ->
            current.copy(dossiers = current.dossiers.filterNot { it.id == dossierId })
        }
    }

    // ==========================================
    // ACADEMY & SM-2 SPACED REPETITION ENGINE
    // ==========================================
    fun markLessonCompleted(lessonId: String) {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        updateAndPersist { current ->
            if (current.completedLessonIds.contains(lessonId)) {
                current
            } else {
                current.copy(
                    totalXp = current.totalXp + 60,
                    completedLessonIds = current.completedLessonIds + lessonId
                )
            }
        }
    }

    fun gradeFlashcard(cardId: String, grade: RecallGrade) {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        val now = PlatformServices.currentEpochMs()

        updateAndPersist { current ->
            val maxDue = current.flashcards.maxOfOrNull { it.dueOrder } ?: 10
            val updatedCards = current.flashcards.map { card ->
                if (card.id == cardId) {
                    SrsEngine.reviewCard(card, grade, now, maxDue)
                } else {
                    card
                }
            }

            current.copy(
                totalXp = current.totalXp + 20,
                flashcards = updatedCards
            )
        }
    }

    fun toggleCardVerified(cardId: String) {
        PlatformServices.triggerHaptic(HapticPulseType.LIGHT_TICK)
        updateAndPersist { current ->
            val updated = current.flashcards.map { card ->
                if (card.id == cardId) {
                    card.copy(isVerified = !card.isVerified)
                } else {
                    card
                }
            }
            current.copy(flashcards = updated)
        }
    }

    fun importCustomNotesToDeck(rawNotes: String, track: CurriculumTrackId): Int {
        val now = PlatformServices.currentEpochMs()
        val currentCards = _state.value.flashcards
        val newCards = SrsEngine.parseNotesIntoFlashcards(rawNotes, track, currentCards.size, now)
        if (newCards.isEmpty()) return 0
        PlatformServices.triggerHaptic(HapticPulseType.HEAVY_ALERT)
        updateAndPersist { current ->
            current.copy(
                totalXp = current.totalXp + (newCards.size * 25),
                flashcards = newCards + current.flashcards
            )
        }
        return newCards.size
    }
}
