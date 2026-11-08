package com.nci.ops.model

import kotlinx.serialization.Serializable

/**
 * Behavioral Table of Elements (BTE) Categories from The Behavior Operations Manual & Ellipsis Manual.
 */
enum class BteCategory(val displayName: String, val zoneCode: String) {
    HEAD_FACE("Head & Face", "HF"),
    EYES_OCULAR("Eyes & Ocular", "EO"),
    MOUTH_LIPS("Mouth & Lips", "ML"),
    TORSO_POSTURE("Torso & Posture", "TP"),
    HANDS_FINGERS("Hands & Fingers", "HN"),
    LEGS_FEET("Legs & Feet", "LF"),
    VOCAL_LINGUISTIC("Vocal & Linguistic", "VL"),
    AUTONOMIC("Autonomic & Respiration", "AU")
}

enum class CuePolarity(val label: String) {
    DISCOMFORT_STRESS("Discomfort / Stress / Deception"),
    COMFORT_RAPPORT("Comfort / Homeostasis / Openness"),
    CONTEXT_DEPENDENT("Context / Baseline Shift")
}

@Serializable
data class BteElement(
    val id: String,
    val symbol: String,             // e.g., "Lp", "Vc", "Vd", "Br", "Sh"
    val atomicNumber: Int,
    val name: String,
    val category: BteCategory,
    val pointValue: Int,            // BTE Stress/Deception weight (1 - 5, or 0 for comfort cue)
    val polarity: CuePolarity,
    val baselineNote: String,
    val deviationMeaning: String,
    val psychologyMechanism: String,
    val commonClusters: List<String>,
    val bookSource: String,         // e.g., "Behavior Ops Manual / Ellipsis Manual"
    val isVerified: Boolean = false // Flagged when verified directly against user's physical book/course materials
)

@Serializable
data class BaselineSnapshot(
    val skipped: Boolean = false,
    val blinkRate: String = "Normal (12-18/min)",
    val handPosture: String = "Relaxed / Open",
    val torsoOrientation: String = "Ventral Fronting",
    val vocalPacePitch: String = "Steady / Normal Pitch",
    val footPlacement: String = "Planted / Neutral",
    val lockedAtEpochMs: Long = 0L
)

@Serializable
data class TimedCueObservation(
    val elementId: String,
    val observedEpochMs: Long,
    val withinStimulusWindow: Boolean
)

@Serializable
data class BteTimedScenario(
    val id: String,
    val title: String,
    val context: String,
    val baselineDescription: String,
    val stimulusQuestion: String,
    val observedCueSymbols: List<String>,
    val totalBteScore: Int,
    val crossesThreshold: Boolean,  // True if >= 11 within 7 seconds of stimulus
    val analysisExplanation: String
)

/**
 * 5-Tier Progressive Field Operations ("Confidence & Skill Ladder")
 */
enum class FieldOpTier(
    val tierNumber: Int,
    val title: String,
    val subtitle: String,
    val requiredPrereqCompletions: Int = 0,
    val requiredAvgConfidence: Int = 0
) {
    TIER_1_RECON(
        1,
        "Tier 1: Zero-Contact Recon",
        "Passive baseline & social need spotting outside of conversation",
        requiredPrereqCompletions = 0,
        requiredAvgConfidence = 0
    ),
    TIER_2_CALIBRATION(
        2,
        "Tier 2: Nonverbal Calibration",
        "Zero/minimal-word body language, authority & warmth adjustments",
        requiredPrereqCompletions = 2,
        requiredAvgConfidence = 7
    ),
    TIER_3_MICRO_RAPPORT(
        3,
        "Tier 3: Micro-Rapport & Trust Code",
        "15–60 second low-stakes interactions to build instant warmth",
        requiredPrereqCompletions = 2,
        requiredAvgConfidence = 7
    ),
    TIER_4_ELICITATION_6MX(
        4,
        "Tier 4: 6MX Profiling & Elicitation",
        "2–6 minute conversations diagnosing Social Needs without direct questions",
        requiredPrereqCompletions = 2,
        requiredAvgConfidence = 7
    ),
    TIER_5_ELLIPSIS_OPS(
        5,
        "Tier 5: Advanced Ellipsis Operations",
        "FATE model, Compliance Ladder, Linguistic Harvesting & BTE tracking",
        requiredPrereqCompletions = 2,
        requiredAvgConfidence = 8
    )
}

