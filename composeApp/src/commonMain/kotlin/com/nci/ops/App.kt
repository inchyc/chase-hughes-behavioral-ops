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
import com.nci.ops.storage.OpsRepository
import com.nci.ops.ui.academy.AcademyScreen
import com.nci.ops.ui.academy.AcademySubTab
import com.nci.ops.ui.btelab.BteLabScreen
import com.nci.ops.ui.cuecards.ToolkitScreen
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
    var academyInitialSubTab by remember { mutableStateOf(AcademySubTab.CURRICULUM) }

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
                            currentTab = MainNavTab.FIELD_OPS
                        },
                        onNavigateToBteScorer = { currentTab = MainNavTab.BTE_LAB },
                        onNavigateToCueCards = { currentTab = MainNavTab.TOOLKIT },
                        onNavigateToSrs = {
                            academyInitialSubTab = AcademySubTab.SRS_FLASHCARDS
                            currentTab = MainNavTab.ACADEMY
                        }
                    )
                    MainNavTab.FIELD_OPS -> FieldOpsScreen(
                        appState = appState,
                        initialStealthMode = openStealthOnFieldOps
                    )
                    MainNavTab.BTE_LAB -> BteLabScreen()
                    MainNavTab.TOOLKIT -> ToolkitScreen(appState = appState)
                    MainNavTab.ACADEMY -> AcademyScreen(
                        appState = appState,
                        initialSubTab = academyInitialSubTab
                    )
                }
            }
        }
    }
}
