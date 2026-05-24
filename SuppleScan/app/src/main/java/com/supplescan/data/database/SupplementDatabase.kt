package com.supplescan.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// ─── Entities ────────────────────────────────────────────────────────────────

@Entity(tableName = "scanned_supplements")
data class ScannedSupplementEntity(
    @PrimaryKey val barcode: String,
    val name: String,
    val brand: String,
    val category: String,
    val ingredientsJson: String,  // JSON-serialized list
    val score: Int,
    val scoreLabel: String,
    val imageUrl: String?,
    val scannedAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

@Entity(tableName = "ingredient_database")
data class IngredientEntity(
    @PrimaryKey val normalizedName: String,
    val displayName: String,
    val rating: String,
    val category: String,
    val description: String,
    val details: String
)

// ─── DAOs ─────────────────────────────────────────────────────────────────────

@Dao
interface ScannedSupplementDao {

    @Query("SELECT * FROM scanned_supplements ORDER BY scannedAt DESC")
    fun getAllScanned(): Flow<List<ScannedSupplementEntity>>

    @Query("SELECT * FROM scanned_supplements WHERE isFavorite = 1 ORDER BY scannedAt DESC")
    fun getFavorites(): Flow<List<ScannedSupplementEntity>>

    @Query("SELECT * FROM scanned_supplements WHERE barcode = :barcode LIMIT 1")
    suspend fun getByBarcode(barcode: String): ScannedSupplementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(supplement: ScannedSupplementEntity)

    @Query("UPDATE scanned_supplements SET isFavorite = :isFavorite WHERE barcode = :barcode")
    suspend fun updateFavorite(barcode: String, isFavorite: Boolean)

    @Query("DELETE FROM scanned_supplements WHERE barcode = :barcode")
    suspend fun deleteByBarcode(barcode: String)

    @Query("SELECT COUNT(*) FROM scanned_supplements")
    suspend fun count(): Int
}

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredient_database WHERE normalizedName = :name LIMIT 1")
    suspend fun findByName(name: String): IngredientEntity?

    @Query("SELECT * FROM ingredient_database WHERE normalizedName LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<IngredientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredients: List<IngredientEntity>)

    @Query("SELECT COUNT(*) FROM ingredient_database")
    suspend fun count(): Int
}

// ─── Database ─────────────────────────────────────────────────────────────────

@Database(
    entities = [ScannedSupplementEntity::class, IngredientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SupplementDatabase : RoomDatabase() {
    abstract fun scannedSupplementDao(): ScannedSupplementDao
    abstract fun ingredientDao(): IngredientDao

    companion object {
        const val DATABASE_NAME = "supplescan.db"
    }
}
