package com.supplescan.domain.scoring

import com.supplescan.data.model.*
import kotlin.math.roundToInt

/**
 * Calculates the overall score and quality label for a supplement
 * based on its ingredients and their individual ratings.
 */
object ScoringEngine {

    data class SupplementScore(
        val overall: Int,              // 0-100
        val grade: ScoreGrade,
        val label: String,
        val summary: String,
        val breakdown: ScoreBreakdown
    )

    data class ScoreBreakdown(
        val excellentCount: Int,
        val goodCount: Int,
        val neutralCount: Int,
        val cautionCount: Int,
        val avoidCount: Int,
        val unknownCount: Int,
        val analyzedIngredients: Int,
        val totalIngredients: Int
    )

    enum class ScoreGrade {
        A_PLUS,   // 90-100
        A,        // 80-89
        B,        // 65-79
        C,        // 50-64
        D,        // 30-49
        F         // 0-29
    }

    fun calculate(supplement: Supplement): SupplementScore {
        val ingredients = supplement.ingredients
        val totalCount = ingredients.size
        val analyzedIngredients = ingredients.filter { it.ingredient != null }

        // Count by rating
        val excellentCount = analyzedIngredients.count { it.ingredient?.rating == IngredientRating.EXCELLENT }
        val goodCount = analyzedIngredients.count { it.ingredient?.rating == IngredientRating.GOOD }
        val neutralCount = analyzedIngredients.count { it.ingredient?.rating == IngredientRating.NEUTRAL }
        val cautionCount = analyzedIngredients.count { it.ingredient?.rating == IngredientRating.CAUTION }
        val avoidCount = analyzedIngredients.count { it.ingredient?.rating == IngredientRating.AVOID }
        val unknownCount = totalCount - analyzedIngredients.size

        val breakdown = ScoreBreakdown(
            excellentCount = excellentCount,
            goodCount = goodCount,
            neutralCount = neutralCount,
            cautionCount = cautionCount,
            avoidCount = avoidCount,
            unknownCount = unknownCount,
            analyzedIngredients = analyzedIngredients.size,
            totalIngredients = totalCount
        )

        // Score calculation
        val overall = if (analyzedIngredients.isEmpty()) {
            50 // Unknown → neutral score
        } else {
            val rawScore = analyzedIngredients.sumOf { it.ingredient!!.rating.toScore() }
            val avgScore = rawScore.toDouble() / analyzedIngredients.size

            // Apply penalty for AVOID ingredients
            val avoidPenalty = avoidCount * 15
            // Apply penalty for CAUTION ingredients
            val cautionPenalty = cautionCount * 5
            // Bonus for high % of excellent
            val excellentRatio = excellentCount.toDouble() / analyzedIngredients.size
            val excellentBonus = (excellentRatio * 5).roundToInt()

            (avgScore - avoidPenalty - cautionPenalty + excellentBonus)
                .coerceIn(0.0, 100.0)
                .roundToInt()
        }

        val grade = when (overall) {
            in 90..100 -> ScoreGrade.A_PLUS
            in 80..89  -> ScoreGrade.A
            in 65..79  -> ScoreGrade.B
            in 50..64  -> ScoreGrade.C
            in 30..49  -> ScoreGrade.D
            else       -> ScoreGrade.F
        }

        val label = when (grade) {
            ScoreGrade.A_PLUS -> "Eccellente"
            ScoreGrade.A      -> "Buono"
            ScoreGrade.B      -> "Discreto"
            ScoreGrade.C      -> "Mediocre"
            ScoreGrade.D      -> "Scadente"
            ScoreGrade.F      -> "Da evitare"
        }

        val summary = buildSummary(overall, grade, breakdown, supplement.warnings)

        return SupplementScore(
            overall = overall,
            grade = grade,
            label = label,
            summary = summary,
            breakdown = breakdown
        )
    }

    private fun buildSummary(
        score: Int,
        grade: ScoreGrade,
        breakdown: ScoreBreakdown,
        warnings: List<String>
    ): String {
        return buildString {
            when (grade) {
                ScoreGrade.A_PLUS -> append("Questo integratore è formulato con ingredienti di alta qualità e ben supportati dalla ricerca scientifica.")
                ScoreGrade.A      -> append("Questo integratore contiene principalmente ingredienti di buona qualità con solide prove scientifiche.")
                ScoreGrade.B      -> append("Questo integratore ha una formulazione discreta. Alcuni ingredienti potrebbero essere migliorati.")
                ScoreGrade.C      -> append("La formulazione di questo integratore è mediocre. Considera alternative con ingredienti migliori.")
                ScoreGrade.D      -> append("Questo integratore ha una formulazione scadente con ingredienti di dubbia efficacia o sicurezza.")
                ScoreGrade.F      -> append("Questo integratore contiene ingredienti potenzialmente pericolosi. Non raccomandato.")
            }
            if (breakdown.avoidCount > 0) {
                append(" ⚠️ Contiene ${breakdown.avoidCount} ingredient${if (breakdown.avoidCount > 1) "i" else "e"} da evitare.")
            }
            if (breakdown.cautionCount > 0) {
                append(" Prestare attenzione a ${breakdown.cautionCount} ingredient${if (breakdown.cautionCount > 1) "i" else "e"}.")
            }
            if (warnings.isNotEmpty()) {
                append(" ⚠️ Avvertenze: ${warnings.first()}.")
            }
        }
    }
}

fun ScoringEngine.ScoreGrade.toColor(): Long = when (this) {
    ScoringEngine.ScoreGrade.A_PLUS -> 0xFF1B5E20L  // Deep Green
    ScoringEngine.ScoreGrade.A      -> 0xFF2E7D32L  // Dark Green
    ScoringEngine.ScoreGrade.B      -> 0xFF558B2FL  // Light Green
    ScoringEngine.ScoreGrade.C      -> 0xFFF57F17L  // Amber
    ScoringEngine.ScoreGrade.D      -> 0xFFE65100L  // Deep Orange
    ScoringEngine.ScoreGrade.F      -> 0xFFB71C1CL  // Dark Red
}

fun ScoringEngine.ScoreGrade.toDisplayLetter(): String = when (this) {
    ScoringEngine.ScoreGrade.A_PLUS -> "A+"
    ScoringEngine.ScoreGrade.A      -> "A"
    ScoringEngine.ScoreGrade.B      -> "B"
    ScoringEngine.ScoreGrade.C      -> "C"
    ScoringEngine.ScoreGrade.D      -> "D"
    ScoringEngine.ScoreGrade.F      -> "F"
}
