package com.supplescan.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supplescan.data.model.*

/**
 * Expandable ingredient row showing rating, name, and details.
 */
@Composable
fun IngredientRow(
    supplementIngredient: SupplementIngredient,
    modifier: Modifier = Modifier
) {
    val ingredient = supplementIngredient.ingredient
    var expanded by remember { mutableStateOf(false) }

    val (ratingColor, ratingIcon) = if (ingredient != null) {
        Color(ingredient.rating.toColor()) to ratingIcon(ingredient.rating)
    } else {
        Color(0xFF90A4AE) to Icons.Default.HelpOutline
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { if (ingredient != null) expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            // Main row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rating indicator dot
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(ratingColor)
                )
                Spacer(Modifier.width(12.dp))

                // Ingredient name + amount
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ingredient?.name ?: supplementIngredient.name,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (supplementIngredient.amount != null) {
                        Text(
                            text = "${supplementIngredient.amount} ${supplementIngredient.unit ?: ""}".trim(),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (ingredient == null) {
                        Text(
                            text = "Ingrediente non in database",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }

                // Rating label chip
                if (ingredient != null) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = ratingColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = ingredient.rating.toLabel(),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ratingColor
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Expanded details
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                if (ingredient != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ratingColor.copy(alpha = 0.05f))
                            .padding(12.dp)
                    ) {
                        // Short description
                        Text(
                            text = ingredient.description,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(6.dp))
                        // Detailed info
                        Text(
                            text = ingredient.details,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        // Category chip
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = ingredient.category.toLabel(),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun ratingIcon(rating: IngredientRating) = when (rating) {
    IngredientRating.EXCELLENT -> Icons.Default.CheckCircle
    IngredientRating.GOOD      -> Icons.Default.ThumbUp
    IngredientRating.NEUTRAL   -> Icons.Default.RemoveCircle
    IngredientRating.CAUTION   -> Icons.Default.Warning
    IngredientRating.AVOID     -> Icons.Default.Cancel
}

fun IngredientCategory.toLabel(): String = when (this) {
    IngredientCategory.VITAMIN        -> "Vitamina"
    IngredientCategory.MINERAL        -> "Minerale"
    IngredientCategory.AMINO_ACID     -> "Aminoacido"
    IngredientCategory.HERBAL_EXTRACT -> "Estratto Erboristico"
    IngredientCategory.PROBIOTIC      -> "Probiotico"
    IngredientCategory.FATTY_ACID     -> "Acido Grasso"
    IngredientCategory.ENZYME         -> "Enzima"
    IngredientCategory.ANTIOXIDANT    -> "Antiossidante"
    IngredientCategory.SWEETENER      -> "Dolcificante"
    IngredientCategory.ADDITIVE       -> "Additivo"
    IngredientCategory.PROTEIN        -> "Proteina"
    IngredientCategory.FIBER          -> "Fibra"
    IngredientCategory.OTHER          -> "Altro"
}
