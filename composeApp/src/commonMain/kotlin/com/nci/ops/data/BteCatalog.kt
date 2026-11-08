package com.nci.ops.data

import com.nci.ops.model.BteCategory
import com.nci.ops.model.BteElement
import com.nci.ops.model.BteTimedScenario
import com.nci.ops.model.CuePolarity

object BteCatalog {

    val elements: List<BteElement> = listOf(
        // EYES & OCULAR
        BteElement(
            id = "bte_br",
            symbol = "Br",
            atomicNumber = 1,
            name = "Blink Rate Spike",
            category = BteCategory.EYES_OCULAR,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Normal conversational blink rate is ~12–18 blinks/min (1 every 3–5 seconds).",
            deviationMeaning = "Sudden rapid fluttering (3+ blinks in 2 seconds) immediately following a stimulus indicates striatal dopamine shift / cognitive stress.",
            psychologyMechanism = "Heightened limbic arousal alters orbicularis oculi firing frequency when processing a threat or fabricating a response.",
            commonClusters = listOf("Lp", "Vd", "Sw"),
            bookSource = "The Behavior Operations Manual"
        ),
        BteElement(
            id = "bte_ec",
            symbol = "Ec",
            atomicNumber = 2,
            name = "Extended Eye Closure (Blocking)",
            category = BteCategory.EYES_OCULAR,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Standard blink duration is 100–150 milliseconds.",
            deviationMeaning = "Eyelids remain closed for >400ms (or accompanied by eye rubbing) as a subconscious defense to block out the threat/questioner.",
            psychologyMechanism = "Visual cortex suppression when overwhelmed by an unwanted stimulus or distressing truth.",
            commonClusters = listOf("Br", "Fc", "Sh"),
            bookSource = "The Behavior Operations Manual / NCI Level 1"
        ),
        BteElement(
            id = "bte_ef",
            symbol = "Ef",
            atomicNumber = 3,
            name = "Eyebrow Flash + Head Tilt",
            category = BteCategory.EYES_OCULAR,
            pointValue = 0,
            polarity = CuePolarity.COMFORT_RAPPORT,
            baselineNote = "Neutral resting brow and vertical cervical spine.",
            deviationMeaning = "Rapid bilateral brow raise (<0.5s) paired with exposing the carotid artery via head tilt signals non-threat and mammalian warmth.",
            psychologyMechanism = "Universal mammalian greeting and vulnerability signal triggering oxytocin release.",
            commonClusters = listOf("Vf", "Op"),
            bookSource = "The Ellipsis Manual / The Trust Code"
        ),

        // MOUTH & LIPS
        BteElement(
            id = "bte_lp",
            symbol = "Lp",
            atomicNumber = 4,
            name = "Lip Compression / Disappearing Lips",
            category = BteCategory.MOUTH_LIPS,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Relaxed, visible vermilion border of upper and lower lips.",
            deviationMeaning = "Rolling the lips inward until the red part disappears signals high withholding—the brain literally sealing information inside.",
            psychologyMechanism = "Sympathetic nervous system activation closing body orifices against perceived danger or self-incrimination.",
            commonClusters = listOf("Br", "Vd", "Jl"),
            bookSource = "The Behavior Operations Manual"
        ),
        BteElement(
            id = "bte_ll",
            symbol = "Ll",
            atomicNumber = 5,
            name = "Lip Licking / Pacifying Bite",
            category = BteCategory.MOUTH_LIPS,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Normal salivary moisture without repetitive tongue sweeps.",
            deviationMeaning = "Dry-mouth tongue sweep across the lips or biting the lower lip immediately after a question indicates sympathetic mucosal drying and self-soothing.",
            psychologyMechanism = "Cortisol/adrenaline diverts blood flow from salivary glands, causing sudden dry mouth.",
            commonClusters = listOf("Sw", "Br", "Vc"),
            bookSource = "The Behavior Operations Manual / NCI Level 1"
        ),

        // HEAD & FACE
        BteElement(
            id = "bte_fc",
            symbol = "Fc",
            atomicNumber = 6,
            name = "Face / Nose / Neck Pacifier Touch",
            category = BteCategory.HEAD_FACE,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Hands resting on lap, table, or gesturing naturally.",
            deviationMeaning = "Touching the suprasternal notch (neck dimple), rubbing the nose, or stroking the jaw/earlobe to stimulate the vagus nerve and lower heart rate.",
            psychologyMechanism = "Vagal nerve stimulation (carotid sinus massage) slows a racing sympathetic heart rate.",
            commonClusters = listOf("Ec", "Lp", "Vd"),
            bookSource = "The Behavior Operations Manual"
        ),
        BteElement(
            id = "bte_jl",
            symbol = "Jl",
            atomicNumber = 7,
            name = "Masseter Jaw Clench / Shift",
            category = BteCategory.HEAD_FACE,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Relaxed jawline without visible masseter flexing.",
            deviationMeaning = "Lateral jaw shift or masseter muscle pulse signals suppressed aggression, anger, or high internal resistance.",
            psychologyMechanism = "Fight-or-flight bite preparation suppressed by social conditioning.",
            commonClusters = listOf("Lp", "Fd", "St"),
            bookSource = "The Ellipsis Manual / NCI Level 1"
        ),
        BteElement(
            id = "bte_ci",
            symbol = "Ci",
            atomicNumber = 8,
            name = "Contradictory Head Shake / Nod",
            category = BteCategory.HEAD_FACE,
            pointValue = 5,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Head motion congruent with spoken affirmative/negative statements.",
            deviationMeaning = "Subtle micro-shake 'No' while verbally stating 'Yes, absolutely' (or after a 0.5s delay) exposes internal cognitive incongruence.",
            psychologyMechanism = "Motor cortex leaks true belief before prefrontal cortex completes verbal fabrication.",
            commonClusters = listOf("Sh", "Eq", "Lp"),
            bookSource = "The Behavior Operations Manual / NCI Level 3"
        ),

        // TORSO & POSTURE
        BteElement(
            id = "bte_vd",
            symbol = "Vd",
            atomicNumber = 9,
            name = "Ventral Denial / Barrier Creation",
            category = BteCategory.TORSO_POSTURE,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Umbilicus (navel) and sternum angled toward the speaker.",
            deviationMeaning = "Blading the torso away, crossing arms tightly with hand-clasp, or placing an object (cup, bag, laptop) between chest and speaker.",
            psychologyMechanism = "Reptilian survival reflex shielding vital organs (heart, lungs, abdominal cavity) from a perceived threat.",
            commonClusters = listOf("Ft", "Lp", "Lr"),
            bookSource = "Six-Minute X-Ray / Behavior Ops Manual"
        ),
        BteElement(
            id = "bte_vf",
            symbol = "Vf",
            atomicNumber = 10,
            name = "Ventral Fronting & Gravitation",
            category = BteCategory.TORSO_POSTURE,
            pointValue = 0,
            polarity = CuePolarity.COMFORT_RAPPORT,
            baselineNote = "Neutral seated or standing alignment.",
            deviationMeaning = "Forward torso lean (10–15 degrees) with exposed sternum and uncrossed limbs toward the speaker indicates high psychological engagement and trust.",
            psychologyMechanism = "Approach motivation driven by ventral striatum reward signaling.",
            commonClusters = listOf("Ef", "Op"),
            bookSource = "The Trust Code / Six-Minute X-Ray"
        ),
        BteElement(
            id = "bte_sh",
            symbol = "Sh",
            atomicNumber = 11,
            name = "Unilateral Shoulder Shrug",
            category = BteCategory.TORSO_POSTURE,
            pointValue = 5,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Symmetrical shoulder elevation during genuine uncertainty.",
            deviationMeaning = "A partial, one-sided shoulder twitch while making a definitive statement signals zero confidence in the words being spoken.",
            psychologyMechanism = "Incomplete autonomic shrug leaking uncertainty through the spinal accessory nerve.",
            commonClusters = listOf("Ci", "Eq", "Vc"),
            bookSource = "The Behavior Operations Manual / NCI Level 2"
        ),
        BteElement(
            id = "bte_lr",
            symbol = "Lr",
            atomicNumber = 12,
            name = "Distancing Lean / Turtle Effect",
            category = BteCategory.TORSO_POSTURE,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Relaxed neck exposure and upright spine.",
            deviationMeaning = "Shoulders rising toward ears (hiding the neck) or leaning backward right as a sensitive topic is introduced.",
            psychologyMechanism = "Withdrawal reflex protecting cervical vertebrae and carotid arteries.",
            commonClusters = listOf("Vd", "Fc", "Lp"),
            bookSource = "The Behavior Operations Manual"
        ),

        // HANDS & FINGERS
        BteElement(
            id = "bte_gm",
            symbol = "Gm",
            atomicNumber = 13,
            name = "Grooming / Lint-Picking Gesture",
            category = BteCategory.HANDS_FINGERS,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Hands engaged in illustrative conversation gestures.",
            deviationMeaning = "Sudden picking at imaginary lint on pants/sleeves accompanied by downward gaze—classic psychological withdrawal and self-pacification.",
            psychologyMechanism = "Displacement activity dissipating nervous motor energy while avoiding eye contact.",
            commonClusters = listOf("Vd", "Ec", "Lp"),
            bookSource = "The Behavior Operations Manual"
        ),
        BteElement(
            id = "bte_fd",
            symbol = "Fd",
            atomicNumber = 14,
            name = "Sudden Gesture Freeze (Illustrator Drop)",
            category = BteCategory.HANDS_FINGERS,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Consistent hand illustrators accompanying speech rhythm.",
            deviationMeaning = "Hands suddenly stop moving or lock onto chair armrests when answering a specific question due to high cognitive load.",
            psychologyMechanism = "Working memory overload during deception starves motor cortex of bandwidth for spontaneous hand illustrators.",
            commonClusters = listOf("St", "Ci", "Eq"),
            bookSource = "The Behavior Operations Manual / NCI Level 3"
        ),
        BteElement(
            id = "bte_op",
            symbol = "Op",
            atomicNumber = 15,
            name = "Ventral Palm Exposure & Steepling",
            category = BteCategory.HANDS_FINGERS,
            pointValue = 0,
            polarity = CuePolarity.COMFORT_RAPPORT,
            baselineNote = "Neutral hand position.",
            deviationMeaning = "Open upward palms signal candor/submission; fingertip steepling signals high confidence and comfort with the current topic.",
            psychologyMechanism = "Absence of defensive muscular tension in flexor digitorum pathways.",
            commonClusters = listOf("Vf", "Ef"),
            bookSource = "Six-Minute X-Ray / The Ellipsis Manual"
        ),

        // LEGS & FEET
        BteElement(
            id = "bte_ft",
            symbol = "Ft",
            atomicNumber = 16,
            name = "Lead Foot Exit Pivot",
            category = BteCategory.LEGS_FEET,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Both feet angled toward the conversation partner.",
            deviationMeaning = "One or both feet pivot toward the nearest exit or away from the speaker while the upper face maintains a polite smile.",
            psychologyMechanism = "Honest lower-limb orientation: the furthest limb from the brain receives the least conscious censorship and prepares for flight.",
            commonClusters = listOf("Vd", "Lr", "Gm"),
            bookSource = "Six-Minute X-Ray / NCI Level 1"
        ),
        BteElement(
            id = "bte_al",
            symbol = "Al",
            atomicNumber = 17,
            name = "Sudden Ankle Lock / Leg Tuck",
            category = BteCategory.LEGS_FEET,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Feet flat on floor or casually crossed.",
            deviationMeaning = "Feet suddenly whip back under the chair and lock around chair legs or ankles immediately after a question.",
            psychologyMechanism = "Limbic freeze response retracting extremities toward core mass.",
            commonClusters = listOf("Fd", "Lp", "Br"),
            bookSource = "The Behavior Operations Manual"
        ),

        // VOCAL & LINGUISTIC
        BteElement(
            id = "bte_vc",
            symbol = "Vc",
            atomicNumber = 18,
            name = "Vocal Pitch Spike / Clearing Throat",
            category = BteCategory.VOCAL_LINGUISTIC,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Resonant chest-voice baseline pitch and smooth cadence.",
            deviationMeaning = "Involuntary laryngeal constriction raises vocal fundamental frequency (pitch) or triggers a non-illness throat clear before answering.",
            psychologyMechanism = "Vagus/recurrent laryngeal nerve tension tightens vocal cords under sympathetic stress.",
            commonClusters = listOf("Sw", "Ll", "Eq"),
            bookSource = "The Behavior Operations Manual / NCI Level 2"
        ),
        BteElement(
            id = "bte_eq",
            symbol = "Eq",
            atomicNumber = 19,
            name = "Linguistic Equivocation & Pronoun Drop",
            category = BteCategory.VOCAL_LINGUISTIC,
            pointValue = 5,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Direct ownership using 'I' and past-tense verbs ('I didn't take it').",
            deviationMeaning = "Dropping 'I', shifting to passive voice ('The file went missing'), or hedging ('To the best of my recollection, basically...') distances the speaker from the act.",
            psychologyMechanism = "Psychological distancing to reduce internal cognitive dissonance while deceiving.",
            commonClusters = listOf("Sh", "Ci", "Vc"),
            bookSource = "NCI Level 2 / Six-Minute X-Ray"
        ),
        BteElement(
            id = "bte_nq",
            symbol = "Nq",
            atomicNumber = 20,
            name = "Repeating the Question / Stall Tactic",
            category = BteCategory.VOCAL_LINGUISTIC,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Immediate, natural response latency (<1.2 seconds for simple recall).",
            deviationMeaning = "Repeating your exact question back ('Did I speak to Mark yesterday?') or asking 'What do you mean by that?' to buy prefrontal fabrication time.",
            psychologyMechanism = "Cognitive delay tactic required to construct a plausible narrative consistent with known facts.",
            commonClusters = listOf("Ec", "Eq", "Fd"),
            bookSource = "The Behavior Operations Manual / NCI Level 3"
        ),

        // AUTONOMIC & RESPIRATION
        BteElement(
            id = "bte_sw",
            symbol = "Sw",
            atomicNumber = 21,
            name = "Hard Swallow / Adam's Apple Jump",
            category = BteCategory.AUTONOMIC,
            pointValue = 4,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Unnoticeable resting swallowing.",
            deviationMeaning = "Visible, effortful swallow due to cricopharyngeal constriction ('lump in the throat') and sudden dry mucosa.",
            psychologyMechanism = "Autonomic fight-or-flight constriction of pharyngeal musculature.",
            commonClusters = listOf("Vc", "Ll", "Br"),
            bookSource = "The Behavior Operations Manual"
        ),
        BteElement(
            id = "bte_st",
            symbol = "St",
            atomicNumber = 22,
            name = "Shallow Thoracic Breathing / Apnea",
            category = BteCategory.AUTONOMIC,
            pointValue = 3,
            polarity = CuePolarity.DISCOMFORT_STRESS,
            baselineNote = "Deep, rhythmic diaphragmatic breathing.",
            deviationMeaning = "Holding breath during the question followed by a long recovery exhale once the topic changes.",
            psychologyMechanism = "Evolutionary freeze reflex minimizing chest movement and sound while under scrutiny.",
            commonClusters = listOf("Fd", "Al", "Jl"),
            bookSource = "The Behavior Operations Manual / Ellipsis Manual"
        )
    )

    val timedScenarios: List<BteTimedScenario> = listOf(
        BteTimedScenario(
            id = "scen_1",
            title = "Vendor Contract Timeline Probe",
            context = "You are reviewing a project delay with a contractor. During 3 minutes of small talk, their baseline is open palms (Op), steady blink rate (~14/min), and relaxed posture.",
            baselineDescription = "Open palms, torso facing you, steady vocal pitch, normal blink rate.",
            stimulusQuestion = "\"Have all the subcontractors already signed off on the Phase 2 safety audit?\"",
            observedCueSymbols = listOf("Nq", "Sh", "Lp", "Br"),
            totalBteScore = 16, // Nq(4) + Sh(5) + Lp(4) + Br(3) = 16 >= 11
            crossesThreshold = true,
            analysisExplanation = "SCORE: 16 (>= 11 Threshold Crossed). Within 5 seconds of the stimulus, the subject stalled by repeating the question (Nq=4), flashed a unilateral shoulder shrug while saying 'Yeah, that's handled' (Sh=5), compressed their lips (Lp=4), and spiked their blink rate (Br=3). Classic multi-channel deception/withholding cluster."
        ),
        BteTimedScenario(
            id = "scen_2",
            title = "Coffee Shop Cold Room Shift",
            context = "You are speaking with a colleague near an air-conditioning vent. They have had their arms crossed (Vd) for the last 10 minutes since sitting down.",
            baselineDescription = "Arms already crossed due to cold air; steady voice; feet angled toward you.",
            stimulusQuestion = "\"What did you think of the quarterly presentation this morning?\"",
            observedCueSymbols = listOf("Vd", "Fc"),
            totalBteScore = 3, // Vd was baseline (0 deviation), Fc(3) = 3 < 11
            crossesThreshold = false,
            analysisExplanation = "SCORE: 3 (Below 11 Threshold). Because arm-crossing (Vd) was already part of the environmental baseline, it is NOT scored as a post-stimulus deviation. A single isolated nose touch (Fc=3) without vocal or postural cluster cues does not indicate deception."
        ),
        BteTimedScenario(
            id = "scen_3",
            title = "Partnership Confidentiality Check",
            context = "Negotiating an NDA with a prospective partner. Baseline is expressive hand gestures and direct first-person language.",
            baselineDescription = "Animated hand illustrators, direct 'I/We' pronouns, relaxed ankles.",
            stimulusQuestion = "\"Has anyone on your team shared our pricing deck with the competing bidder?\"",
            observedCueSymbols = listOf("Fd", "Al", "Eq", "Sw"),
            totalBteScore = 17, // Fd(4) + Al(4) + Eq(5) + Sw(4) = 17 >= 11
            crossesThreshold = true,
            analysisExplanation = "SCORE: 17 (>= 11 Threshold Crossed). Instantaneous freeze across three channels: hand illustrators stopped dead (Fd=4), ankles locked under the chair (Al=4), a hard autonomic swallow occurred (Sw=4), followed by linguistic equivocation ('Not to my knowledge would something like that be distributed', Eq=5)."
        ),
        BteTimedScenario(
            id = "scen_4",
            title = "Social Needs Validation Test",
            context = "You suspect a new client's primary Social Need is Intelligence. You test the hypothesis by validating their analytical breakdown.",
            baselineDescription = "Slightly guarded posture, analytical tone.",
            stimulusQuestion = "\"Most people miss that second-order bottleneck—how did you spot that pattern so quickly?\"",
            observedCueSymbols = listOf("Ef", "Vf", "Op"),
            totalBteScore = 0, // Comfort cluster
            crossesThreshold = false,
            analysisExplanation = "COMFORT & HOMEOSTASIS CLUSTER (Score: 0 Stress). The subject displayed an eyebrow flash + head tilt (Ef), leaned forward into Ventral Fronting (Vf), and opened their palms/steepled (Op). This confirms Intelligence as a primary Social Need and signals rapid oxytocin rapport."
        )
    )
}
