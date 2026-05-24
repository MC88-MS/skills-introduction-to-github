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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supplescan.data.model.*
import com.supplescan.domain.scoring.ScoringEngine
import com.supplescan.domain.scoring.toColor
import com.supplescan.ui.components.*

/**
 * Full product detail screen, showing score, ingredients, and analysis.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    supplement: Supplement,
    score: ScoringEngine.SupplementScore,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShare: () -> Unit
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = supplement.name,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Indietro")
                    }
                },
                actions = {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Preferito",
                            tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = onShare) {
                        Icon(Icons.Default.Share, contentDescription = "Condividi")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // ─── Hero Score Section ────────────────────────────────────────────
            item {
                ScoreHeroSection(supplement = supplement, score = score)
            }

            // ─── Score Breakdown ───────────────────────────────────────────────
            item {
                ScoreBreakdownSection(breakdown = score.breakdown)
            }

            // ─── Summary ──────────────────────────────────────────────────────
            item {
                SummarySection(summary = score.summary)
            }

            // ─── Warnings ─────────────────────────────────────────────────────
            if (supplement.warnings.isNotEmpty()) {
                item {
                    WarningsSection(warnings = supplement.warnings)
                }
            }

            // ─── Certifications ───────────────────────────────────────────────
            if (supplement.certifications.isNotEmpty()) {
                item {
                    CertificationsSection(certifications = supplement.certifications)
                }
            }

            // ─── Ingredients ──────────────────────────────────────────────────
            item {
                SectionTitle(
                    title = "Ingredienti (${supplement.ingredients.size})",
                    icon = Icons.Default.Science
                )
            }

            items(supplement.ingredients) { ingredient ->
                IngredientRow(
                    supplementIngredient = ingredient,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            // ─── Product Info ──────────────────────────────────────────────────
            item {
                ProductInfoSection(supplement = supplement)
            }
        }
    }
}

@Composable
private fun ScoreHeroSection(
    supplement: Supplement,
    score: ScoringEngine.SupplementScore
) {
    val scoreColor = Color(score.grade.toColor())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(scoreColor.copy(alpha = 0.08f))
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ScoreCircle(
                score = score.overall,
                grade = score.grade,
                size = 120.dp
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = score.label,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = scoreColor
                )
                Text(
                    text = supplement.brand,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = supplement.category.toLabel(),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun ScoreBreakdownSection(breakdown: ScoringEngine.ScoreBreakdown) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Analisi ingredienti",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "${breakdown.analyzedIngredients} su ${breakdown.totalIngredients} ingredienti riconosciuti",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BreakdownItem("Eccellenti", breakdown.excellentCount, Color(0xFF2E7D32))
                BreakdownItem("Buoni", breakdown.goodCount, Color(0xFF66BB6A))
                BreakdownItem("Neutri", breakdown.neutralCount, Color(0xFFFFA726))
                BreakdownItem("Attenzione", breakdown.cautionCount, Color(0xFFFF7043))
                BreakdownItem("Da evitare", breakdown.avoidCount, Color(0xFFD32F2F))
            }
        }
    }
}

@Composable
private fun BreakdownItem(label: String, count: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (count > 0) color else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
private fun SummarySection(summary: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = summary,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun WarningsSection(warnings: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SectionTitle(title = "Avvertenze", icon = Icons.Default.Warning)
        warnings.forEach { warning ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFF6F00), modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(text = warning, fontSize = 12.sp, color = Color(0xFF4E342E))
                }
            }
        }
    }
}

@Composable
private fun CertificationsSection(certifications: List<String>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        SectionTitle(title = "Certificazioni", icon = Icons.Default.Verified)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            certifications.forEach { cert ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFE8F5E9),
                    border = BorderStroke(1.dp, Color(0xFF81C784))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF388E3C), modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(text = cert, fontSize = 12.sp, color = Color(0xFF1B5E20))
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductInfoSection(supplement: Supplement) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Informazioni prodotto", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            InfoRow("Barcode", supplement.barcode)
            InfoRow("Marca", supplement.brand)
            InfoRow("Categoria", supplement.category.toLabel())
            if (supplement.servingSize != null) InfoRow("Porzione", supplement.servingSize)
            if (supplement.servingsPerContainer != null) InfoRow("Porzioni per contenitore", supplement.servingsPerContainer.toString())
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun SectionTitle(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(8.dp))
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}
