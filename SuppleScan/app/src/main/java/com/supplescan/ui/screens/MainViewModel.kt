package com.supplescan.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supplescan.data.model.*
import com.supplescan.data.repository.ScannedSupplementSummary
import com.supplescan.data.repository.SupplementRepository
import com.supplescan.domain.scoring.ScoringEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductUiState(
    val supplement: Supplement? = null,
    val score: ScoringEngine.SupplementScore? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ScanState {
    object Idle : ScanState()
    object Scanning : ScanState()
    data class Processing(val barcode: String) : ScanState()
    data class Found(val barcode: String) : ScanState()
    data class NotFound(val barcode: String) : ScanState()
    data class Error(val message: String) : ScanState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SupplementRepository
) : ViewModel() {

    val history: StateFlow<List<ScannedSupplementSummary>> =
        repository.getHistory()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<List<ScannedSupplementSummary>> =
        repository.getFavorites()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _scanState = MutableStateFlow<ScanState>(ScanState.Idle)
    val scanState: StateFlow<ScanState> = _scanState.asStateFlow()

    private val _productState = MutableStateFlow(ProductUiState())
    val productState: StateFlow<ProductUiState> = _productState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedIngredientDatabaseIfNeeded()
        }
    }

    // ─── Scanning ─────────────────────────────────────────────────────────────

    fun onBarcodeDetected(barcode: String) {
        if (_scanState.value is ScanState.Processing) return
        viewModelScope.launch {
            _scanState.value = ScanState.Processing(barcode)
            val cached = repository.getCachedSupplement(barcode)
            if (cached != null) {
                loadSupplement(cached)
                _scanState.value = ScanState.Found(barcode)
            } else {
                // Fetch from Open Food Facts or similar API
                val supplement = fetchSupplementFromNetwork(barcode)
                if (supplement != null) {
                    repository.saveSupplement(supplement)
                    loadSupplement(supplement)
                    _scanState.value = ScanState.Found(barcode)
                } else {
                    _scanState.value = ScanState.NotFound(barcode)
                }
            }
        }
    }

    fun resetScanState() {
        _scanState.value = ScanState.Idle
    }

    // ─── Product ──────────────────────────────────────────────────────────────

    fun loadProduct(barcode: String) {
        viewModelScope.launch {
            _productState.value = ProductUiState(isLoading = true)
            val supplement = repository.getCachedSupplement(barcode)
            if (supplement != null) {
                loadSupplement(supplement)
            } else {
                _productState.value = ProductUiState(error = "Prodotto non trovato")
            }
        }
    }

    private fun loadSupplement(supplement: Supplement) {
        viewModelScope.launch {
            // Enrich ingredients with database lookup
            val enriched = supplement.ingredients.map { si ->
                si.copy(ingredient = repository.findIngredient(si.name))
            }
            val enrichedSupplement = supplement.copy(ingredients = enriched)
            val score = ScoringEngine.calculate(enrichedSupplement)
            val cached = repository.getCachedSupplement(supplement.barcode)

            _productState.value = ProductUiState(
                supplement = enrichedSupplement,
                score = score,
                isFavorite = cached?.isFavorite ?: false
            )
        }
    }

    fun toggleFavorite() {
        val barcode = _productState.value.supplement?.barcode ?: return
        val current = _productState.value.isFavorite
        viewModelScope.launch {
            repository.toggleFavorite(barcode, !current)
            _productState.value = _productState.value.copy(isFavorite = !current)
        }
    }

    // ─── Manual Entry ─────────────────────────────────────────────────────────

    fun createManualSupplement(
        barcode: String,
        name: String,
        brand: String,
        category: SupplementCategory,
        ingredientNames: List<String>
    ) {
        viewModelScope.launch {
            val ingredients = repository.analyzeIngredients(ingredientNames)
            val supplement = Supplement(
                barcode = barcode,
                name = name,
                brand = brand,
                category = category,
                ingredients = ingredients
            )
            repository.saveSupplement(supplement)
            loadSupplement(supplement)
        }
    }

    fun deleteFromHistory(barcode: String) {
        viewModelScope.launch {
            repository.deleteSupplement(barcode)
        }
    }

    // ─── Network (Open Food Facts / OpenSupplements) ───────────────────────────

    private suspend fun fetchSupplementFromNetwork(barcode: String): Supplement? {
        // In a real app this would call Open Food Facts API:
        // GET https://world.openfoodfacts.org/api/v0/product/{barcode}.json
        // For now, return a mock for well-known barcodes
        return getMockSupplement(barcode)
    }

    private suspend fun getMockSupplement(barcode: String): Supplement? {
        val commonBarcodes = mapOf(
            "3661112101014" to Supplement(
                barcode = "3661112101014",
                name = "Omega 3 Premium",
                brand = "Nutri+ Pro",
                category = SupplementCategory.OMEGA_FATTY_ACIDS,
                ingredients = repository.analyzeIngredients(
                    listOf("olio di pesce", "vitamina e", "gelatina", "glicerolo")
                ),
                servingSize = "2 capsule",
                servingsPerContainer = 60,
                certifications = listOf("IFOS Certified", "GMP")
            ),
            "8000015800038" to Supplement(
                barcode = "8000015800038",
                name = "Magnesio Supremo",
                brand = "Natural Point",
                category = SupplementCategory.OTHER,
                ingredients = repository.analyzeIngredients(
                    listOf("magnesio", "acido citrico", "fruttosio", "aroma naturale")
                ),
                servingSize = "5g",
                servingsPerContainer = 32
            ),
            "3175681234567" to Supplement(
                barcode = "3175681234567",
                name = "Pre-Workout Extreme",
                brand = "SportMax",
                category = SupplementCategory.PRE_WORKOUT,
                ingredients = repository.analyzeIngredients(
                    listOf("caffeina", "beta-alanina", "l-citrullina", "creatina", "sucralosio", "biossido di titanio", "yohimbina")
                ),
                warnings = listOf("Contiene caffeina (200mg per dose). Non adatto a bambini e donne in gravidanza.")
            )
        )
        return commonBarcodes[barcode]
    }
}
