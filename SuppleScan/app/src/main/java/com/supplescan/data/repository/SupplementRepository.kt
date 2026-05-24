package com.supplescan.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.supplescan.data.database.*
import com.supplescan.data.model.*
import com.supplescan.domain.scoring.ScoringEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

data class ScannedSupplementSummary(
    val barcode: String,
    val name: String,
    val brand: String,
    val category: String,
    val score: Int,
    val scoreLabel: String,
    val imageUrl: String?,
    val scannedAt: Long,
    val isFavorite: Boolean
)

@Singleton
class SupplementRepository @Inject constructor(
    private val scannedDao: ScannedSupplementDao,
    private val ingredientDao: IngredientDao
) {
    private val gson = Gson()

    // ─── History ─────────────────────────────────────────────────────────────

    fun getHistory(): Flow<List<ScannedSupplementSummary>> =
        scannedDao.getAllScanned().map { list ->
            list.map { it.toSummary() }
        }

    fun getFavorites(): Flow<List<ScannedSupplementSummary>> =
        scannedDao.getFavorites().map { list ->
            list.map { it.toSummary() }
        }

    suspend fun getCachedSupplement(barcode: String): Supplement? {
        val entity = scannedDao.getByBarcode(barcode) ?: return null
        return entity.toSupplement()
    }

    suspend fun saveSupplement(supplement: Supplement) {
        val score = ScoringEngine.calculate(supplement)
        val ingredientsJson = gson.toJson(
            supplement.ingredients.map { si ->
                mapOf(
                    "name" to si.name,
                    "amount" to (si.amount ?: ""),
                    "unit" to (si.unit ?: "")
                )
            }
        )
        scannedDao.insert(
            ScannedSupplementEntity(
                barcode = supplement.barcode,
                name = supplement.name,
                brand = supplement.brand,
                category = supplement.category.name,
                ingredientsJson = ingredientsJson,
                score = score.overall,
                scoreLabel = score.label,
                imageUrl = supplement.imageUrl
            )
        )
    }

    suspend fun toggleFavorite(barcode: String, isFavorite: Boolean) {
        scannedDao.updateFavorite(barcode, isFavorite)
    }

    suspend fun deleteSupplement(barcode: String) {
        scannedDao.deleteByBarcode(barcode)
    }

    // ─── Ingredient lookup ────────────────────────────────────────────────────

    suspend fun findIngredient(name: String): Ingredient? {
        val normalized = name.lowercase().trim()
        val entity = ingredientDao.findByName(normalized) ?: return null
        return entity.toIngredient()
    }

    suspend fun searchIngredients(query: String): List<Ingredient> {
        return ingredientDao.search(query.lowercase()).map { it.toIngredient() }
    }

    suspend fun analyzeIngredients(ingredientNames: List<String>): List<SupplementIngredient> {
        return ingredientNames.map { name ->
            val ingredient = findIngredient(name)
            SupplementIngredient(
                name = name,
                ingredient = ingredient
            )
        }
    }

    // ─── Seed ─────────────────────────────────────────────────────────────────

    suspend fun seedIngredientDatabaseIfNeeded() {
        if (ingredientDao.count() == 0) {
            ingredientDao.insertAll(IngredientSeedData.ingredients)
        }
    }

    // ─── Mappers ──────────────────────────────────────────────────────────────

    private fun ScannedSupplementEntity.toSummary() = ScannedSupplementSummary(
        barcode = barcode,
        name = name,
        brand = brand,
        category = category,
        score = score,
        scoreLabel = scoreLabel,
        imageUrl = imageUrl,
        scannedAt = scannedAt,
        isFavorite = isFavorite
    )

    private fun ScannedSupplementEntity.toSupplement(): Supplement {
        val type = object : TypeToken<List<Map<String, String>>>() {}.type
        val rawIngredients: List<Map<String, String>> = gson.fromJson(ingredientsJson, type) ?: emptyList()
        return Supplement(
            barcode = barcode,
            name = name,
            brand = brand,
            category = runCatching { SupplementCategory.valueOf(category) }.getOrDefault(SupplementCategory.OTHER),
            imageUrl = imageUrl,
            ingredients = rawIngredients.map { m ->
                SupplementIngredient(
                    name = m["name"] ?: "",
                    amount = m["amount"]?.ifEmpty { null },
                    unit = m["unit"]?.ifEmpty { null }
                )
            }
        )
    }

    private fun IngredientEntity.toIngredient() = Ingredient(
        name = displayName,
        rating = runCatching { IngredientRating.valueOf(rating) }.getOrDefault(IngredientRating.NEUTRAL),
        description = description,
        details = details,
        category = runCatching { IngredientCategory.valueOf(category) }.getOrDefault(IngredientCategory.OTHER)
    )
}
