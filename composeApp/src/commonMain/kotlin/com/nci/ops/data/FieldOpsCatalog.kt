package com.nci.ops.data

import com.nci.ops.model.FieldMission
import com.nci.ops.model.FieldOpTier
import com.nci.ops.model.MicroHabit
import com.nci.ops.model.SkillAxis

object FieldOpsCatalog {

    val missions: List<FieldMission> = listOf(
        // ==========================================
        // TIER 1: ZERO-CONTACT RECON (Outside of Conversation)
        // ==========================================
        FieldMission(
            id = "op_t1_01",
            tier = FieldOpTier.TIER_1_RECON,
            codeName = "OP-SILENT-COMPASS",
            title = "Lead-Foot & Ventral Recon (Zero Contact)",
            sourceBookOrCourse = "Six-Minute X-Ray / NCI Level 1",
            durationEstimate = "10 Mins (Public Space / Cafe)",
            objective = "Observe 5 pairs of people talking from a distance without speaking to anyone. Identify who wants to stay vs. who is subconsciously preparing to leave based solely on umbilicus (navel) and lead-foot orientation.",
            preFlightBriefing = listOf(
                "Find a vantage point in a coffee shop, lobby, or park where you can see full-body postures including feet.",
                "Remember the NCI rule: The face lies first (social smile); the feet tell the truth (reptilian exit preparation).",
                "Look for 'Ventral Fronting' (chest & navel aimed at partner) vs. 'Ventral Denial + Exit Foot' (torso bladed or one foot pointed toward the door/aisle).",
                "Use the No-Look Haptic Stealth Tally below to tap each cue without staring at your phone."
            ),
            stealthTallyLabels = listOf(
                "Ventral Fronting (Engaged)",
                "Ventral Denial / Barrier",
                "Lead Foot Exit Pivot",
                "Congruent Openness"
            ),
            successCriteria = "Log at least 8 total posture/foot observations across 3–5 different dyads.",
            debriefPrompt = "Did you notice anyone smiling and nodding with their upper body while their feet were already pointed toward the exit?",
            xpReward = 100,
            skillAxisAffected = SkillAxis.BASELINE_RECON
        ),
        FieldMission(
            id = "op_t1_02",
            tier = FieldOpTier.TIER_1_RECON,
            codeName = "OP-BLINK-BASELINE",
            title = "Checkout Counter Baseline Shift",
            sourceBookOrCourse = "The Behavior Operations Manual",
            durationEstimate = "8 Mins (Retail / Cafe Line)",
            objective = "While waiting in a line, establish a 20-second baseline on a customer or cashier, then spot the exact BTE deviation when payment/price or an unexpected question occurs.",
            preFlightBriefing = listOf(
                "Observe the subject for 20 seconds BEFORE they reach the register to lock their resting blink rate and hand posture.",
                "Watch the 7-second window right when the total is announced or a card is tapped.",
                "Look for: Lip Compression (Lp), Neck/Face Touch (Fc), Blink Rate Spike (Br), or Weight Shift."
            ),
            stealthTallyLabels = listOf(
                "Baseline Locked",
                "Blink / Eye Shift (Br/Ec)",
                "Lip / Mouth Cue (Lp/Ll)",
                "Hand / Pacifier Touch (Fc)"
            ),
            successCriteria = "Establish 3 baselines and spot at least 2 post-stimulus deviations.",
            debriefPrompt = "Which channel leaked stress fastest at the register—eyes, mouth, or hands?",
            xpReward = 120,
            skillAxisAffected = SkillAxis.BTE_DETECTION
        ),
        FieldMission(
            id = "op_t1_03",
            tier = FieldOpTier.TIER_1_RECON,
            codeName = "OP-WARDROBE-XRAY",
            title = "Passive Social Needs Spotting",
            sourceBookOrCourse = "Six-Minute X-Ray (6MX)",
            durationEstimate = "12 Mins (Any Social/Work Environment)",
            objective = "Without initiating a full conversation, hypothesize the Primary Social Need of 4 individuals based on clothing, posture, vocal volume, and how they occupy space.",
            preFlightBriefing = listOf(
                "SIGNIFICANCE / POWER: High-status signalling, expansive posture, bespoke/flashy watches or badges, takes up maximum space.",
                "ACCEPTANCE / APPROVAL: Brand-conforming tribal attire, mirroring group posture, frequent checking of others' reactions after speaking.",
                "INTELLIGENCE: Functional/precision gear, observational periphery seating, corrective or analytical interjections.",
                "PITY: Slumped/martyr posture, audible sighs, visibly carrying burdens so others ask 'Are you okay?'"
            ),
            stealthTallyLabels = listOf(
                "Significance / Power Cue",
                "Approval / Acceptance Cue",
                "Intelligence Cue",
                "Pity / Martyr Cue"
            ),
            successCriteria = "Log at least 4 distinct Social Need reads from passive observation.",
            debriefPrompt = "Which Social Need was the easiest for you to spot visually, and what specific marker gave it away?",
            xpReward = 130,
            skillAxisAffected = SkillAxis.SIX_MX_PROFILING
        ),

        // ==========================================
        // TIER 2: NONVERBAL CALIBRATION (Minimal/Zero Words)
        // ==========================================
        FieldMission(
            id = "op_t2_01",
            tier = FieldOpTier.TIER_2_CALIBRATION,
            codeName = "OP-MAMMALIAN-FLASH",
            title = "The Mammalian Warmth Trigger",
            sourceBookOrCourse = "The Ellipsis Manual / The Trust Code",
            durationEstimate = "10 Mins (Walking / Hallways / Errands)",
            objective = "Test the evolutionary 'Non-Threat Friend' signal on 5 passersby: a subtle Eyebrow Flash (<0.3s) + 10-degree Head Tilt (exposing carotid) + relaxed half-smile.",
            preFlightBriefing = listOf(
                "When walking past someone at ~8 feet distance, make brief eye contact.",
                "Deliver a micro eyebrow flash paired with a slight head tilt and warm eyes, then casually look ahead (zero neediness).",
                "Observe whether their reptilian defense drops and they involuntarily mirror the brow flash, smile, or nod."
            ),
            stealthTallyLabels = listOf(
                "Mirrored Brow / Smile",
                "Verbal Greeting Triggered",
                "Neutral / No Shift",
                "Defensive Look Away"
            ),
            successCriteria = "Execute 5 clean reps and track the reciprocation rate.",
            debriefPrompt = "How did adding the slight head tilt (vulnerability cue) change people's reactions compared to a flat stare?",
            xpReward = 150,
            skillAxisAffected = SkillAxis.NONVERBAL_AUTHORITY
        ),
        FieldMission(
            id = "op_t2_02",
            tier = FieldOpTier.TIER_2_CALIBRATION,
            codeName = "OP-SLOW-GRAVITY",
            title = "20% Movement Deceleration & Vocal Drop",
            sourceBookOrCourse = "The Ellipsis Manual (Authority Pyramid)",
            durationEstimate = "15 Mins (Any Everyday Interaction)",
            objective = "Adopt high-authority kinematics: slow your hand, head, and torso movements by 20%, eliminate head-bobbing while listening, and end every sentence on a downward vocal inflection.",
            preFlightBriefing = listOf(
                "High-status mammals move smoothly and deliberately; low-status mammals move rapidly and jerkily.",
                "Before turning your head when someone speaks or walks in, pause 0.5 seconds, then turn smoothly.",
                "When ordering coffee or greeting someone, drop the final word of your sentence down in pitch (statement inflection, not upward question inflection)."
            ),
            stealthTallyLabels = listOf(
                "Downward Pitch Rep",
                "Slow Head/Hand Turn",
                "Held Stillness (No Fidget)",
                "Caught Myself Rushing"
            ),
            successCriteria = "Complete at least 6 deliberate authority reps in real-world interactions.",
            debriefPrompt = "How did slowing your movement by 20% affect your own internal feeling of calm and confidence?",
            xpReward = 160,
            skillAxisAffected = SkillAxis.NONVERBAL_AUTHORITY
        ),

        // ==========================================
        // TIER 3: MICRO-RAPPORT & THE TRUST CODE (15–60 Seconds)
        // ==========================================
        FieldMission(
            id = "op_t3_01",
            tier = FieldOpTier.TIER_3_MICRO_RAPPORT,
            codeName = "OP-TIME-CONSTRAINT",
            title = "The Artificial Time Constraint Opener",
            sourceBookOrCourse = "Six-Minute X-Ray / The Ellipsis Manual",
            durationEstimate = "30–60 Seconds (1–2 Micro-Interactions)",
            objective = "Initiate a brief comment or question with a stranger while immediately deploying an Artificial Time Constraint so their brain drops its 'trapped/salesman' defense shield.",
            preFlightBriefing = listOf(
                "The #1 reason strangers put up a defensive wall is uncertainty about how long you will corner them.",
                "Use the formula: 'Hey, I have to run in 20 seconds, but...' or while keeping your feet angled slightly toward your path.",
                "Ask a brief situational question or offer a genuine observation, then warmly exit FIRST while rapport is at its peak."
            ),
            stealthTallyLabels = listOf(
                "Time Constraint Deployed",
                "Defensive Wall Dropped",
                "Subject Extended Chat",
                "Clean High-Note Exit"
            ),
            successCriteria = "Execute 2 micro-interactions using an Artificial Time Constraint and exit on a high note.",
            debriefPrompt = "Did you notice the subject physically relax or lean in once they knew you were leaving in 20 seconds?",
            xpReward = 200,
            skillAxisAffected = SkillAxis.TRUST_RAPPORT
        ),
        FieldMission(
            id = "op_t3_02",
            tier = FieldOpTier.TIER_3_MICRO_RAPPORT,
            codeName = "OP-WOBBLE-ZERO",
            title = "The Trust Code Triad & Vulnerability Loop",
            sourceBookOrCourse = "The Trust Code",
            durationEstimate = "1–2 Minutes (Colleague, Service Worker, or Acquaintance)",
            objective = "Audit your own Trust Triad (Authenticity, Empathy, Logic) in a 1-minute interaction and deploy a micro-admission of flaw ('Controlled Vulnerability') to trigger instant oxytocin.",
            preFlightBriefing = listOf(
                "In The Trust Code, people distrust perfection. Admitting a small, low-stakes human flaw early makes everything else you say 3x more believable.",
                "Example: 'I'm still waking up on my first coffee so my brain is running at 80%...' or 'I completely overthought this earlier...'",
                "Pair this with 100% Ventral Fronting and zero phone checking."
            ),
            stealthTallyLabels = listOf(
                "Controlled Flaw Shared",
                "Empathy Label Used",
                "Subject Smiled / Opened Up",
                "Reciprocal Disclosure"
            ),
            successCriteria = "Deploy 1 Controlled Vulnerability statement and observe the reciprocal warmth shift.",
            debriefPrompt = "What did the other person share or do immediately after you showed a small human imperfection?",
            xpReward = 220,
            skillAxisAffected = SkillAxis.TRUST_RAPPORT
        ),

        // ==========================================
        // TIER 4: 6MX PROFILING & ELICITATION (2–6 Minutes)
        // ==========================================
        FieldMission(
            id = "op_t4_01",
            tier = FieldOpTier.TIER_4_ELICITATION_6MX,
            codeName = "OP-NO-QUESTION-PROBE",
            title = "Elicitation Without Questions",
            sourceBookOrCourse = "Six-Minute X-Ray / NCI Level 2",
            durationEstimate = "3–5 Minutes (Conversation)",
            objective = "Discover 3 pieces of meaningful information about someone's work, weekend, or priorities WITHOUT asking a single direct question—using only Elicitation Statements.",
            preFlightBriefing = listOf(
                "Direct questions trigger the prefrontal 'interview defense'. Statements trigger the human urge to correct, elaborate, or agree.",
                "Technique 1 (Assumptive/Provocative Statement): 'It seems like your industry has gotten way more chaotic this year.'",
                "Technique 2 (Mild Disbelief / Flattery Trap): 'There's no way a team of that size pulled that off in one month.'",
                "Technique 3 (Third-Party Quote): 'A lot of people say the hardest part of that role is the client politics...'"
            ),
            stealthTallyLabels = listOf(
                "Assumptive Statement Used",
                "Disbelief / Flattery Used",
                "Subject Corrected/Elaborated",
                "Accidentally Asked Question"
            ),
            successCriteria = "Successfully elicit 3 detailed responses using statements instead of questions.",
            debriefPrompt = "How much longer and more candid were their answers to statements compared to standard 'What do you do?' questions?",
            xpReward = 280,
            skillAxisAffected = SkillAxis.ELLIPSIS_ELICITATION
        ),
        FieldMission(
            id = "op_t4_02",
            tier = FieldOpTier.TIER_4_ELICITATION_6MX,
            codeName = "OP-SOCIAL-NEED-LOCK",
            title = "6MX Live Social Need Hypothesis & Validation",
            sourceBookOrCourse = "Six-Minute X-Ray (6MX Course) / Behavior Practitioner",
            durationEstimate = "4–6 Minutes (Conversation)",
            objective = "Within 4 minutes of conversation, identify the person's #1 Social Need (Significance, Approval, Acceptance, Intelligence, Pity, or Power), feed it with a tailored validation statement, and watch for the Homeostasis sigh/relaxation.",
            preFlightBriefing = listOf(
                "Listen for how they describe themselves: Do they highlight how smart their solution was (Intelligence)? How much they sacrificed (Pity)? How they led/controlled the outcome (Power/Significance)?",
                "Once identified, deliver a laser-targeted validation targeting THAT exact need (not your own need!).",
                "Watch for the 'Homeostasis Release': shoulders dropping, chest opening, or rapid verbal agreement."
            ),
            stealthTallyLabels = listOf(
                "Social Need Identified",
                "Decision Style Spotted",
                "Targeted Validation Delivered",
                "Homeostasis / Relief Seen"
            ),
            successCriteria = "Diagnose Primary Social Need + Decision Style and confirm via homeostasis response.",
            debriefPrompt = "What was their Primary Social Need, and what exact phrase did you use to validate it?",
            xpReward = 320,
            skillAxisAffected = SkillAxis.SIX_MX_PROFILING
        ),

        // ==========================================
        // TIER 5: ADVANCED ELLIPSIS OPERATIONS (Full Mastery)
        // ==========================================
        FieldMission(
            id = "op_t5_01",
            tier = FieldOpTier.TIER_5_ELLIPSIS_OPS,
            codeName = "OP-LINGUISTIC-HARVEST",
            title = "Linguistic Harvesting & Compliance Ladder",
            sourceBookOrCourse = "The Ellipsis Manual / NCI Level 3 & 4",
            durationEstimate = "5–10 Minutes (Meeting, Negotiation, or Deep Chat)",
            objective = "Harvest 3 exact unusual adjectives/nouns the subject uses, feed those exact words back 2 minutes later while gesturing to a physical space (Spatial Anchoring), and execute a 3-step Compliance Ladder.",
            preFlightBriefing = listOf(
                "LINGUISTIC HARVESTING: People attach deep neural meaning to specific words they choose (e.g., 'bulletproof', 'frictionless', 'nightmare'). Echoing their exact word triggers subconscious 'They get me' resonance.",
                "COMPLIANCE LADDER: Never ask for the big behavioral shift first. Get 1) A micro verbal 'Yes' -> 2) A tiny physical/collaborative action (looking at something, handing an item) -> 3) The target agreement."
            ),
            stealthTallyLabels = listOf(
                "Word Harvested",
                "Harvested Word Echoed",
                "Micro-Compliance Step 1/2",
                "Target Agreement Reached"
            ),
            successCriteria = "Harvest & echo 3 exact words and complete a 3-step Compliance Ladder.",
            debriefPrompt = "Write down the exact 3 words you harvested and how the subject reacted when you echoed them back.",
            xpReward = 400,
            skillAxisAffected = SkillAxis.ELLIPSIS_ELICITATION
        ),
        FieldMission(
            id = "op_t5_02",
            tier = FieldOpTier.TIER_5_ELLIPSIS_OPS,
            codeName = "OP-FATE-ORCHESTRATION",
            title = "Full FATE Model & Live BTE Cluster Audit",
            sourceBookOrCourse = "The Ellipsis Manual / Behavior Operations Manual",
            durationEstimate = "6–15 Minutes (High-Value Interaction)",
            objective = "Engage all 4 levers of the FATE Model (Focus, Authority, Tribe, Emotion) while tracking the subject's BTE cluster score during a key decision point.",
            preFlightBriefing = listOf(
                "FOCUS: Capture attention by breaking an expected pattern or introducing curiosity.",
                "AUTHORITY: Maintain 20% slower movement, downward vocal inflection, and calm stillness.",
                "TRIBE: Frame the goal using 'We/Us' and shared identity boundaries.",
                "EMOTION: Anchor the outcome to their Primary Social Need while watching for any BTE cluster >= 11 on objections."
            ),
            stealthTallyLabels = listOf(
                "Focus / Authority Lever",
                "Tribe ('We') Frame Used",
                "BTE Stress Cue Spotted",
                "Comfort Cluster Confirmed"
            ),
            successCriteria = "Deploy all 4 FATE levers and identify any hidden objection via BTE cues.",
            debriefPrompt = "Where did you see the strongest comfort cluster vs. stress cluster during the interaction?",
            xpReward = 450,
            skillAxisAffected = SkillAxis.BTE_DETECTION
        )
    )

