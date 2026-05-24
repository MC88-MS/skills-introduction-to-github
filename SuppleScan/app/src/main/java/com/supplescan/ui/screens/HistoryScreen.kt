package com.supplescan.ui.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supplescan.data.repository.ScannedSupplementSummary
import com.supplescan.domain.scoring.ScoringEngine
import com.supplescan.ui.components.ScoreBadge
import java.text.SimpleDateFormat
import java.util.*

/**
 * Cronologia scansioni con possibilità di visualizzare solo i preferiti.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    history: List<ScannedSupplementSummary>,
    favorites: List<ScannedSupplementSummary>,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit
) {
    var showFavoritesOnly by remember { mutableStateOf(false) }
    val displayList = if (showFavoritesOnly) favorites else history

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cronologia") },
                actions = {
                    IconButton(onClick = { showFavoritesOnly = !showFavoritesOnly }) {
                        Icon(
                            imageVector = if (showFavoritesOnly) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Preferiti",
                            tint = if (showFavoritesOnly) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (displayList.isEmpty()) {
            EmptyHistoryPlaceholder(
                isFavoritesTab = showFavoritesOnly,
                modifier = Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (showFavoritesOnly) {
                    item {
                        Text(
                            text = "${favorites.size} preferit${if (favorites.size == 1) "o" else "i"}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                } else {
                    item {
                        Text(
                            text = "${history.size} prodott${if (history.size == 1) "o" else "i"} scansionat${if (history.size == 1) "o" else "i"}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                items(displayList, key = { it.barcode }) { item ->
                    HistoryItemCard(
                        item = item,
                        onClick = { onItemClick(item.barcode) },
                        onDelete = { onDeleteItem(item.barcode) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryItemCard(
    item: ScannedSupplementSummary,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val grade = scoreToGrade(item.score)
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.ITALIAN) }
    val date = dateFormat.format(Date(item.scannedAt))
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Rimuovere dalla cronologia?") },
            text = { Text("Vuoi rimuovere \"${item.name}\" dalla cronologia?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) { Text("Rimuovi", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Annulla") }
            }
        )
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreBadge(score = item.score, grade = grade)

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    maxLines = 1
                )
                Text(
                    text = item.brand,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.scoreLabel,
                        fontSize = 11.sp,
                        color = Color(grade.toColor()),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = " · $date",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }

            if (item.isFavorite) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red.copy(alpha = 0.7f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
            }

            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.DeleteOutline,
                    contentDescription = "Elimina",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun EmptyHistoryPlaceholder(isFavoritesTab: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (isFavoritesTab) Icons.Default.FavoriteBorder else Icons.Default.QrCodeScanner,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = if (isFavoritesTab) "Nessun preferito" else "Nessuna scansione ancora",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = if (isFavoritesTab)
                "Aggiungi prodotti ai preferiti dalla schermata di dettaglio."
            else
                "Scansiona il barcode di un integratore per iniziare.",
            fontSize = 14.sp,
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

private fun ScoringEngine.ScoreGrade.toColor(): Long = when (this) {
    ScoringEngine.ScoreGrade.A_PLUS -> 0xFF1B5E20L
    ScoringEngine.ScoreGrade.A      -> 0xFF2E7D32L
    ScoringEngine.ScoreGrade.B      -> 0xFF558B2FL
    ScoringEngine.ScoreGrade.C      -> 0xFFF57F17L
    ScoringEngine.ScoreGrade.D      -> 0xFFE65100L
    ScoringEngine.ScoreGrade.F      -> 0xFFB71C1CL
}
