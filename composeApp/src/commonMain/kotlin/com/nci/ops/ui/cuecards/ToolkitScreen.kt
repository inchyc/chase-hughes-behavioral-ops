package com.nci.ops.ui.cuecards

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
import com.nci.ops.data.PocketCueCardsCatalog
import com.nci.ops.model.DecisionStyleType
import com.nci.ops.model.PocketCueCard
import com.nci.ops.model.SocialNeedType
import com.nci.ops.model.SubjectDossier
import com.nci.ops.storage.OpsRepository
import com.nci.ops.storage.PersistedAppState
import com.nci.ops.ui.theme.*

enum class ToolkitSubTab(val label: String) {
    POCKET_CUE_CARDS("30s Pocket Cue Cards"),
    SIX_MX_DOSSIERS("6MX Profiler & Dossiers")
}

@Composable
fun ToolkitScreen(appState: PersistedAppState) {
    var activeTab by remember { mutableStateOf(ToolkitSubTab.POCKET_CUE_CARDS) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "PRE-MEETING CUE CARDS & 6MX TOOLKIT",
            color = CyanPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.3.sp
        )
        Text(
            text = "30-Second Scripts & Subject Dossiers",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToolkitSubTab.entries.forEach { tab ->
                val isSelected = activeTab == tab
                Button(
                    onClick = { activeTab = tab },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) CyanPrimary else TacticalCard,
                        contentColor = if (isSelected) TacticalObsidian else TextPrimary
                    )
                ) {
                    Text(tab.label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        when (activeTab) {
            ToolkitSubTab.POCKET_CUE_CARDS -> PocketCueCardsView()
            ToolkitSubTab.SIX_MX_DOSSIERS -> SixMxDossiersView(appState = appState)
        }
    }
}

@Composable
private fun PocketCueCardsView() {
    var selectedCard by remember { mutableStateOf(PocketCueCardsCatalog.cards.first()) }

    Text(
        text = "TAP ANY CARD FOR A 30-SECOND PRE-INTERACTION BRIEFING:",
        color = TextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )

    // Quick Grid of 9 Cue Cards (3 per row)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PocketCueCardsCatalog.cards.chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { card ->
                    val isSelected = card.id == selectedCard.id
                    Surface(
                        color = if (isSelected) CyanPrimary.copy(alpha = 0.2f) else TacticalCard,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) CyanPrimary else TacticalBorder
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedCard = card }
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = card.category,
                                color = AmberAlert,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = card.title,
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

    // ACTIVE 30-SECOND POCKET CUE CARD DETAIL
    PocketCueCardDetailView(card = selectedCard)
}

