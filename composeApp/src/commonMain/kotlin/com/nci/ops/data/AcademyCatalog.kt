package com.nci.ops.data

import com.nci.ops.model.AcademyLesson
import com.nci.ops.model.CurriculumTrackId
import com.nci.ops.model.SrsFlashcard

object AcademyCatalog {

    val lessons: List<AcademyLesson> = listOf(
        // TRACK 1: SIX-MINUTE X-RAY (BOOK & 6MX COURSE)
        AcademyLesson(
            id = "les_6mx_1",
            trackId = CurriculumTrackId.SIX_MINUTE_XRAY,
            title = "The Human Compass: 6 Social Needs & Homeostasis",
            readTimeMinutes = 5,
            corePrinciples = listOf(
                "Every human operates with 2 dominant Social Needs out of 6: Significance, Approval, Acceptance, Intelligence, Pity, and Power.",
                "We habitually communicate using OUR own Social Need (e.g., an Intelligence type tries to impress a Significance type with raw data—and fails).",
                "When you accurately diagnose and feed someone's Primary Social Need, their nervous system experiences 'Homeostasis'—a visible physical sigh or relaxation response."
            ),
            tacticalApplication = "In the first 3 minutes of any conversation, stop trying to be interesting. Listen for what the subject boasts about, complains about, or defends—that points directly to their Primary Social Need.",
            fieldDrillPrompt = "Identify the Primary + Secondary Social Need of the next two people you speak with today."
        ),
        AcademyLesson(
            id = "les_6mx_2",
            trackId = CurriculumTrackId.SIX_MINUTE_XRAY,
            title = "The 6 Decision Styles & Focus Profiles",
            readTimeMinutes = 4,
            corePrinciples = listOf(
                "A person's Social Need is WHY they act; their Decision Style is HOW they justify saying yes.",
                "The 6 Decision Styles: Analytical (data/specs), Belief (values/morals), Leader (first/pioneer), Social (peer proof), Necessity (urgent utility), and Impulsive (immediate feeling).",
                "Pairing Social Need + Decision Style creates a complete 6MX Behavioral Map (e.g., Significance + Leader vs. Acceptance + Social)."
            ),
            tacticalApplication = "Frame the emotional hook around their Social Need, and frame the logical structure of your proposal around their Decision Style.",
            fieldDrillPrompt = "Before pitching an idea today, ask yourself: 'Is this person an Analytical decider, a Social Proof decider, or a Leader decider?'"
        ),

        // TRACK 2: THE BEHAVIOR OPERATIONS MANUAL
        AcademyLesson(
            id = "les_bom_1",
            trackId = CurriculumTrackId.BEHAVIOR_OPS_MANUAL,
            title = "The 7-Second Rule & The >= 11 BTE Threshold",
            readTimeMinutes = 6,
            corePrinciples = listOf(
                "Single isolated body language cues mean nothing—there is no 'Pinocchio nose'.",
                "In the Behavioral Table of Elements (BTE), a deception or high-stress indicator is only valid when: 1) It deviates from the subject's baseline, 2) It occurs within 0–7 seconds of a specific stimulus, and 3) A cluster of cues sums to >= 11 points.",
                "Always separate environmental discomfort (cold room, tight shoes) from stimulus-locked psychological discomfort."
            ),
            tacticalApplication = "Establish a 30-second baseline during harmless small talk (or activate Hot-Start multi-channel mode if dropped straight into action). Only score cues that fire within 7 seconds of your probe.",
            fieldDrillPrompt = "Practice watching the 7-second window immediately after you ask a direct question."
        ),

        // TRACK 3: THE ELLIPSIS MANUAL
        AcademyLesson(
            id = "les_elp_1",
            trackId = CurriculumTrackId.ELLIPSIS_MANUAL,
            title = "The FATE Model & The Authority Pyramid",
            readTimeMinutes = 6,
            corePrinciples = listOf(
                "FATE stands for Focus, Authority, Tribe, and Emotion—the four evolutionary levers that bypass neocortical resistance.",
                "The Authority Pyramid ranks how humans evaluate status: Behavior/Kinematics sits at the core—moving 20% slower, maintaining stillness, and using downward vocal inflections override superficial dress.",
                "The Compliance Ladder builds momentum through tiny, frictionless micro-agreements before requesting a major behavioral shift."
            ),
            tacticalApplication = "Audit your own Authority kinematics upon entering a room: decelerate your movements by 20%, keep your hands visible with relaxed fingers, and pause 0.5s before answering.",
            fieldDrillPrompt = "Execute a 3-step Compliance Ladder in a routine conversation today."
        ),
        AcademyLesson(
            id = "les_elp_2",
            trackId = CurriculumTrackId.ELLIPSIS_MANUAL,
            title = "Linguistic Harvesting & Spatial Anchoring",
            readTimeMinutes = 5,
            corePrinciples = listOf(
                "When someone uses a distinctive adjective or metaphor ('It felt like walking through quicksand'), that word is neurally wired to their emotional experience.",
                "Harvesting that exact word and echoing it back later creates profound subconscious rapport ('They truly understand my world').",
                "Spatial Anchoring assigns positive topics to one physical gesture zone (e.g., your right hand/side) and problems/competitors to a distant zone."
            ),
            tacticalApplication = "Write down or mentally lock 3 exact sensory words your conversation partner uses, and feed them back verbatim within 3 minutes.",
            fieldDrillPrompt = "Harvest and echo 3 exact words in your next meeting."
        ),

        // TRACK 4: THE TRUST CODE
        AcademyLesson(
            id = "les_trc_1",
            trackId = CurriculumTrackId.TRUST_CODE,
            title = "Oxytocin vs. Cortisol & The Trust Wobble Triad",
            readTimeMinutes = 5,
            corePrinciples = listOf(
                "Trust is a biological state governed by oxytocin (safety/connection) vs. cortisol (vigilance/defense).",
                "The brain evaluates trust across three pillars: Authenticity, Empathy, and Logic.",
                "A 'Trust Wobble' is the weakest pillar in the moment. If someone doubts your Empathy, giving them more Logic only increases their distrust."
            ),
            tacticalApplication = "Use 'Controlled Vulnerability' (admitting a small, human imperfection early) to eliminate Authenticity Wobbles and trigger rapid oxytocin.",
            fieldDrillPrompt = "Identify which pillar (Authenticity, Empathy, or Logic) is your default strength and which one wobbles under stress."
        ),

        // TRACK 5: THE BEHAVIOR PRACTITIONER COURSE
        AcademyLesson(
            id = "les_bpc_1",
            trackId = CurriculumTrackId.BEHAVIOR_PRACTITIONER,
            title = "Applied Behavioral Diagnostics & Resistance Removal",
            readTimeMinutes = 5,
            corePrinciples = listOf(
                "Psychological reactance occurs whenever a subject feels their autonomy is threatened.",
                "Instead of pushing against resistance, validate the protective function of their hesitation and preserve their autonomy ('You're 100% free to walk away from this...').",
                "Map a subject's beliefs, fears, and social needs before attempting any behavioral intervention."
            ),
            tacticalApplication = "When someone resists an idea, immediately grant them total autonomy ('Ultimately this is 100% your call') and watch their defensive posture drop.",
            fieldDrillPrompt = "Use an autonomy-granting phrase the next time you sense hesitation."
        ),

        // TRACK 6: NCI LEVEL 1
        AcademyLesson(
            id = "les_nci1_1",
            trackId = CurriculumTrackId.NCI_LEVEL_1,
            title = "NCI Level 1: Mammalian Comfort vs. Discomfort & Barriers",
            readTimeMinutes = 5,
            corePrinciples = listOf(
                "Forget 'body language dictionaries'. Focus first on one binary question: Is this person's mammalian brain in Comfort (openness/gravitation) or Discomfort (withdrawal/barriers)?",
                "Ventral Fronting (exposing heart/stomach) and Eyebrow Flashes indicate comfort.",
                "Ventral Denial (blading away, placing coffee cups/laptops across the chest, ankle locks, lint-picking) indicates immediate psychological discomfort."
            ),
            tacticalApplication = "Track when a conversation partner places or removes physical barriers (coffee cup, arms, phone) between their sternum and yours.",
            fieldDrillPrompt = "Notice 3 barrier placements or removals today and note the exact topic being discussed."
        ),

        // TRACK 7: NCI LEVEL 2
        AcademyLesson(
            id = "les_nci2_1",
            trackId = CurriculumTrackId.NCI_LEVEL_2,
            title = "NCI Level 2: Conversational Elicitation & Statement Analysis",
            readTimeMinutes = 6,
            corePrinciples = listOf(
                "Direct interrogation questions trigger guarded, rehearsed answers. Elicitation statements exploit the human compulsion to correct, clarify, or top a story.",
                "In Statement Analysis, watch for Pronoun Drops ('Went to the warehouse' instead of 'I went'), Tense Shifts (past to present tense during fabricated segments), and Equivocation hedges ('To tell you the truth', 'Basically')."
            ),
            tacticalApplication = "Replace 'How did that project go?' with 'I heard projects like that usually run into huge supply-chain delays' and let them correct you with the full truth.",
            fieldDrillPrompt = "Go 5 minutes in a conversation using zero questions—only statements and curiosity pauses."
        ),

        // TRACK 8: NCI LEVELS 3 & 4
        AcademyLesson(
            id = "les_nci34_1",
            trackId = CurriculumTrackId.NCI_LEVEL_3_4,
            title = "NCI Levels 3 & 4: Cognitive Load & Multi-Channel Cluster Mastery",
            readTimeMinutes = 7,
            corePrinciples = listOf(
                "Truth-tellers retrieve memories; liars must simultaneously fabricate a story, monitor your reaction, and suppress the truth.",
                "This high cognitive load starves the motor cortex: hand illustrators freeze (Fd), blink rate shifts (Br), unilateral shoulder shrugs leak (Sh), and micro head-shakes contradict verbal 'Yes' statements (Ci).",
                "Asking an unexpected reverse-chronological or spatial question ('Where were you standing when that happened?') overwhelms a fabricated script."
            ),
            tacticalApplication = "Look for incongruence between channels: when words say 'I'm 100% confident' while a unilateral shoulder shrug (Sh=5) and lip compression (Lp=4) fire simultaneously.",
            fieldDrillPrompt = "Run 3 timed BTE threshold scenarios in the BTE Lab to sharpen your 7-second multi-channel scoring."
        )
    )

