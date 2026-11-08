package com.nci.ops.data

import com.nci.ops.model.FieldMission
import com.nci.ops.model.FieldOpTier
import com.nci.ops.model.MicroHabit
import com.nci.ops.model.SkillAxis
import com.nci.ops.model.TargetBehaviorGuide

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
            skillAxisAffected = SkillAxis.BASELINE_RECON,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Ventral Fronting (Engaged)",
                    quickHint = "Navel, sternum, and toes pointed squarely toward the partner",
                    physicalTells = listOf(
                        "Navel and sternum aligned perpendicular to partner (no blading)",
                        "Feet planted with toes aimed in partner's direction",
                        "Arms resting openly on table or lap without forming an upper-body barricade",
                        "Unconscious forward lean of 5–10 degrees closing interpersonal distance"
                    ),
                    realWorldExample = "Two colleagues seated at a cafe: both torsos are squared up directly facing each other across the small table, coffee mugs kept to the periphery, uncrossed legs.",
                    psychologicalWhy = "Evolutionary safety disclosure: Exposing anterior vital organs (throat, heart, visceral abdomen) signals zero perceived physical or social threat and high rapport.",
                    falsePositivesToAvoid = "Constrained seating (e.g. narrow booths, bar counters with fixed stools) where rotating the torso is physically restricted.",
                    relatedBteSymbol = "Vf"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Ventral Denial / Barrier",
                    quickHint = "Navel bladed >30° away or arms/belongings clutched as a shield",
                    physicalTells = listOf(
                        "Torso rotated 30° to 60° away while head turns back to sustain eye contact",
                        "Arms tightly crossed over sternum with hands tucked into armpits",
                        "Physical shield objects: clutching a bag, laptop, coffee cup, or clipboard horizontally across the chest",
                        "Leg cross where the upper knee points away from the speaker, creating a physical wall"
                    ),
                    realWorldExample = "A patron standing in a conversation nods politely with their face, but has pivoted their torso 45° toward the door and clutched their tote bag with both hands across their midsection.",
                    psychologicalWhy = "Limbic freeze/flight reflex: The autonomic nervous system shields delicate anterior organs when experiencing discomfort, social entrapment, or unspoken disagreement.",
                    falsePositivesToAvoid = "Cold ambient temperature (shivering / hugging self for warmth) or chairs without armrests where folding arms is purely ergonomic. Always look for cluster confirmation.",
                    relatedBteSymbol = "Vd"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Lead Foot Exit Pivot",
                    quickHint = "One foot angled 45°–90° away toward an exit, hallway, or aisle",
                    physicalTells = listOf(
                        "Dominant/lead foot points toward the doorway, corridor, or outside path",
                        "Weight transferred onto the rear leg, preparing for propulsion",
                        "Upper body continues polite social nod while lower chassis prepares for flight",
                        "Foot tapping or heel bouncing in the exit direction"
                    ),
                    realWorldExample = "At a networking lobby, someone's head is tilted listening to an acquaintance, but their right shoe is cocked at 90 degrees pointing down the stairs. Within 45 seconds, they invent an excuse to step away.",
                    psychologicalWhy = "Reptilian motor priming: The brain decides to leave long before the conscious prefrontal cortex initiates a polite social exit. The feet leak authentic intent first.",
                    falsePositivesToAvoid = "Shifting foot placement due to leg numbness or uneven flooring. Verify that the toe actually points toward an exit or target destination.",
                    relatedBteSymbol = "Ep"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Congruent Openness",
                    quickHint = "Full upper-to-lower body alignment with relaxed palms and facial muscles",
                    physicalTells = listOf(
                        "Head, chest, and toes aligned in the same directional vector",
                        "Open or visible palms with relaxed fingers (no clenched fists or finger tapping)",
                        "Normal resting blink rate (~12–18 blinks per minute) without eyelid fluttering",
                        "Vocal pitch smooth and conversational without sudden pitch spikes"
                    ),
                    realWorldExample = "An engaged listener whose entire body is centered toward the speaker, reacting spontaneously with synchronous smiles without any self-soothing gestures.",
                    psychologicalWhy = "Limbic homeostasis: Complete absence of fight-or-flight arousal; both autonomic branches are in balanced equilibrium without defensive filtering.",
                    falsePositivesToAvoid = "Overly slumped or collapsed posture indicating detachment or boredom rather than active, receptive openness.",
                    relatedBteSymbol = "Op"
                )
            )
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
            skillAxisAffected = SkillAxis.BTE_DETECTION,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Baseline Locked",
                    quickHint = "20s observation establishing resting blink rate, facial tone, and posture",
                    physicalTells = listOf(
                        "Count resting blinks over 15–20s (normal baseline is 1 blink every 3–5 seconds)",
                        "Observe resting mouth posture: lips relaxed with visible vermilion border",
                        "Note resting hand position: hanging loose, in pockets, or holding wallet without white knuckles",
                        "Confirm zero stimulus has been introduced yet"
                    ),
                    realWorldExample = "Customer standing in line browsing their phone or waiting quietly: blinking steadily at ~14 blinks/min, jaw unclenched, shoulders level.",
                    psychologicalWhy = "NCI Golden Rule: 'Deviation from baseline equals stress or deception.' Without an established baseline, any single gesture is uninterpretable.",
                    falsePositivesToAvoid = "Mistaking someone who is already frantic or on a stressful phone call for a calm baseline.",
                    relatedBteSymbol = "Base"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Blink / Eye Shift (Br/Ec)",
                    quickHint = "Sudden fluttering (3+ blinks in 2s) or prolonged eye closure (>400ms)",
                    physicalTells = listOf(
                        "Blink Rate Spike (Br): Flurry of rapid fluttering immediately when price is stated",
                        "Extended Eye Closure (Ec): Eyelids remain clamped shut for >0.4s to block visual input",
                        "Gaze Aversion: Rapid darting of the pupils downward or away upon hearing unexpected news",
                        "Squinting / Orbital Tightening: Orbicularis oculi muscles contract sharply"
                    ),
                    realWorldExample = "The cashier announces '$184.50'—the customer instantly executes 4 rapid blinks in one second, eyes widening momentarily before squinting down at the card terminal.",
                    psychologicalWhy = "Striatal dopamine surge and visual cortex defense: Limbic surprise or financial stress alters blink frequency and triggers subconscious visual blocking.",
                    falsePositivesToAvoid = "Dry eyes from air conditioning, contact lens irritation, or bright overhead fluorescent glare. Look for timing within 7s of the stimulus.",
                    relatedBteSymbol = "Br"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Lip / Mouth Cue (Lp/Ll)",
                    quickHint = "Disappearing lips pressed flat or sudden lip-licking/pacifying bite",
                    physicalTells = listOf(
                        "Lip Compression (Lp): Red vermilion border of lips disappears into a tight horizontal slit",
                        "Lip Licking (Ll): Quick tongue sweep across lips to moisten acute sympathetic dry mouth",
                        "Lip Sucking / Pacifying Bite: Lower lip drawn in and bitten lightly between incisors",
                        "Jaw Clench: Masseter muscle bulges at the corner of the jaw"
                    ),
                    realWorldExample = "Card reader beeps 'DECLINED' or cashier asks 'Would you like to donate $10?': subject instantly rolls their lips inward until invisible while staring at the terminal.",
                    psychologicalWhy = "Limbic withholding and mucosal dry mouth: Sympathetic tone constricts oral blood flow (causing dryness) and seals the mouth against unwanted expression or disclosure.",
                    falsePositivesToAvoid = "Someone who has chapped lips applying balm or chewing gum habitually.",
                    relatedBteSymbol = "Lp"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Hand / Pacifier Touch (Fc)",
                    quickHint = "Touching neck notch, rubbing nose/ear, or squeezing back of the neck",
                    physicalTells = listOf(
                        "Suprasternal Notch Touch: Index/thumb touches the dimple at base of neck",
                        "Neck Strangle / Squeeze: Hand wraps around back of neck and massages trapezius",
                        "Nose Rub / Scratch: Rapid index finger swipe beneath the septum or along nasal bridge",
                        "Collar Pull / Ventilation: Tugging clothing away from neck to release perceived heat"
                    ),
                    realWorldExample = "A customer informed that an item is out of stock immediately brings their left hand up to massage their throat and tugs lightly at their shirt collar.",
                    psychologicalWhy = "Vagal nerve stimulation: Touching the carotid sinus or cervical nerve endings releases acetylcholine, physically braking a spike in heart rate caused by stress.",
                    falsePositivesToAvoid = "An actual itch. An authentic itch involves prolonged fingernail scratching; a pacifier is a brief, rhythmic stroking or squeezing gesture.",
                    relatedBteSymbol = "Fc"
                )
            )
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
            skillAxisAffected = SkillAxis.SIX_MX_PROFILING,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Significance / Power Cue",
                    quickHint = "Expansive territorial posture, luxury/status badges, loud vocal projection",
                    physicalTells = listOf(
                        "Sprawling across multiple seats, elbows resting broadly on chair backs",
                        "Prominent display of status badges (watches, high-end branded hardware, exclusive keys)",
                        "Commands room volume; speaks with booming cadence without checking for permission",
                        "Expects others to step out of their walking path"
                    ),
                    realWorldExample = "A businessman enters a hotel lobby, tosses keys onto the counter with authority, drapes coat over two armchairs, and speaks loudly enough for the whole lounge to hear.",
                    psychologicalWhy = "Dominance hierarchy positioning: The subconscious imperative to be perceived as superior, indispensable, and powerful; deeply wounded by being overlooked or treated as ordinary.",
                    falsePositivesToAvoid = "A naturally tall or broad person who simply takes up space physically without status-demanding body language.",
                    relatedBteSymbol = "6MX-Sig"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Approval / Acceptance Cue",
                    quickHint = "Tribal brand conformity, nervous smile, scans faces immediately after speaking",
                    physicalTells = listOf(
                        "Clothing strictly adheres to the peer group's aesthetic (in-group tribal uniform)",
                        "Micro-nodding and excessive agreement smiles during conversations",
                        "Eye flick immediately to peer group after finishing a sentence to check reaction",
                        "Submissive head tilt and compressed physical footprint"
                    ),
                    realWorldExample = "A young professional in an office group wearing the exact same fleece jacket as colleagues, constantly laughing at minor jokes and looking around to see if everyone else is laughing.",
                    psychologicalWhy = "Tribal belonging and fear of ostracism: The evolutionary terror of exile from the band drives continuous compliance, warmth signals, and fear of rejection.",
                    falsePositivesToAvoid = "Basic politeness or professional etiquette. Look for the distinct *anxious scanning* for social validation.",
                    relatedBteSymbol = "6MX-App"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Intelligence Cue",
                    quickHint = "Functional/tactical gear, periphery seating, analytical stare, corrective tone",
                    physicalTells = listOf(
                        "Prefers corner or periphery seating where they can observe the entire room",
                        "High functional/utilitarian attire (minimalist, rugged, or analytical tech gear)",
                        "Listens with furrowed brow, chin resting on thumb and curled forefinger",
                        "Interjects to correct factual inaccuracies or refine technical definitions"
                    ),
                    realWorldExample = "In a team briefing, one individual sits with back to the wall, takes meticulous notes in a grid notebook, and gently points out a statistical discrepancy in slide 3.",
                    psychologicalWhy = "Cognitive competence shield: Identity is constructed around being the smartest person in the room; deeply terrified of being perceived as uninformed, irrational, or incompetent.",
                    falsePositivesToAvoid = "Shyness or social awkwardness. Someone with the Intelligence need is confident in their analysis even if socially reserved.",
                    relatedBteSymbol = "6MX-Int"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Pity / Martyr Cue",
                    quickHint = "Slumped posture, audible sighs, ostentatious display of burden or suffering",
                    physicalTells = listOf(
                        "Carrying excessive bags, files, or tasks while visibly struggling or groaning softly",
                        "Audible post-exertion sighs ('Phew, another 60-hour week...')",
                        "Downward downturn of mouth corners (depressor anguli oris activation)",
                        "Slumped spinal kyphosis creating an aura of being physically weighted down"
                    ),
                    realWorldExample = "A coworker walks into the break room carrying three heavy binders, places them down with a loud groan, clutches their lower back, and waits for someone to ask 'Are you okay?'",
                    psychologicalWhy = "Victim leverage: Weaponizing perceived suffering to extract sympathy, excuse underperformance, or monopolize emotional attention without risking direct competition.",
                    falsePositivesToAvoid = "Actual physical illness, acute grief, or elderly mobility impairment. Look for whether the suffering is ostentatiously performed for an audience.",
                    relatedBteSymbol = "6MX-Pit"
                )
            )
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
            skillAxisAffected = SkillAxis.NONVERBAL_AUTHORITY,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Mirrored Brow / Smile",
                    quickHint = "Passerby involuntarily flashes eyebrows back or breaks into warm smile",
                    physicalTells = listOf(
                        "Bilateral raise of eyebrows for 150–300 milliseconds",
                        "Zygomatic major muscle pulls lip corners upward (Duchenne crow's feet at eyes)",
                        "Micro-nod acknowledging mammalian kinship",
                        "Immediate relaxation of facial tension"
                    ),
                    realWorldExample = "You walk past a stranger in an office corridor. At 7 feet, you deliver a micro-flash + head tilt; their neutral face instantly lights up with an involuntary brow bounce and reciprocal grin.",
                    psychologicalWhy = "Mirror neuron circuitry and mammalian recognition: The brow flash is a universal primate greeting code indicating recognition and absence of hostile intent.",
                    falsePositivesToAvoid = "A forced polite 'customer service' grin without eye muscle involvement.",
                    relatedBteSymbol = "Ef"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Verbal Greeting Triggered",
                    quickHint = "Visual trigger prompts the subject to spontaneously speak first ('Good morning', 'Hey')",
                    physicalTells = listOf(
                        "Subject opens mouth and initiates an unsolicited greeting",
                        "Tone is warm, friendly, or surprised-cheerful",
                        "May pause walking stride slightly or pivot head to maintain contact"
                    ),
                    realWorldExample = "A passerby who was staring at the ground looks up, meets your calibrated warmth signal, and cheerfully volunteers: 'Morning! How's it going?'",
                    psychologicalWhy = "Social contract reciprocation: High-warmth, low-threat stimuli compel an automatic reciprocal verbal acknowledgement to close the social loop.",
                    falsePositivesToAvoid = "Greeting someone whose job requires them to greet you (e.g. security guard, retail greeter).",
                    relatedBteSymbol = "Tr"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Neutral / No Shift",
                    quickHint = "Subject maintains flat facial expression or steady forward gaze",
                    physicalTells = listOf(
                        "Zero change in eyebrow position or mouth posture",
                        "Sustained flat gaze without avoidance or warmth",
                        "Normal walking cadence remains unchanged"
                    ),
                    realWorldExample = "Passerby looks toward you, registers your face, but displays zero micro-expression and continues walking without breaking stride.",
                    psychologicalWhy = "Cognitive pre-occupation: Subject is deeply immersed in internal thoughts, auditory processing (earbuds), or task-focused tunnel vision.",
                    falsePositivesToAvoid = "Assuming they dislike you; this is almost always internal cognitive absorption.",
                    relatedBteSymbol = "Neu"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Defensive Look Away",
                    quickHint = "Sharp downward eye flick, throat clearing, or accelerated walking pace",
                    physicalTells = listOf(
                        "Sudden downward dart of the pupils to the floor",
                        "Tightening of jaw masseter or lip compression (Lp)",
                        "Shoulders hike or posture blades away",
                        "Accelerating stride to get past quickly"
                    ),
                    realWorldExample = "A passerby makes eye contact, immediately snaps their gaze to the floor, pulls their coat tighter, and hurries past.",
                    psychologicalWhy = "Social anxiety or boundary defense: In dense urban environments, unexpected eye contact can be perceived as predatory invasion by an overactive amygdala.",
                    falsePositivesToAvoid = "Checking a smartphone notification that coincidentally buzzed at that moment.",
                    relatedBteSymbol = "Vd"
                )
            )
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
            skillAxisAffected = SkillAxis.NONVERBAL_AUTHORITY,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Downward Pitch Rep",
                    quickHint = "Dropping final syllable down in pitch (declarative certainty, not question)",
                    physicalTells = listOf(
                        "Final word of sentence drops 2–4 semitones in pitch",
                        "No trailing upward 'uptalk' that seeks confirmation",
                        "Full vocal cord closure; chest resonance rather than throat or head voice",
                        "Crisp, deliberate stop at the end of the phrase with comfortable silence"
                    ),
                    realWorldExample = "Ordering coffee: 'I'll have a flat white, whole milk.' with 'milk' dropping firmly down, rather than rising as if asking permission.",
                    psychologicalWhy = "Acoustic hierarchy signalling: Dominant leaders end statements with pitch decay (command/certainty); submissive animals use pitch elevation (appeasement/permission).",
                    falsePositivesToAvoid = "Sounding angry, monotone, or robotic. The tone must remain warm, grounded, and relaxed.",
                    relatedBteSymbol = "Dp"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Slow Head/Hand Turn",
                    quickHint = "0.5s pause before rotating head smoothly as a single unit with torso",
                    physicalTells = listOf(
                        "When someone enters or calls your name: hold still for 0.5s before moving",
                        "Head and neck turn at 80% normal velocity (smooth panoramic camera pan)",
                        "Head and shoulders move in unison (no jerky neck snapping)",
                        "Hand gestures are purposeful, unfolding smoothly from the elbows"
                    ),
                    realWorldExample = "A colleague calls your name from the doorway. Instead of whipping your head around instantly, you finish your breath, then turn smoothly with relaxed eye contact.",
                    psychologicalWhy = "Kinematic predator vs. prey: Prey animals startle and jerk rapidly to monitor threats. Apex mammals move deliberately because they fear nothing in the environment.",
                    falsePositivesToAvoid = "Exaggerated slow-motion that looks theatrical or combative.",
                    relatedBteSymbol = "Sg"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Held Stillness (No Fidget)",
                    quickHint = "Zero head bobbing while listening, zero pen clicking, calm diaphragmatic breathing",
                    physicalTells = listOf(
                        "Eliminate repetitive listening nods ('bobble-head effect')",
                        "Hands remain perfectly still on lap or desk without touching pens, rings, or face",
                        "Weight evenly distributed across both feet without hip cocking",
                        "Slow abdominal breaths (~4–6 breaths per minute)"
                    ),
                    realWorldExample = "Listening to someone explain a problem for 30 seconds straight without a single nod or fidget, holding grounded eye contact with a calm, attentive expression.",
                    psychologicalWhy = "Gravitational authority: Stillness conveys immense self-control and neural bandwidth. Submissive individuals fidget to discharge excess nervous cortisol.",
                    falsePositivesToAvoid = "A blank zombie stare. Keep facial warmth active through micro-eyebrow appreciation and attentive listening.",
                    relatedBteSymbol = "St"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Caught Myself Rushing",
                    quickHint = "Self-audit marker: noticing an involuntary rush, bobble-head, or vocal uptalk",
                    physicalTells = listOf(
                        "Catching yourself speaking too quickly without breathing",
                        "Noticing repetitive appeasement nods while the other person talks",
                        "Catching hands nervously adjusting clothing or tapping fingers",
                        "Ending a sentence with a high-pitched upward inflection"
                    ),
                    realWorldExample = "You realize you just gave 5 rapid nods and answered 'Sure thing?' with a rising pitch. You immediately catch it, pause, drop your shoulders, and slow down.",
                    psychologicalWhy = "Metacognitive calibration: Becoming conscious of conditioned appeasement behaviors is the indispensable prerequisite to rewriting autonomic habits.",
                    falsePositivesToAvoid = "Self-criticism. Tally this as a training victory—catching a habit is the exact mechanism of behavioral neuroplasticity.",
                    relatedBteSymbol = "Ca"
                )
            )
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
            skillAxisAffected = SkillAxis.TRUST_RAPPORT,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Time Constraint Deployed",
                    quickHint = "Explicit verbal boundary stating you must leave in 20–30 seconds",
                    physicalTells = listOf(
                        "Phrase: 'I only have about 30 seconds before my ride arrives, but...' or 'I've gotta run in a second...'",
                        "Feet remain angled slightly toward your walking path (physical corroboration)",
                        "Quick glance at watch or phone confirming external time pressure",
                        "Energetic, upbeat vocal delivery without desperation"
                    ),
                    realWorldExample = "Standing at a bookstore: 'Hey, I literally have 30 seconds before my parking meter expires, but which of these two translations did you find better?'",
                    psychologicalWhy = "Amygdala boundary relief: When strangers approach, the brain fears an open-ended conversational entrapment. An explicit boundary completely disarms this defense.",
                    falsePositivesToAvoid = "Sounding stressed, impatient, or panicked. The time constraint is a courtesy, not an emergency.",
                    relatedBteSymbol = "Tc"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Defensive Wall Dropped",
                    quickHint = "Subject shoulders drop, crossed arms uncoil, eye contact softens warmly",
                    physicalTells = listOf(
                        "Immediate downward drop of the trapezius/shoulders (stress sigh)",
                        "Arms uncrossing from ventral shielding to open posture",
                        "Head tilts forward with genuine engagement",
                        "Smile switches from polite/social to authentic Duchenne smile"
                    ),
                    realWorldExample = "The moment you say 'I only have 30 seconds...', the stranger's stiff posture relaxes, they turn their body toward you, and their voice warms up.",
                    psychologicalWhy = "Threat deflation: Recognizing that you have no intention of selling, pitching, or trapping them triggers mammalian social safety.",
                    falsePositivesToAvoid = "Polite nods that are still physically defensive (e.g. feet still pointed away).",
                    relatedBteSymbol = "Wd"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Subject Extended Chat",
                    quickHint = "Subject attempts to prolong interaction, asks follow-up questions, or steps closer",
                    physicalTells = listOf(
                        "Subject steps closer or leans in to continue speaking",
                        "Subject asks you a return question: 'Oh really, where are you heading?'",
                        "Subject touches your arm or offers unexpected extra advice",
                        "Ignores your stated time limit because rapport feels intoxicating"
                    ),
                    realWorldExample = "After answering your quick question, they say: 'Wait, if you like that author, have you read their earlier work too? Let me show you where it is...'",
                    psychologicalWhy = "Scarcity & high-value dynamics: People place higher value on conversations that are scarce and non-needy. They chase what moves away smoothly.",
                    falsePositivesToAvoid = "Someone who is notoriously chatty with everyone. Notice if they lean in specifically after your constraint.",
                    relatedBteSymbol = "Ec"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Clean High-Note Exit",
                    quickHint = "Departing while conversation is at its peak, leaving positive oxytocin resonance",
                    physicalTells = listOf(
                        "Exiting while both people are smiling or laughing (not waiting for awkward silence)",
                        "Warm closing statement: 'Thanks, that's exactly what I needed—have an awesome afternoon!'",
                        "Smooth pivot and purposeful departure at steady 80% pace",
                        "Zero lingering or needy backwards glances"
                    ),
                    realWorldExample = "They finish giving great advice; you smile warmly, say 'I appreciate you, gotta run!', and walk away smoothly while they are still glowing from the exchange.",
                    psychologicalWhy = "Peak-End Rule (Kahneman): Human memory evaluates an interaction almost entirely by its emotional peak and its ending. Leaving at the peak anchors pure positive association.",
                    falsePositivesToAvoid = "Abruptly cutting someone off mid-sentence. Gracefully transition on a laugh or natural completion.",
                    relatedBteSymbol = "Ex"
                )
            )
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
            skillAxisAffected = SkillAxis.TRUST_RAPPORT,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Controlled Flaw Shared",
                    quickHint = "Sharing a low-stakes, relatable imperfection to disarm distrust of perfection",
                    physicalTells = listOf(
                        "Micro-admission delivered with self-deprecating warmth, not self-pity",
                        "Phrases: 'My brain is running on 70% battery today until this coffee kicks in' or 'I made a mess of my spreadsheet this morning'",
                        "Relaxed posture with slight smile and head tilt",
                        "Kept strictly to low-stakes topics (fatigue, caffeine, trivial tech hiccup)"
                    ),
                    realWorldExample = "To a cashier or colleague: 'I'm embarrassed to admit it took me three tries to enter my own PIN code this morning.'",
                    psychologicalWhy = "Pratfall Effect & Trust Triad: Flawless facades trigger subconscious suspicion. Admitting a small flaw proves authenticity and eliminates the threat of deception.",
                    falsePositivesToAvoid = "Oversharing catastrophic personal drama (divorce, legal troubles, deep emotional instability) which triggers revulsion, not trust.",
                    relatedBteSymbol = "Cf"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Empathy Label Used",
                    quickHint = "Naming the other person's emotional state ('It looks like you've had a marathon morning')",
                    physicalTells = listOf(
                        "Phrase formula: 'It feels like / It seems like / It sounds like...'",
                        "Down-pitch inflection on the label (not an inquisitive tone)",
                        "Delivered with steady, compassionate eye contact",
                        "Followed by 3 seconds of total silence to let the label land"
                    ),
                    realWorldExample = "To a busy barista or service worker: 'It looks like the morning rush hit you guys from every direction all at once today.'",
                    psychologicalWhy = "Neural resonance & amygdala dampening: Labeling an emotion activates the prefrontal cortex and de-escalates limbic stress, fostering intense subconscious gratitude.",
                    falsePositivesToAvoid = "Using 'I understand how you feel' (which centers yourself). Always use third-person observation: 'It seems like...'",
                    relatedBteSymbol = "El"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Subject Smiled / Opened Up",
                    quickHint = "Subject shoulders drop, eyes soften, genuine sigh of relief or laugh",
                    physicalTells = listOf(
                        "Spontaneous chuckle or full smile acknowledging the truth of your label",
                        "Audible exhale: 'Oh man, you have no idea...'",
                        "Ventral alignment: leaning forward over the counter or desk",
                        "Direct, appreciative eye contact"
                    ),
                    realWorldExample = "The barista laughs, shakes their head, and smiles: 'You have no idea! Two people called in sick and our blender broke!'",
                    psychologicalWhy = "Oxytocin release through validation: Being genuinely seen and understood without judgment floods the brain with social bonding chemicals.",
                    falsePositivesToAvoid = "A flat customer service response without emotional resonance.",
                    relatedBteSymbol = "So"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Reciprocal Disclosure",
                    quickHint = "Subject voluntarily shares their own private frustration, story, or perk",
                    physicalTells = listOf(
                        "Subject shares insider information, candid work details, or extra perks",
                        "Subject lowers voice slightly to share something confidential or personal",
                        "Expresses genuine personal opinions rather than corporate scripted responses",
                        "Lingers to connect on a human-to-human level"
                    ),
                    realWorldExample = "The barista adds extra flavor or upgrades the drink size: 'Don't worry about the upcharge, you just made my whole morning.'",
                    psychologicalWhy = "The Vulnerability Loop: When party A displays controlled vulnerability, party B reciprocates with disclosure, cementing high trust in under 60 seconds.",
                    falsePositivesToAvoid = "Expecting material rewards. The victory is emotional reciprocity and open communication.",
                    relatedBteSymbol = "Rd"
                )
            )
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
            skillAxisAffected = SkillAxis.ELLIPSIS_ELICITATION,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Assumptive Statement Used",
                    quickHint = "Making a confident statement about them that compels them to confirm or correct",
                    physicalTells = listOf(
                        "Statement delivered with downward inflection: 'You look like someone who deals with operations, not sales.'",
                        "Zero question words ('Who/What/Where/When/Why/How')",
                        "Slight chin tilt with comfortable pause immediately after the statement",
                        "Tone is thoughtful observation, not accusatory"
                    ),
                    realWorldExample = "'You sound like you've been managing teams in this city for at least ten years.'",
                    psychologicalWhy = "The Correction Urge: The human ego cannot tolerate an inaccurate or incomplete assumption about its identity. People will voluntarily correct a statement with 3x more detail than answering a question.",
                    falsePositivesToAvoid = "Making offensive, insulting, or overly intimate assumptions that trigger defensiveness.",
                    relatedBteSymbol = "As"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Disbelief / Flattery Used",
                    quickHint = "Expressing mild skepticism that baiting their ego into proving their accomplishment",
                    physicalTells = listOf(
                        "Phrases: 'There is no way three people handled that entire launch alone...' or 'I doubt that department gave up budget without a fight.'",
                        "Slight eyebrow raise and respectful skepticism",
                        "Leaves a wide opening for them to boast or explain the inside truth",
                        "Warm, impressed vocal cadence"
                    ),
                    realWorldExample = "'I can't imagine leadership signed off on that kind of budget without six months of committee review.'",
                    psychologicalWhy = "Ego defense & validation trap: Challenging their capability gently creates an irresistible desire to prove competence by spilling operational details.",
                    falsePositivesToAvoid = "Sounding cynical or insulting. The disbelief must be paired with admiration.",
                    relatedBteSymbol = "Df"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Subject Corrected/Elaborated",
                    quickHint = "Subject launches into a multi-sentence explanation revealing inside details",
                    physicalTells = listOf(
                        "Subject leans in and begins with: 'Actually, the truth is...' or 'Well, not exactly...'",
                        "Speaks continuously for >20 seconds without prompting",
                        "Gestures become expansive, showing excitement to set the record straight",
                        "Leaks organizational, personal, or strategic details freely"
                    ),
                    realWorldExample = "After your disbelief statement: 'Well, actually! We bypassed the VP entirely because we had direct backing from the CEO, and we worked every weekend in March...'",
                    psychologicalWhy = "Autonomous self-disclosure: Because they are *correcting* you rather than answering an interrogation, their guard remains completely lowered.",
                    falsePositivesToAvoid = "A simple 1-word 'Yup'. If they give a 1-word answer, the assumption was too generic.",
                    relatedBteSymbol = "Ce"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Accidentally Asked Question",
                    quickHint = "Catching an accidental 'What do you do?' or 'How long have you lived here?'",
                    physicalTells = listOf(
                        "Catching yourself using question interrogatives ('What', 'Where', 'How', 'Did you')",
                        "Noticing an upward inflection at the end of the sentence",
                        "Observing the subject's guard instantly stiffen into 'interview mode'"
                    ),
                    realWorldExample = "You slip and say: 'So what made you choose this career?' and notice their answers become concise and guarded.",
                    psychologicalWhy = "Interrogation awareness: Direct questions feel transactional and evaluative, shifting the brain from creative storytelling into risk calculation.",
                    falsePositivesToAvoid = "Dwelling on the mistake; immediately follow up with an assumptive observation.",
                    relatedBteSymbol = "Aq"
                )
            )
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
            skillAxisAffected = SkillAxis.SIX_MX_PROFILING,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Social Need Identified",
                    quickHint = "Pinpointing the core fuel: Significance, Approval, Acceptance, Intelligence, Pity, Power",
                    physicalTells = listOf(
                        "Significance/Power: Boasts about influence, leadership, status, or being irreplaceable",
                        "Approval/Acceptance: Emphasizes team harmony, being liked, or conforms to group consensus",
                        "Intelligence: Mentions data, books, systems, or emphasizes being factually correct",
                        "Pity: Emphasizes unfair burdens, lack of support, or how hard they work without thanks"
                    ),
                    realWorldExample = "A project manager mentions: 'Everyone else panicked, but I sat down and built a 14-tab architectural framework that solved the bottleneck.' -> PRIMARY NEED: Intelligence.",
                    psychologicalWhy = "Core evolutionary driver: Social Needs are subconscious survival scripts developed in childhood to secure attention, safety, and resources.",
                    falsePositivesToAvoid = "Projecting YOUR own social need onto them. Listen purely to where their stories place value.",
                    relatedBteSymbol = "6MX"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Decision Style Spotted",
                    quickHint = "Auditing how they make choices: Impulsive, Analytical, Consensus, or Gut/Value",
                    physicalTells = listOf(
                        "Analytical: Asks for specs, documentation, timelines, proof",
                        "Consensus: Asks who else uses it, what their team thinks, checks reviews",
                        "Impulsive: Makes rapid intuitive leaps, talks about energy and momentum",
                        "Value/Safety: Focuses on risk prevention, guarantees, and past track records"
                    ),
                    realWorldExample = "When discussing a restaurant or trip: 'I read 40 reviews and cross-referenced the inspection scores' -> Analytical Decision Style.",
                    psychologicalWhy = "Cognitive filtering: Each decision style represents a heuristic shortcut to mitigate regret and preserve energy.",
                    falsePositivesToAvoid = "Mistaking a slow decision-maker for an analytical one; consensus-seekers can also be slow while waiting for group approval.",
                    relatedBteSymbol = "Ds"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Targeted Validation Delivered",
                    quickHint = "Feeding their exact Social Need with a tailored verbal acknowledgement",
                    physicalTells = listOf(
                        "For Intelligence: 'The analytical rigor behind that system is exceptionally rare.'",
                        "For Significance: 'Most people wouldn't have had the authority to pull that off.'",
                        "For Pity: 'It's unbelievable how much you carried on your shoulders with zero support.'",
                        "For Approval: 'Your team is unbelievably lucky to have someone who cares this much about the people.'"
                    ),
                    realWorldExample = "You look directly at the analytical manager and say: 'Most people would have guessed; taking the time to architect the real data logic is what saved that project.'",
                    psychologicalWhy = "Dopamine & emotional fulfillment: Hearing their deepest subconscious need validated by an outside observer creates instant, magnetic rapport.",
                    falsePositivesToAvoid = "Generic flattery ('You're so great!'). It must be laser-targeted to their specific need.",
                    relatedBteSymbol = "Tv"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Homeostasis / Relief Seen",
                    quickHint = "Physical sigh of relief, chest expansion, dropping of shoulders, rapid agreement",
                    physicalTells = listOf(
                        "Deep, audible exhalation (respiratory reset)",
                        "Shoulders drop 1–2 inches down away from ears",
                        "Chest opens and torso leans forward toward you",
                        "Enthusiastic verbal agreement: 'YES! Exactly! You completely understand!'"
                    ),
                    realWorldExample = "The moment you validate their burden, their face relaxes completely, their shoulders slump in relief, and they exhale: 'Finally, someone who actually sees it!'",
                    psychologicalWhy = "Vagal brake and limbic resolution: Validating their primary need satisfies their autonomic hunger, shifting their nervous system into rest-and-digest safety.",
                    falsePositivesToAvoid = "Confusing polite agreement with a genuine visceral relaxation response.",
                    relatedBteSymbol = "Hm"
                )
            )
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
            skillAxisAffected = SkillAxis.ELLIPSIS_ELICITATION,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Word Harvested",
                    quickHint = "Catching unique sensory/emotional predicates (e.g. 'bulletproof', 'chaotic', 'nightmare')",
                    physicalTells = listOf(
                        "Identify unique metaphors or emotionally weighted adjectives",
                        "Notice sensory predicates (Visual: 'I see', Auditory: 'That sounds', Kinesthetic: 'Feels heavy')",
                        "Mentally tag words that the subject repeats with vocal emphasis",
                        "Avoid standard generic words; harvest the evocative personal vocabulary"
                    ),
                    realWorldExample = "A client says: 'We need this rollout to be totally *frictionless*, because the last vendor was an absolute *landmine*.' You mentally log: 'frictionless' and 'landmine'.",
                    psychologicalWhy = "Linguistic neuro-mapping: Each individual's brain constructs emotional reality through a specific lexicon. Using their lexicon bypasses cognitive skepticism.",
                    falsePositivesToAvoid = "Harvesting overused buzzwords that carry no personal emotional charge.",
                    relatedBteSymbol = "Lh"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Harvested Word Echoed",
                    quickHint = "Feeding their exact harvested word back 2 minutes later in your own sentence",
                    physicalTells = listOf(
                        "Deploy the harvested word naturally in a later sentence without quotation marks",
                        "Pair with a subtle hand gesture toward the same spatial quadrant",
                        "Maintain steady, relaxed eye contact",
                        "Observe whether the subject nods with visceral familiarity"
                    ),
                    realWorldExample = "Two minutes later: 'If we want this process to stay completely *frictionless*, the first step is...' The client instantly nods vigorously.",
                    psychologicalWhy = "Subconscious echo-recognition: When someone hears their own internal vocabulary spoken by another, the brain processes it as its own thought, eliminating objection resistance.",
                    falsePositivesToAvoid = "Using the word with sarcasm or repeating it immediately like a parrot. Space it out by 1–3 minutes.",
                    relatedBteSymbol = "We"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "Micro-Compliance Step 1/2",
                    quickHint = "Securing 1) Micro verbal 'Yes', then 2) Tiny physical collaboration",
                    physicalTells = listOf(
                        "Step 1: Low-friction agreement ('Does that timeline make basic sense?') -> secures verbal agreement",
                        "Step 2: Tiny physical compliance ('Could you slide that document closer?' or 'Take a look at line 4 with me') -> secures motor compliance",
                        "Subject complies effortlessly without hesitation",
                        "Both steps feel organic, tiny, and non-threatening"
                    ),
                    realWorldExample = "In a meeting: 1) 'We'd all agree reliability matters most, right?' ('Yes'). 2) 'Take a quick look at this chart with me' (They lean in and turn the page).",
                    psychologicalWhy = "Commitment & Consistency bias (Cialdini): The nervous system seeks self-consistency. Once physical and verbal compliance is initiated, the threshold for future compliance drops by 60%.",
                    falsePositivesToAvoid = "Skipping straight to a major request. The steps must be tiny and effortless.",
                    relatedBteSymbol = "Mc"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Target Agreement Reached",
                    quickHint = "Presenting the primary objective at the top of the ladder and securing commitment",
                    physicalTells = listOf(
                        "Subject agrees to the primary request with open posture",
                        "No signs of buyer's remorse (no lip compression, no neck clutching)",
                        "Subject maintains steady forward eye contact and comfortable breathing",
                        "Closing handshake or definitive scheduling confirmed"
                    ),
                    realWorldExample = "Following micro-compliances, you propose: 'Let's pilot this with team A for two weeks starting Monday.' The subject agrees immediately without resistance.",
                    psychologicalWhy = "Momentum engineering: The subject has already complied 3 times in 5 minutes; saying yes to the final step feels like the only congruent path forward.",
                    falsePositivesToAvoid = "Over-talking after the agreement is reached. Stop talking the moment commitment is given.",
                    relatedBteSymbol = "Ta"
                )
            )
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
            skillAxisAffected = SkillAxis.BTE_DETECTION,
            targetBehaviors = listOf(
                TargetBehaviorGuide(
                    quadrantIndex = 0,
                    cueName = "Focus / Authority Lever",
                    quickHint = "Breaking expected pattern (curiosity) + grounded 20% slower movement and downward pitch",
                    physicalTells = listOf(
                        "Focus: Open with a surprising pattern interrupt or question that halts autopilot",
                        "Authority: 20% slower kinematics, grounded spine, relaxed diaphragmatic breathing",
                        "Downward vocal inflection at sentence endings",
                        "Total absence of fidgeting or appeasement bobble-heads"
                    ),
                    realWorldExample = "Opening an important pitch: You pause for 3 seconds of calm stillness, then say: 'Before we look at the numbers, there is one fatal assumption in the current plan that nobody is talking about.'",
                    psychologicalWhy = "Reticular Activating System (RAS) hijack: Novelty captures 100% of sensory processing, while apex mammalian kinematics command involuntary deference.",
                    falsePositivesToAvoid = "Aggression or arrogance. True authority is calm, relaxed, and benevolent.",
                    relatedBteSymbol = "Fa"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 1,
                    cueName = "Tribe ('We') Frame Used",
                    quickHint = "Framing the challenge as 'We/Us' against a shared obstacle or outside threat",
                    physicalTells = listOf(
                        "Substitute 'I / You' with 'We / Us / Our team'",
                        "Define a common friction or competitor as the outside antagonist",
                        "Open palm gesture encompassing both yourself and the partner",
                        "Subject shifts from defensive adversary to collaborative ally"
                    ),
                    realWorldExample = "'What we are up against isn't each other; it's the fact that our competitors are moving twice as fast with lower overhead.'",
                    psychologicalWhy = "In-group oxytocin bonding: Evolutionarily, the human brain bifurcates the world into In-Group (protect) and Out-Group (compete). Moving into their in-group dissolves all opposition.",
                    falsePositivesToAvoid = "Forced or superficial camaraderie that sounds like cheap sales manipulation.",
                    relatedBteSymbol = "Tr"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 2,
                    cueName = "BTE Stress Cue Spotted",
                    quickHint = "Spotting deception or hidden objection: Lip Compression (Lp), Neck Touch (Fc), Blink Spike (Br)",
                    physicalTells = listOf(
                        "Lp: Lips roll inward into a thin line (withholding objection)",
                        "Fc: Hand touches suprasternal notch or squeezes back of neck (stress pacifier)",
                        "Br: Blink rate spikes 3x within 7 seconds of discussing price or terms",
                        "Vd: Torso or lead foot rotates away toward the door"
                    ),
                    realWorldExample = "When discussing the deadline, the partner says 'Sure, that's fine' while compressing their lips (Lp=4) and rubbing their neck (Fc=3) — BTE Score = 7 in 4 seconds.",
                    psychologicalWhy = "Subconscious leak: While social conditioning produces verbal compliance, limbic autonomic arousal leaks hidden disagreements through micro-gestures.",
                    falsePositivesToAvoid = "Calling them out directly. Instead, use an elicitation probe: 'It seems like there might be a snag with that deadline...'",
                    relatedBteSymbol = "BTE"
                ),
                TargetBehaviorGuide(
                    quadrantIndex = 3,
                    cueName = "Comfort Cluster Confirmed",
                    quickHint = "Confirming genuine buy-in: Eyebrow Flash (Ef), Ventral Fronting (Vf), Open Palms (Op)",
                    physicalTells = listOf(
                        "Ventral Fronting: Torso squared up, leaning forward",
                        "Open palms facing upward or resting smoothly on the table",
                        "Relaxed breathing and smooth, melodic voice tone",
                        "BTE Score = 0; complete absence of pacifying gestures"
                    ),
                    realWorldExample = "After addressing the snag, the partner uncrosses their arms, smiles warmly with crow's feet, and says: 'Now that makes complete sense, let's do it.'",
                    psychologicalWhy = "Limbic homeostasis: The agreement has been cleared by both conscious reasoning and unconscious safety circuits; zero cognitive dissonance remains.",
                    falsePositivesToAvoid = "Premature celebration before the comfort cluster is observed.",
                    relatedBteSymbol = "Cc"
                )
            )
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