@Serializable
data class TargetBehaviorGuide(
    val quadrantIndex: Int,              // 0..3 matching the Stealth Tally Quadrant
    val cueName: String,                 // e.g., "Ventral Denial / Barrier"
    val quickHint: String,               // Brief 1-line field reference hint
    val physicalTells: List<String>,     // Anatomical tells: torso, hands, feet, eyes, voice
    val realWorldExample: String,        // Real-world operational scene description
    val psychologicalWhy: String,        // Limbic mechanism / Chase Hughes behavioral rationale
    val falsePositivesToAvoid: String,   // What NOT to confuse this with
    val relatedBteSymbol: String? = null // Cross-reference badge (e.g., "Vd", "Lp", "Fc", "Ef")
)

@Serializable
data class FieldMission(
    val id: String,
    val tier: FieldOpTier,
    val codeName: String,
    val title: String,
    val sourceBookOrCourse: String,
    val durationEstimate: String,
    val objective: String,
    val preFlightBriefing: List<String>,
    val stealthTallyLabels: List<String>, // 4 quadrant labels for No-Look Haptic Stealth Tally
    val successCriteria: String,
    val debriefPrompt: String,
    val xpReward: Int,
    val skillAxisAffected: SkillAxis,
    val targetBehaviors: List<TargetBehaviorGuide> = emptyList()
)

enum class SkillAxis(val displayName: String) {
    BASELINE_RECON("Baseline & Recon"),
    NONVERBAL_AUTHORITY("Nonverbal & Authority"),
    TRUST_RAPPORT("Trust & Rapport"),
    SIX_MX_PROFILING("6MX Social Needs"),
    BTE_DETECTION("BTE & Deception"),
    ELLIPSIS_ELICITATION("Elicitation & Influence")
}

@Serializable
data class FieldDebriefLog(
    val id: String,
    val missionId: String,
    val missionTitle: String,
    val timestampLabel: String,
    val timestampEpochMs: Long = 0L,
    val quadrantCounts: List<Int>,
    val confidenceScore: Int,       // 1 - 10
    val baselineObserved: String,
    val harvestedPhrases: String,
    val reflectionNotes: String
)

@Serializable
data class MicroHabit(
    val id: String,
    val title: String,
    val source: String,
    val whyItMatters: String,
    val dailyTargetReps: Int,
    val currentRepsToday: Int = 0,
    val totalLifetimeReps: Int = 0,
    val lastCompletedDateString: String = ""
)

/**
 * 30-Second Pre-Meeting Pocket Cue Cards & 6MX Human Compass
 */
enum class SocialNeedType(
    val title: String,
    val shortCode: String,
    val coreDriver: String,
    val fearAvoidance: String
) {
    SIGNIFICANCE("Significance", "SIG", "To be seen as special, respected, elite, and irreplaceable", "Being ignored, average, or disrespected"),
    APPROVAL("Approval", "APP", "To be liked, validated, praised, and affirmed by others", "Criticism, rejection, or disapproval"),
    ACCEPTANCE("Acceptance", "ACC", "To belong safely within the tribe/group without standing out awkwardly", "Ostracism, exclusion, or standing out negatively"),
    INTELLIGENCE("Intelligence", "INT", "To be recognized as the smartest, most insightful analytical mind", "Looking foolish, uninformed, or outsmarted"),
    PITY("Pity", "PIT", "To receive sympathy, rescuing, and exemption from harsh expectations", "Having struggles minimized or being told 'everyone goes through that'"),
    POWER("Power", "POW", "To hold control, autonomy, dominance, and decision-making authority", "Feeling controlled, cornered, or subordinate")
}

enum class DecisionStyleType(val title: String, val coreRule: String) {
    ANALYTICAL("Analytical", "Decides through data, logic, specs, and risk reduction"),
    BELIEF("Belief / Principle", "Decides based on core values, morals, and 'how things should be'"),
    LEADER("Leader / Pioneer", "Decides to be first, set the trend, and hold competitive edge"),
    SOCIAL("Social Proof", "Decides based on what respected peers or the tribe are doing"),
    NECESSITY("Necessity / Pragmatic", "Decides only when urgent need or clear utility demands it"),
    IMPULSIVE("Impulsive / Emotional", "Decides rapidly based on novelty, excitement, and immediate feeling")
}