    val initialFlashcards: List<SrsFlashcard> = listOf(
        SrsFlashcard(
            id = "fc_01",
            trackId = CurriculumTrackId.BEHAVIOR_OPS_MANUAL,
            frontPrompt = "What are the 3 mandatory conditions for a valid BTE deception/stress cluster in The Behavior Operations Manual?",
            backAnswer = "1) Must deviate from established baseline\n2) Must occur within the 7-second window after a stimulus\n3) Cluster score must reach >= 11 BTE points across multiple channels.",
            tacticalTip = "Never score an isolated cue outside the 7-second post-stimulus window.",
            dueOrder = 1
        ),
        SrsFlashcard(
            id = "fc_02",
            trackId = CurriculumTrackId.SIX_MINUTE_XRAY,
            frontPrompt = "Name all 6 Social Needs in Six-Minute X-Ray (6MX) and how Acceptance differs from Significance.",
            backAnswer = "The 6 Needs: Significance, Approval, Acceptance, Intelligence, Pity, Power.\n\nDifference: Significance wants to stand OUT as elite/special; Acceptance wants to fit IN safely with the tribe without being singled out.",
            tacticalTip = "Praising an Acceptance person loudly in front of a crowd actually causes them stress!",
            dueOrder = 2
        ),
        SrsFlashcard(
            id = "fc_03",
            trackId = CurriculumTrackId.ELLIPSIS_MANUAL,
            frontPrompt = "What does the FATE acronym stand for in The Ellipsis Manual?",
            backAnswer = "F — Focus (capturing attention / pattern interrupt)\nA — Authority (calm kinematics, vocal gravity, certainty)\nT — Tribe (shared 'We/Us' identity)\nE — Emotion (dopamine/oxytocin/social need leverage)",
            tacticalTip = "Always sequence Focus -> Authority -> Tribe -> Emotion before making a high-stakes ask.",
            dueOrder = 3
        ),
        SrsFlashcard(
            id = "fc_04",
            trackId = CurriculumTrackId.TRUST_CODE,
            frontPrompt = "What are the 3 pillars of The Trust Code, and how do you fix an Authenticity Wobble?",
            backAnswer = "The 3 Pillars: Authenticity, Empathy, and Logic.\n\nFix an Authenticity Wobble using Controlled Vulnerability—proactively admitting a small human flaw or limitation early.",
            tacticalTip = "People distrust seamless perfection; a small candid admission spikes oxytocin.",
            dueOrder = 4
        ),
        SrsFlashcard(
            id = "fc_05",
            trackId = CurriculumTrackId.NCI_LEVEL_2,
            frontPrompt = "What are 3 Elicitation Techniques from NCI Level 2 & 6MX to get information without asking direct questions?",
            backAnswer = "1) Assumptive / Provocative Statement (triggers the urge to correct you)\n2) Mild Disbelief / Flattery Trap ('No way you built that in two weeks')\n3) Quoting a Third Party ('Most people say X is impossible...')",
            tacticalTip = "Statements bypass the prefrontal 'interrogation shield' that questions activate.",
            dueOrder = 5
        ),
        SrsFlashcard(
            id = "fc_06",
            trackId = CurriculumTrackId.BEHAVIOR_OPS_MANUAL,
            frontPrompt = "In the BTE, what does a Unilateral Shoulder Shrug (Sh = 5 pts) during a definitive statement indicate?",
            backAnswer = "High cognitive incongruence / deception leak: the speaker has zero internal confidence in the statement they are making.",
            tacticalTip = "True shrugs of uncertainty are bilateral and symmetrical; a one-sided micro-twitch leaks doubt.",
            dueOrder = 6
        ),
        SrsFlashcard(
            id = "fc_07",
            trackId = CurriculumTrackId.SIX_MINUTE_XRAY,
            frontPrompt = "What are the 6 Decision Styles in Six-Minute X-Ray?",
            backAnswer = "1) Analytical (data/specs)\n2) Belief (principles/morals)\n3) Leader (pioneer/first-mover)\n4) Social (peer proof)\n5) Necessity (urgent utility)\n6) Impulsive (emotional speed/novelty)",
            tacticalTip = "Never pitch Social Proof ('Everyone has one!') to a Leader decider.",
            dueOrder = 7
        ),
        SrsFlashcard(
            id = "fc_08",
            trackId = CurriculumTrackId.NCI_LEVEL_3_4,
            frontPrompt = "Why do Hand Illustrators suddenly freeze (Fd = 4 pts) during fabrication in NCI Level 3/4?",
            backAnswer = "High cognitive load in the prefrontal cortex starves the motor cortex of bandwidth needed to generate spontaneous rhythmic hand gestures.",
            tacticalTip = "Watch for expressive hands suddenly locking onto chair armrests or the lap right after a probe.",
            dueOrder = 8
        )
    )
}