@Composable
private fun PocketCueCardDetailView(card: PocketCueCard) {
    Surface(
        color = TacticalSurface,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, CyanPrimary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.badgeText,
                    color = AmberAlert,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "30-SEC FIELD CARD",
                    color = CyanPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = card.title,
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = card.thirtySecondSummary,
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )

            // TELLS
            Surface(
                color = TacticalCard,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, TacticalBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "👁️ VISUAL & VERBAL TELLS TO SPOT",
                        color = CyanPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    card.visualAndVerbalTells.forEach { tell ->
                        Text(
                            text = "• $tell",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }

            // EXACT PHRASES TO USE
            Surface(
                color = TacticalObsidian,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, EmeraldComfort.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "✓ HIGH-LEVERAGE PHRASES TO DEPLOY (OXYTOCIN / HOMEOSTASIS)",
                        color = EmeraldComfort,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    card.exactPhrasesToUse.forEach { phrase ->
                        Text(
                            text = "• $phrase",
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }

            // FATAL ERRORS TO AVOID
            Surface(
                color = TacticalObsidian,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, CrimsonThreshold.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "✗ FATAL ERRORS TO AVOID (TRIGGERS CORTISOL / RESISTANCE)",
                        color = CrimsonThreshold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    card.fatalErrorsToAvoid.forEach { err ->
                        Text(
                            text = "• $err",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }

            // NONVERBAL CALIBRATION
            Text(
                text = "Body Language Calibration: ${card.bodyLanguageCalibration}",
                color = AmberAlert,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SixMxDossiersView(appState: PersistedAppState) {
    var aliasInput by remember { mutableStateOf("") }
    var roleInput by remember { mutableStateOf("") }
    var primaryNeed by remember { mutableStateOf(SocialNeedType.SIGNIFICANCE) }
    var secondaryNeed by remember { mutableStateOf(SocialNeedType.INTELLIGENCE) }
    var decisionStyle by remember { mutableStateOf(DecisionStyleType.ANALYTICAL) }
    var baselineInput by remember { mutableStateOf("") }
    var predicatesInput by remember { mutableStateOf("") }
    var wobbleInput by remember { mutableStateOf("") }

    Surface(
        color = TacticalCard,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, TacticalBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "6MX RAPID HUMAN COMPASS & DOSSIER BUILDER",
                color = CyanPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = aliasInput,
                    onValueChange = { aliasInput = it },
                    label = { Text("Subject Alias / Code") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = roleInput,
                    onValueChange = { roleInput = it },
                    label = { Text("Context / Role") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Text("Primary Social Need:", color = AmberAlert, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SocialNeedType.entries.forEach { need ->
                    val isSelected = primaryNeed == need
                    Surface(
                        color = if (isSelected) CyanPrimary else TacticalObsidian,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, TacticalBorder),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { primaryNeed = need }
                    ) {
                        Text(
                            text = need.shortCode,
                            color = if (isSelected) TacticalObsidian else TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                        )
                    }
                }
            }

            Text("Secondary Social Need:", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SocialNeedType.entries.forEach { need ->
                    val isSelected = secondaryNeed == need
                    Surface(
                        color = if (isSelected) PurpleEllipsis else TacticalObsidian,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, TacticalBorder),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { secondaryNeed = need }
                    ) {
                        Text(
                            text = need.shortCode,
                            color = if (isSelected) TacticalObsidian else TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                        )
                    }
                }
            }

            Text("Decision Style:", color = EmeraldComfort, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                DecisionStyleType.entries.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        row.forEach { ds ->
                            val isSelected = decisionStyle == ds
                            Surface(
                                color = if (isSelected) EmeraldComfort else TacticalObsidian,
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, TacticalBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { decisionStyle = ds }
                            ) {
                                Text(
                                    text = ds.title,
                                    color = if (isSelected) TacticalObsidian else TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Instant Generated Strategy Preview
            Surface(
                color = TacticalObsidian,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "6MX TACTICAL PRESCRIPTION: ${primaryNeed.title.uppercase()} + ${decisionStyle.title.uppercase()}",
                        color = CyanPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "• Core Driver: ${primaryNeed.coreDriver}\n• Threat to Avoid: ${primaryNeed.fearAvoidance}\n• Decision Pitch Rule: ${decisionStyle.coreRule}",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            OutlinedTextField(
                value = baselineInput,
                onValueChange = { baselineInput = it },
                label = { Text("Subject Baseline & BTE Stress Cues") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = predicatesInput,
                onValueChange = { predicatesInput = it },
                label = { Text("Harvested Linguistic Words & Anchors") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = wobbleInput,
                onValueChange = { wobbleInput = it },
                label = { Text("Trust Code Notes (Authenticity / Empathy / Logic)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (aliasInput.isNotBlank()) {
                        OpsRepository.saveDossier(
                            SubjectDossier(
                                id = "dos_${appState.dossiers.size + 1}_${aliasInput.hashCode()}",
                                alias = aliasInput.trim(),
                                contextOrRole = roleInput.ifBlank { "General Field Profile" },
                                primarySocialNeed = primaryNeed,
                                secondarySocialNeed = secondaryNeed,
                                decisionStyle = decisionStyle,
                                baselineNotes = baselineInput,
                                harvestedPredicates = predicatesInput,
                                trustWobbleNotes = wobbleInput
                            )
                        )
                        aliasInput = ""
                        roleInput = ""
                        baselineInput = ""
                        predicatesInput = ""
                        wobbleInput = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyanPrimary,
                    contentColor = TacticalObsidian
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Save Subject Dossier (+100 XP)", fontWeight = FontWeight.ExtraBold)
            }
        }
    }

    // SAVED DOSSIERS LIST
    Text(
        text = "SAVED SUBJECT DOSSIERS (${appState.dossiers.size})",
        color = TextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )

    appState.dossiers.forEach { dos ->
        Surface(
            color = TacticalCard,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, TacticalBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(dos.alias, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                        Text(dos.contextOrRole, color = TextSecondary, fontSize = 12.sp)
                    }
                    Surface(
                        color = CyanPrimary.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, CyanPrimary)
                    ) {
                        Text(
                            text = "${dos.primarySocialNeed.shortCode}/${dos.secondarySocialNeed.shortCode} • ${dos.decisionStyle.title}",
                            color = CyanPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                if (dos.baselineNotes.isNotBlank()) {
                    Text("Baseline/BTE: ${dos.baselineNotes}", color = TextPrimary, fontSize = 12.sp)
                }
                if (dos.harvestedPredicates.isNotBlank()) {
                    Text("Harvested Words: ${dos.harvestedPredicates}", color = AmberAlert, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                if (dos.trustWobbleNotes.isNotBlank()) {
                    Text("Trust Strategy: ${dos.trustWobbleNotes}", color = EmeraldComfort, fontSize = 12.sp)
                }
            }
        }
    }
}