@Serializable
data class PocketCueCard(
    val id: String,
    val category: String,           // "6MX Social Needs", "6MX Decision Styles", "Ellipsis FATE", "Trust Code", "Authority Pyramid"
    val title: String,
    val badgeText: String,
    val thirtySecondSummary: String,
    val visualAndVerbalTells: List<String>,
    val exactPhrasesToUse: List<String>,
    val fatalErrorsToAvoid: List<String>,
    val bodyLanguageCalibration: String,
    val isVerified: Boolean = false
)

@Serializable
data class SubjectDossier(
    val id: String,
    val alias: String,
    val contextOrRole: String,
    val primarySocialNeed: SocialNeedType,
    val secondarySocialNeed: SocialNeedType,
    val decisionStyle: DecisionStyleType,
    val baselineNotes: String,
    val harvestedPredicates: String,
    val trustWobbleNotes: String,
    val createdEpochMs: Long = 0L
)

/**
 * 8-Track Academy & SM-2 Spaced Repetition Flashcards
 */
enum class CurriculumTrackId(
    val title: String,
    val shortBadge: String,
    val categoryType: String,
    val description: String
) {
    SIX_MINUTE_XRAY(
        "Six-Minute X-Ray (Book & 6MX)",
        "6MX",
        "Book + Course",
        "Rapid human profiling: The 6 Social Needs, 6 Decision Styles, Focus Profiles, and Elicitation without questions."
    ),
    BEHAVIOR_OPS_MANUAL(
        "The Behavior Operations Manual",
        "BOM",
        "Book",
        "Neuro-cognitive intelligence, the Behavioral Table of Elements (BTE), 7-Second Rule, and >=11 Cluster Scoring."
    ),
    ELLIPSIS_MANUAL(
        "The Ellipsis Manual",
        "ELP",
        "Book",
        "Engineering & hacking human behavior: FATE Model, Authority Pyramid, Compliance Ladder, and Linguistic Harvesting."
    ),
    TRUST_CODE(
        "The Trust Code",
        "TRC",
        "Book",
        "Neuroscience of oxytocin vs. cortisol, the Authenticity-Empathy-Logic Triad, and eliminating Trust Wobbles."
    ),
    BEHAVIOR_PRACTITIONER(
        "The Behavior Practitioner Course",
        "BPC",
        "Course",
        "Applied behavioral diagnostics, motivational mapping, covert rapport, and ethical behavioral intervention."
    ),
    NCI_LEVEL_1(
        "NCI Level 1: Nonverbal Foundations",
        "NCI-1",
        "Course",
        "Mammalian vs. Reptilian brain mechanics, establishing baselines, comfort vs. discomfort, and barrier reading."
    ),
    NCI_LEVEL_2(
        "NCI Level 2: Elicitation & Statement Analysis",
        "NCI-2",
        "Course",
        "Advanced conversational elicitation, pronoun/tense shifts, equivocation, and deep Social Needs leverage."
    ),
    NCI_LEVEL_3_4(
        "NCI Levels 3 & 4: Mastery & Interrogation",
        "NCI-3/4",
        "Course",
        "High-stakes multi-channel BTE cluster analysis, cognitive load interviewing, and covert influence orchestration."
    )
}

@Serializable
data class AcademyLesson(
    val id: String,
    val trackId: CurriculumTrackId,
    val title: String,
    val readTimeMinutes: Int,
    val corePrinciples: List<String>,
    val tacticalApplication: String,
    val fieldDrillPrompt: String,
    val isVerified: Boolean = false
)

@Serializable
data class SrsFlashcard(
    val id: String,
    val trackId: CurriculumTrackId,
    val frontPrompt: String,
    val backAnswer: String,
    val tacticalTip: String,
    // SM-2 state
    val intervalDays: Int = 0,
    val repetitions: Int = 0,
    val easeFactor: Double = 2.5,
    val dueOrder: Int = 0,
    val nextReviewEpochMs: Long = 0L,
    val lastReviewedEpochMs: Long = 0L,
    val isCustom: Boolean = false,
    val isVerified: Boolean = false
)