    val defaultMicroHabits: List<MicroHabit> = listOf(
        MicroHabit(
            id = "hab_downward_pitch",
            title = "Downward Vocal Inflection",
            source = "The Ellipsis Manual (Authority Pyramid)",
            whyItMatters = "Ending statements with a slight drop in pitch signals certainty and reptilian authority; upward 'question' inflection leaks approval-seeking.",
            dailyTargetReps = 5
        ),
        MicroHabit(
            id = "hab_ventral_lock",
            title = "Conscious Ventral Fronting + Open Palms",
            source = "Six-Minute X-Ray / The Trust Code",
            whyItMatters = "Aiming your sternum/navel squarely at your partner with uncrossed limbs triggers mammalian safety and trust.",
            dailyTargetReps = 5
        ),
        MicroHabit(
            id = "hab_elicitation_swap",
            title = "Swap a Direct Question for an Elicitation Statement",
            source = "Six-Minute X-Ray / NCI Level 2",
            whyItMatters = "Catch yourself before asking 'What/Why/How?' and turn it into an assumptive or curious statement ('It looks like...').",
            dailyTargetReps = 3
        ),
        MicroHabit(
            id = "hab_word_harvest",
            title = "Harvest & Echo 1 Exact Predicate Word",
            source = "The Ellipsis Manual",
            whyItMatters = "Trains active listening and deep subconscious rapport by logging and reusing the other person's exact emotional vocabulary.",
            dailyTargetReps = 3
        ),
        MicroHabit(
            id = "hab_baseline_first",
            title = "15-Second Baseline Read Before Speaking",
            source = "The Behavior Operations Manual",
            whyItMatters = "Conditions you to observe resting blink rate, hand position, and foot angle before introducing any stimulus.",
            dailyTargetReps = 4
        )
    )
}
