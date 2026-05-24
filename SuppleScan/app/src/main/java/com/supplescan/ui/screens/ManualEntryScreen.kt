package com.supplescan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.supplescan.data.model.SupplementCategory
import com.supplescan.data.model.toLabel

/**
 * Manual entry screen for users to input supplement data by hand.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualEntryScreen(
    onBack: () -> Unit,
    onSubmit: (
        barcode: String,
        name: String,
        brand: String,
        category: SupplementCategory,
        ingredients: List<String>
    ) -> Unit
) {
    var barcode by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(SupplementCategory.OTHER) }
    var ingredientsText by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }

    val isValid = name.isNotBlank() && ingredientsText.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inserimento manuale") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Indietro")
                    }
                }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        val ingredients = ingredientsText
                            .split(",", "\n")
                            .map { it.trim() }
                            .filter { it.isNotBlank() }
                        onSubmit(
                            barcode.ifBlank { System.currentTimeMillis().toString() },
                            name,
                            brand.ifBlank { "Sconosciuto" },
                            selectedCategory,
                            ingredients
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValid,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Analytics, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Analizza integratore")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Inserisci i dati del prodotto",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome prodotto *") },
                placeholder = { Text("es. Vitamina C 1000mg") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Label, null) },
                singleLine = true
            )

            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Marca") },
                placeholder = { Text("es. Solgar") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Business, null) },
                singleLine = true
            )

            OutlinedTextField(
                value = barcode,
                onValueChange = { barcode = it.filter { c -> c.isDigit() } },
                label = { Text("Codice a barre (opzionale)") },
                placeholder = { Text("es. 1234567890123") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.QrCode, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Category dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory.toLabel(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    leadingIcon = { Icon(Icons.Default.Category, null) }
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    SupplementCategory.entries.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.toLabel()) },
                            onClick = {
                                selectedCategory = category
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // Ingredients
            OutlinedTextField(
                value = ingredientsText,
                onValueChange = { ingredientsText = it },
                label = { Text("Ingredienti *") },
                placeholder = { Text("Vitamina C, Magnesio, Zinco\n(separati da virgola o a capo)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                leadingIcon = { Icon(Icons.Default.Science, null) },
                maxLines = 8
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    Icon(
                        Icons.Default.Lightbulb,
                        null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Puoi trovare gli ingredienti sull'etichetta del prodotto. Separali con una virgola.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}
