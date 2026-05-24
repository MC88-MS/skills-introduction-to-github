package com.supplescan.data.model

/**
 * Represents a supplement ingredient with its health rating.
 */
data class Ingredient(
    val name: String,
    val rating: IngredientRating,
    val description: String,
    val details: String,
    val category: IngredientCategory
)

enum class IngredientRating {
    EXCELLENT,   // Score 90-100: proven benefits, safe
    GOOD,        // Score 70-89: beneficial, well-studied
    NEUTRAL,     // Score 50-69: neither harmful nor beneficial
    CAUTION,     // Score 25-49: limited evidence or minor concerns
    AVOID        // Score 0-24: potentially harmful or no evidence
}

enum class IngredientCategory {
    VITAMIN,
    MINERAL,
    AMINO_ACID,
    HERBAL_EXTRACT,
    PROBIOTIC,
    FATTY_ACID,
    ENZYME,
    ANTIOXIDANT,
    SWEETENER,
    ADDITIVE,
    PROTEIN,
    FIBER,
    OTHER
}

fun IngredientRating.toScore(): Int = when (this) {
    IngredientRating.EXCELLENT -> 95
    IngredientRating.GOOD -> 80
    IngredientRating.NEUTRAL -> 60
    IngredientRating.CAUTION -> 35
    IngredientRating.AVOID -> 10
}

fun IngredientRating.toColor(): Long = when (this) {
    IngredientRating.EXCELLENT -> 0xFF2E7D32L  // Dark Green
    IngredientRating.GOOD -> 0xFF66BB6AL       // Light Green
    IngredientRating.NEUTRAL -> 0xFFFFA726L    // Orange
    IngredientRating.CAUTION -> 0xFFFF7043L    // Deep Orange
    IngredientRating.AVOID -> 0xFFD32F2FL      // Red
}

fun IngredientRating.toLabel(): String = when (this) {
    IngredientRating.EXCELLENT -> "Eccellente"
    IngredientRating.GOOD -> "Buono"
    IngredientRating.NEUTRAL -> "Neutro"
    IngredientRating.CAUTION -> "Attenzione"
    IngredientRating.AVOID -> "Da evitare"
}
