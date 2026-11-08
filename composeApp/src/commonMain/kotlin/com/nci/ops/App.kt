package com.nci.ops

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nci.ops.model.CurriculumTrackId
import com.nci.ops.model.SkillAxis
import com.nci.ops.storage.OpsRepository
import com.nci.ops.ui.academy.AcademyScreen
import com.nci.ops.ui.academy.AcademySubTab
import com.nci.ops.ui.btelab.BteLabScreen
import com.nci.ops.ui.btelab.BteSubTab
import com.nci.ops.ui.cuecards.ToolkitScreen
import com.nci.ops.ui.cuecards.ToolkitSubTab
import com.nci.ops.ui.dashboard.DashboardScreen
import com.nci.ops.ui.fieldops.FieldOpsScreen
import com.nci.ops.ui.theme.*

enum class MainNavTab(val label: String, val badgeCode: String) {
    COMMAND("Command", "CMD"),
    FIELD_OPS("Field Ops", "OPS"),
    BTE_LAB("BTE Lab", "BTE"),
    TOOLKIT("Cue Cards", "6MX"),
    ACADEMY("Academy", "NCI")
}

@Composable
fun App() {
    val appState by OpsRepository.state.collectAsState()
    var currentTab by remember { mutableStateOf(MainNavTab.COMMAND) }
    var openStealthOnFieldOps by remember { mutableStateOf(false) }
    var fieldOpsInitialCueIndex by remember { mutableStateOf<Int?>(null) }
    var academyInitialSubTab by remember { mutableStateOf(AcademySubTab.CURRICULUM) }
    var academyInitialTrackId by remember { mutableStateOf<CurriculumTrackId?>(null) }
    var toolkitInitialSubTab by remember { mutableStateOf(ToolkitSubTab.POCKET_CUE_CARDS) }
    var toolkitInitialCardId by remember { mutableStateOf<String?>(null) }
    var bteLabInitialSubTab by remember { mutableStateOf(BteSubTab.LIVE_SCORER) }

    NciTacticalTheme {
        Scaffold(
            containerColor = TacticalObsidian,
            bottomBar = {
                Surface(
                    color = TacticalSurface,
                    border = BorderStroke(1.dp, TacticalBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MainNavTab.entries.forEach { tab ->
                            val isSelected = currentTab == tab
                            TextButton(
                                onClick = {
                                    openStealthOnFieldOps = false
                                    fieldOpsInitialCueIndex = null
                                    currentTab = tab
                                },
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 6.dp, horizontal = 2.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "[${tab.badgeCode}]",
                                        color = if (isSelected) CyanPrimary else TextSecondary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(
                                        text = tab.label,
                                        color = if (isSelected) TextPrimary else TextSecondary,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    MainNavTab.COMMAND -> DashboardScreen(
                        appState = appState,
                        onNavigateToFieldOps = { stealth ->
                            openStealthOnFieldOps = stealth
                            fieldOpsInitialCueIndex = null
                            currentTab = MainNavTab.FIELD_OPS
                        },
                        onNavigateToFieldOpCue = { cueIndex ->
                            openStealthOnFieldOps = false
                            fieldOpsInitialCueIndex = cueIndex
                            currentTab = MainNavTab.FIELD_OPS
                        },
                        onNavigateToSkillAxis = { axis ->
                            when (axis) {
                                SkillAxis.BASELINE_RECON -> {
                                    bteLabInitialSubTab = BteSubTab.PERIODIC_TABLE
                                    currentTab = MainNavTab.BTE_LAB
                                }
                                SkillAxis.NONVERBAL_AUTHORITY -> {
                                    academyInitialSubTab = AcademySubTab.CURRICULUM
                                    academyInitialTrackId = CurriculumTrackId.NCI_LEVEL_1
                                    currentTab = MainNavTab.ACADEMY
                                }
                                SkillAxis.TRUST_RAPPORT -> {
                                    toolkitInitialSubTab = ToolkitSubTab.POCKET_CUE_CARDS
                                    toolkitInitialCardId = "cue_trust_wobbles"
                                    currentTab = MainNavTab.TOOLKIT
                                }
                                SkillAxis.SIX_MX_PROFILING -> {
                                    toolkitInitialSubTab = ToolkitSubTab.SIX_MX_DOSSIERS
                                    currentTab = MainNavTab.TOOLKIT
                                }
                                SkillAxis.BTE_DETECTION -> {
                                    bteLabInitialSubTab = BteSubTab.LIVE_SCORER
                                    currentTab = MainNavTab.BTE_LAB
                                }
                                SkillAxis.ELLIPSIS_ELICITATION -> {
                                    toolkitInitialSubTab = ToolkitSubTab.POCKET_CUE_CARDS
                                    toolkitInitialCardId = "cue_fate_model"
                                    currentTab = MainNavTab.TOOLKIT
                                }
                            }
                        },
                        onNavigateToMicroHabitReview = { habitId ->
                            when (habitId) {
                                "habit_1_eye_contact" -> {
                                    toolkitInitialSubTab = ToolkitSubTab.POCKET_CUE_CARDS
                                    toolkitInitialCardId = "cue_sn_significance"
                                    currentTab = MainNavTab.TOOLKIT
                                }
                                "habit_2_controlled_vulnerability" -> {
                                    toolkitInitialSubTab = ToolkitSubTab.POCKET_CUE_CARDS
                                    toolkitInitialCardId = "cue_trust_wobbles"
                                    currentTab = MainNavTab.TOOLKIT
                                }
                                "habit_3_linguistic_elicitation" -> {
                                    academyInitialSubTab = AcademySubTab.CURRICULUM
                                    academyInitialTrackId = CurriculumTrackId.ELLIPSIS_MANUAL
                                    currentTab = MainNavTab.ACADEMY
                                }
                                "habit_4_bte_cluster_awareness" -> {
                                    bteLabInitialSubTab = BteSubTab.PERIODIC_TABLE
                                    currentTab = MainNavTab.BTE_LAB
                                }
                                else -> {
                                    toolkitInitialSubTab = ToolkitSubTab.POCKET_CUE_CARDS
                                    currentTab = MainNavTab.TOOLKIT
                                }
                            }
                        },
                        onNavigateToBteScorer = {
                            bteLabInitialSubTab = BteSubTab.LIVE_SCORER
                            currentTab = MainNavTab.BTE_LAB
                        },
                        onNavigateToCueCards = {
                            toolkitInitialSubTab = ToolkitSubTab.POCKET_CUE_CARDS
                            currentTab = MainNavTab.TOOLKIT
                        },
                        onNavigateToSrs = {
                            academyInitialSubTab = AcademySubTab.SRS_FLASHCARDS
                            currentTab = MainNavTab.ACADEMY
                        }
                    )
                    MainNavTab.FIELD_OPS -> FieldOpsScreen(
                        appState = appState,
                        initialStealthMode = openStealthOnFieldOps,
                        initialCueIndex = fieldOpsInitialCueIndex,
                        onNavigateToAcademyTrack = { trackId ->
                            academyInitialSubTab = AcademySubTab.CURRICULUM
                            academyInitialTrackId = trackId
                            currentTab = MainNavTab.ACADEMY
                        }
                    )
                    MainNavTab.BTE_LAB -> BteLabScreen(
                        initialSubTab = bteLabInitialSubTab
                    )
                    MainNavTab.TOOLKIT -> ToolkitScreen(
                        appState = appState,
                        initialSubTab = toolkitInitialSubTab,
                        initialCardId = toolkitInitialCardId
                    )
                    MainNavTab.ACADEMY -> AcademyScreen(
                        appState = appState,
                        initialSubTab = academyInitialSubTab,
                        initialTrackId = academyInitialTrackId,
                        onNavigateToFieldOps = {
                            openStealthOnFieldOps = false
                            fieldOpsInitialCueIndex = null
                            currentTab = MainNavTab.FIELD_OPS
                        }
                    )
                }
            }
        }
    }
}
