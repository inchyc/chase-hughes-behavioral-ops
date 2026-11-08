package com.nci.ops.data

import com.nci.ops.model.PocketCueCard

object PocketCueCardsCatalog {

    val cards: List<PocketCueCard> = listOf(
        // ==========================================
        // 6MX: THE 6 SOCIAL NEEDS (30-SECOND POCKET CARDS)
        // ==========================================
        PocketCueCard(
            id = "cue_sn_significance",
            category = "6MX Social Needs",
            title = "Significance (SIG)",
            badgeText = "NEED #1: SIGNIFICANCE",
            thirtySecondSummary = "Driven to be seen as unique, elite, respected, and irreplaceable. They don't want to 'fit in' with the crowd—they want to stand above it.",
            visualAndVerbalTells = listOf(
                "Bespoke, limited-edition, or high-status statement items (watches, shoes, badges).",
                "Frequently steers conversation back to their personal track record, titles, or exclusive access.",
                "Expansive posture; dislikes being compared to 'most people'."
            ),
            exactPhrasesToUse = listOf(
                "\"Honestly, 99% of people in your field don't operate at that standard.\"",
                "\"Given your track record with [X], I wanted your specific perspective on this.\"",
                "\"This isn't the standard approach we offer everyone—it requires someone who actually understands the high-level picture.\""
            ),
            fatalErrorsToAvoid = listOf(
                "NEVER say 'Oh yeah, a lot of our clients do that too!' (destroys their uniqueness).",
                "NEVER interrupt them while they are recounting an achievement or correct a minor detail publicly."
            ),
            bodyLanguageCalibration = "Give them full Ventral Fronting, lean in slightly when they describe an accomplishment, and take deliberate notes while they speak."
        ),
        PocketCueCard(
            id = "cue_sn_approval",
            category = "6MX Social Needs",
            title = "Approval (APP)",
            badgeText = "NEED #2: APPROVAL",
            thirtySecondSummary = "Driven by being liked, praised, and overtly validated. They go out of their way to please others and scan faces constantly to make sure you are happy with them.",
            visualAndVerbalTells = listOf(
                "Frequent nodding, smiling, and apologizing even when not at fault ('Sorry to bother you!').",
                "Fishes for reassurance or checks your facial reaction right after making a point.",
                "Avoids conflict at all costs; uses softening qualifiers ('I might be wrong, but...')."
            ),
            exactPhrasesToUse = listOf(
                "\"I really appreciate how thoughtful you were with how you handled that.\"",
                "\"You made that whole process so much easier for everyone involved.\"",
                "\"I was just telling [Name] how impressed I was with the effort you put into this.\""
            ),
            fatalErrorsToAvoid = listOf(
                "NEVER use cold, deadpan silence after they share an idea (they interpret neutral silence as rejection).",
                "NEVER deliver blunt criticism without first anchoring high warmth and personal appreciation."
            ),
            bodyLanguageCalibration = "Use frequent warm eyebrow flashes, encouraging head nods, and vocal 'mm-hmm' affirmations so their nervous system feels safe."
        ),
        PocketCueCard(
            id = "cue_sn_acceptance",
            category = "6MX Social Needs",
            title = "Acceptance (ACC)",
            badgeText = "NEED #3: ACCEPTANCE",
            thirtySecondSummary = "Driven by safe belonging inside the tribe. Unlike Significance (who wants to stand out), Acceptance wants to feel included without being put under a harsh spotlight.",
            visualAndVerbalTells = listOf(
                "Wears group-conforming attire (team gear, company merch, subculture norms).",
                "Uses 'We' and 'Us' frequently; looks to the group before stating a controversial opinion.",
                "Uncomfortable with excessive 1-on-1 public praise that singles them out from peers."
            ),
            exactPhrasesToUse = listOf(
                "\"Look, we're in this together—that's just how people like us handle things.\"",
                "\"Everyone on the core team immediately felt like you were a natural fit here.\"",
                "\"A lot of us went through that exact same transition when we first started.\""
            ),
            fatalErrorsToAvoid = listOf(
                "NEVER put them on the spot in front of a group without warning.",
                "NEVER frame a choice as 'You'll be the only one doing it this way' (triggers tribal isolation fear)."
            ),
            bodyLanguageCalibration = "Angle your posture side-by-side or at a relaxed 45-degree tribal angle rather than an intimidating head-on interrogation stance."
        ),
        PocketCueCard(
            id = "cue_sn_intelligence",
            category = "6MX Social Needs",
            title = "Intelligence (INT)",
            badgeText = "NEED #4: INTELLIGENCE",
            thirtySecondSummary = "Driven to be recognized as the sharpest, most logical, and most perceptive mind in the room. They value competence, precision, and spotting flaws others missed.",
            visualAndVerbalTells = listOf(
                "Uses precise vocabulary, corrects subtle inaccuracies ('Well, technically...'), and asks probing questions.",
                "Chooses functional, well-researched gear over flashy luxury brands.",
                "Often holds a finger-to-chin evaluation gesture or steeples while analyzing."
            ),
            exactPhrasesToUse = listOf(
                "\"How did you catch that variable so fast? Most people completely overlook that.\"",
                "\"Poke holes in this for me—where do you see the logical bottleneck here?\"",
                "\"I skipped the basic overview because I knew you'd already see the underlying mechanics.\""
            ),
            fatalErrorsToAvoid = listOf(
                "NEVER use cheesy emotional hype or exaggerated claims without data to back them up.",
                "NEVER talk down to them or over-explain obvious steps."
            ),
            bodyLanguageCalibration = "Reduce overly bubbly facial expressions; adopt calm, measured vocal cadence, steeple lightly, and give them space to think before speaking."
        ),
        PocketCueCard(
            id = "cue_sn_pity",
            category = "6MX Social Needs",
            title = "Pity (PIT)",
            badgeText = "NEED #5: PITY",
            thirtySecondSummary = "Driven to have their hardships, exhaustion, and unfair burdens witnessed and validated. They gain social currency and safety through sympathy and struggle.",
            visualAndVerbalTells = listOf(
                "Answers 'How are you?' with a heavy sigh and a list of stressors, lack of sleep, or how everyone else dropped the ball.",
                "Carries visible physical tension or slumped 'weight of the world' shoulders.",
                "Feels invalidated if you try to 'cheer them up' or say 'It's not that bad!'"
            ),
            exactPhrasesToUse = listOf(
                "\"I honestly don't know how you've been carrying all of that on your own without burning out.\"",
                "\"Most people have zero idea how much unseen work you've had to absorb this week.\"",
                "\"That's genuinely brutal—you've been dealing with a lot all at once.\""
            ),
            fatalErrorsToAvoid = listOf(
                "NEVER toxic-positively minimize their struggle ('Look on the bright side!' or 'Oh, everyone is busy right now').",
                "NEVER immediately jump into 'Here's the easy fix!' before validating how hard their situation is."
            ),
            bodyLanguageCalibration = "Use a sympathetic head tilt, lower and soften your vocal tone, and pause for 2 seconds after they share a struggle to let the weight register."
        ),
        PocketCueCard(
            id = "cue_sn_power",
            category = "6MX Social Needs",
            title = "Power (POW)",
            badgeText = "NEED #6: POWER",
            thirtySecondSummary = "Driven by agency, control, dominance, and having the final word. They resist any feeling of being managed, cornered, or told what to do.",
            visualAndVerbalTells = listOf(
                "Direct, unblinking gaze; speaks in declarative imperatives; interrupts to steer the agenda.",
                "Controls physical territory (sits at head of table, spreads items out, checks watch).",
                "Pushes back automatically if given only one directive option."
            ),
            exactPhrasesToUse = listOf(
                "\"You're in the driver's seat on this—how do you want to deploy the next step?\"",
                "\"Here are the two high-leverage options and the tradeoffs; it's 100% your call which route we pull the trigger on.\"",
                "\"Bottom line: what outcome do you want to see by Friday?\""
            ),
            fatalErrorsToAvoid = listOf(
                "NEVER use controlling language ('You need to...', 'Our policy requires you to...').",
                "NEVER ramble with long-winded backstory—get straight to the bottom line and hand them the choice."
            ),
            bodyLanguageCalibration = "Stay calm, grounded, and unshaken (never flinch or fidget), keep vocal inflections downward, and always offer 2–3 choices so they exercise control."
        ),

        // ==========================================
        // 6MX: THE 6 DECISION STYLES
        // ==========================================
        PocketCueCard(
            id = "cue_ds_matrix",
            category = "6MX Decision Styles",
            title = "The 6 Decision Styles Matrix",
            badgeText = "6MX DECISION ENGINE",
            thirtySecondSummary = "In Six-Minute X-Ray, how someone satisfies their Social Need is distinct from HOW they make a decision. Match your pitch to their Decision Style:",
            visualAndVerbalTells = listOf(
                "ANALYTICAL: Asks for specs, ROI, failure rates, and documentation.",
                "BELIEF: Uses moral/principle words ('should', 'right way', 'integrity').",
                "LEADER: Wants to be the pioneer ('Who else has this? Nobody yet? Good.').",
                "SOCIAL: Asks for case studies, reviews, and what respected peers chose.",
                "NECESSITY: Only acts when a deadline or urgent pain forces action.",
                "IMPULSIVE: Decides on emotional spark, speed, and immediate experience."
            ),
            exactPhrasesToUse = listOf(
                "Analytical: \"Here is the exact breakdown of the numbers and the downside protection.\"",
                "Belief: \"This aligns directly with the core principle you stood for from day one.\"",
                "Leader: \"This puts you 12 months ahead of everyone else in the market.\"",
                "Social: \"Here is how the top three teams in your sector implemented this.\""
            ),
            fatalErrorsToAvoid = listOf(
                "Pitching 'Everyone is doing this!' (Social Proof) to a Leader/Significance type (instantly repels them).",
                "Drowning an Impulsive/Power buyer in a 40-page Analytical spreadsheet."
            ),
            bodyLanguageCalibration = "Mirror their cognitive tempo: slow and structured for Analytical/Belief; brisk and decisive for Leader/Impulsive."
        ),

        // ==========================================
        // THE ELLIPSIS MANUAL: FATE & AUTHORITY PYRAMID
        // ==========================================
        PocketCueCard(
            id = "cue_fate_model",
            category = "Ellipsis FATE",
            title = "The FATE Model (Reptilian Lever)",
            badgeText = "ELLIPSIS MANUAL",
            thirtySecondSummary = "Every ancestral survival response and compliance shift in the human brain is governed by four primal levers: Focus, Authority, Tribe, and Emotion (FATE).",
            visualAndVerbalTells = listOf(
                "FOCUS: What the brain attends to becomes amplified reality. Novelty or pattern interruption hijacks focus.",
                "AUTHORITY: The brain automatically defers to calm certainty, vocal gravity, and status symbols.",
                "TRIBE: The mammal brain craves inclusion and mirrors the behavior of its perceived in-group.",
                "EMOTION: Dopamine, oxytocin, or fear bypasses logical friction and locks in behavioral action."
            ),
            exactPhrasesToUse = listOf(
                "Focus Hook: \"Wait—hold on one second. Look at that pattern right there.\"",
                "Authority Frame: \"Here is how we are going to structure the next ten minutes.\"",
                "Tribe Frame: \"For people wired like us, the standard playbook doesn't work.\"",
                "Emotion Anchor: \"Imagine the exact relief when this bottleneck is permanently off your plate.\""
            ),
            fatalErrorsToAvoid = listOf(
                "Trying to persuade with pure dry logic while Focus is scattered and Tribe is adversarial.",
                "Breaking your own Authority lever with nervous laughter or upward vocal inflections."
            ),
            bodyLanguageCalibration = "Sequence your interaction: 1) Capture Focus -> 2) Project calm Authority -> 3) Establish 'We' Tribe -> 4) Trigger Social Need Emotion."
        ),
        PocketCueCard(
            id = "cue_trust_wobbles",
            category = "Trust Code",
            title = "The Trust Code: Eliminating Wobbles",
            badgeText = "THE TRUST CODE",
            thirtySecondSummary = "Trust breaks down the moment a person detects a 'Wobble' in one of three pillars: Authenticity (Are you real?), Empathy (Do you care about my outcome?), or Logic (Can you actually deliver?).",
            visualAndVerbalTells = listOf(
                "AUTHENTICITY WOBBLE: Over-polished corporate jargon, fake smile, hiding obvious flaws.",
                "EMPATHY WOBBLE: Interrupting, checking your phone/watch, rushing past their concern to pitch your agenda.",
                "LOGIC WOBBLE: Vague promises, hedging ('hopefully', 'we'll try'), or disorganized thinking."
            ),
            exactPhrasesToUse = listOf(
                "Fix Authenticity Wobble: \"Let me be 100% transparent—here is the one part we're still solving...\"",
                "Fix Empathy Wobble: \"Before we look at numbers, it sounds like the real stress for you has been [X].\"",
                "Fix Logic Wobble: \"There are three specific steps we take here, and here is why Step 1 works.\""
            ),
            fatalErrorsToAvoid = listOf(
                "Doubling down on Logic/Credentials when the other person is experiencing an Empathy Wobble.",
                "Faking certainty on something you don't know instead of saying 'I'll verify that exact number for you at 2 PM'."
            ),
            bodyLanguageCalibration = "Align all 3 channels: Congruent facial expression (Authenticity) + Ventral Fronting & head tilt (Empathy) + Measured, structured hand gestures (Logic)."
        )
    )
}
