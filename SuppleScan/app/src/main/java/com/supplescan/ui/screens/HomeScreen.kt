package com.supplescan.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supplescan.data.repository.ScannedSupplementSummary
import com.supplescan.domain.scoring.ScoringEngine
import com.supplescan.ui.components.ScoreBadge
import com.supplescan.ui.theme.SuppleScanGreen

/**
 * Home screen with quick stats and recent scans.
 */
@Composable
fun HomeScreen(
    recentScans: List<ScannedSupplementSummary>,
    onScanClick: () -> Unit,
    onItemClick: (String) -> Unit,
    onManualEntryClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // ─── Hero Banner ──────────────────────────────────────────────────────
        HeroBanner(onScanClick = onScanClick)

        Spacer(Modifier.height(16.dp))

        // ─── Quick Actions ────────────────────────────────────────────────────
        QuickActions(
            onScanClick = onScanClick,
            onManualEntryClick = onManualEntryClick
        )

        Spacer(Modifier.height(20.dp))

        // ─── Stats ────────────────────────────────────────────────────────────
        if (recentScans.isNotEmpty()) {
            StatsRow(scans = recentScans)
            Spacer(Modifier.height(20.dp))
        }

        // ─── Recent Scans ─────────────────────────────────────────────────────
        if (recentScans.isNotEmpty()) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Scansioni recenti", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            recentScans.take(5).forEach { item ->
                RecentScanCard(item = item, onClick = { onItemClick(item.barcode) })
                Spacer(Modifier.height(8.dp))
            }
        } else {
            EmptyHomeState(onScanClick = onScanClick)
        }

        Spacer(Modifier.height(80.dp)) // Bottom nav space
    }
}

@Composable
private fun HeroBanner(onScanClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF00BFA5), Color(0xFF00897B))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "💊 SuppleScan",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Analizza la qualità dei tuoi integratori",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onScanClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF00897B)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Scansiona ora", fontWeight = FontWeight.Bold)
            }
        }

        // Decorative icon
        Icon(
            imageVector = Icons.Default.Science,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.1f),
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        )
    }
}

@Composable
private fun QuickActions(onScanClick: () -> Unit, onManualEntryClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionCard(
            icon = Icons.Default.CameraAlt,
            title = "Scansiona",
            subtitle = "Usa la fotocamera",
            color = SuppleScanGreen,
            modifier = Modifier.weight(1f),
            onClick = onScanClick
        )
        QuickActionCard(
            icon = Icons.Default.Edit,
            title = "Inserisci",
            subtitle = "Barcode manuale",
            color = Color(0xFF7986CB),
            modifier = Modifier.weight(1f),
            onClick = onManualEntryClick
        )
    }
}

@Composable
private fun QuickActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
            Text(subtitle, fontSize = 11.sp, color = color.copy(alpha = 0.7f))
        }
    }
}

@Composable
private fun StatsRow(scans: List<ScannedSupplementSummary>) {
    val avg = scans.map { it.score }.average().toInt()
    val excellent = scans.count { it.score >= 80 }
    val caution = scans.count { it.score < 50 }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard("Media", "$avg", "punteggio", SuppleScanGreen, Modifier.weight(1f))
        StatCard("Top", "$excellent", "ottimi", Color(0xFF2E7D32), Modifier.weight(1f))
        StatCard("Attenzione", "$caution", "da controllare", Color(0xFFFF7043), Modifier.weight(1f))
    }
}

@Composable
private fun StatCard(title: String, value: String, unit: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(unit, fontSize = 10.sp, color = color.copy(alpha = 0.7f))
            Text(title, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun RecentScanCard(
    item: ScannedSupplementSummary,
    onClick: () -> Unit
) {
    val grade = scoreToGrade(item.score)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreBadge(score = item.score, grade = grade)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, maxLines = 1)
                Text(item.brand, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun EmptyHomeState(onScanClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🔍", fontSize = 48.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            "Inizia scansionando il tuo primo integratore!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "SuppleScan analizza gli ingredienti e ti dice se il prodotto è sicuro ed efficace.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

private fun scoreToGrade(score: Int): ScoringEngine.ScoreGrade = when (score) {
    in 90..100 -> ScoringEngine.ScoreGrade.A_PLUS
    in 80..89  -> ScoringEngine.ScoreGrade.A
    in 65..79  -> ScoringEngine.ScoreGrade.B
    in 50..64  -> ScoringEngine.ScoreGrade.C
    in 30..49  -> ScoringEngine.ScoreGrade.D
    else       -> ScoringEngine.ScoreGrade.F
}
