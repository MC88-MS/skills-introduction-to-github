package com.supplescan.data.model

/**
 * Represents a scanned supplement product.
 */
data class Supplement(
    val barcode: String,
    val name: String,
    val brand: String,
    val category: SupplementCategory,
    val imageUrl: String? = null,
    val ingredients: List<SupplementIngredient>,
    val servingSize: String? = null,
    val servingsPerContainer: Int? = null,
    val warnings: List<String> = emptyList(),
    val certifications: List<String> = emptyList()
)

data class SupplementIngredient(
    val name: String,
    val amount: String? = null,
    val unit: String? = null,
    val ingredient: Ingredient? = null
)

enum class SupplementCategory {
    MULTIVITAMIN,
    PROTEIN_SUPPLEMENT,
    PRE_WORKOUT,
    FAT_BURNER,
    JOINT_SUPPORT,
    DIGESTIVE_HEALTH,
    IMMUNITY,
    ENERGY,
    SLEEP_RECOVERY,
    SPORTS_PERFORMANCE,
    OMEGA_FATTY_ACIDS,
    HERBAL_SUPPLEMENT,
    WEIGHT_MANAGEMENT,
    COGNITIVE_SUPPORT,
    OTHER
}

fun SupplementCategory.toLabel(): String = when (this) {
    SupplementCategory.MULTIVITAMIN -> "Multivitaminico"
    SupplementCategory.PROTEIN_SUPPLEMENT -> "Proteine"
    SupplementCategory.PRE_WORKOUT -> "Pre-workout"
    SupplementCategory.FAT_BURNER -> "Bruciagrassi"
    SupplementCategory.JOINT_SUPPORT -> "Articolazioni"
    SupplementCategory.DIGESTIVE_HEALTH -> "Digestione"
    SupplementCategory.IMMUNITY -> "Immunità"
    SupplementCategory.ENERGY -> "Energia"
    SupplementCategory.SLEEP_RECOVERY -> "Sonno & Recupero"
    SupplementCategory.SPORTS_PERFORMANCE -> "Performance Sportiva"
    SupplementCategory.OMEGA_FATTY_ACIDS -> "Acidi Grassi Omega"
    SupplementCategory.HERBAL_SUPPLEMENT -> "Integratore Erboristico"
    SupplementCategory.WEIGHT_MANAGEMENT -> "Gestione del Peso"
    SupplementCategory.COGNITIVE_SUPPORT -> "Supporto Cognitivo"
    SupplementCategory.OTHER -> "Altro"
}
