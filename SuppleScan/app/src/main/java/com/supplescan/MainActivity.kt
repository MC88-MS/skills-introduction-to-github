package com.supplescan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import androidx.navigation.compose.*
import com.supplescan.ui.screens.*
import com.supplescan.ui.theme.SuppleScanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuppleScanTheme {
                SuppleScanApp()
            }
        }
    }
}

// ─── Navigation Routes ────────────────────────────────────────────────────────

sealed class Screen(val route: String) {
    object Home      : Screen("home")
    object Scanner   : Screen("scanner")
    object History   : Screen("history")
    object Manual    : Screen("manual")
    object Product   : Screen("product/{barcode}") {
        fun withBarcode(barcode: String) = "product/$barcode"
    }
}

// ─── Bottom Nav Items ─────────────────────────────────────────────────────────

data class NavItem(
    val screen: Screen,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector = icon
)

val bottomNavItems = listOf(
    NavItem(Screen.Home, "Home", Icons.Default.Home),
    NavItem(Screen.Scanner, "Scansiona", Icons.Default.QrCodeScanner),
    NavItem(Screen.History, "Cronologia", Icons.Default.History)
)

// ─── App Root ─────────────────────────────────────────────────────────────────

@Composable
fun SuppleScanApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val history by viewModel.history.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val scanState by viewModel.scanState.collectAsStateWithLifecycle()
    val productState by viewModel.productState.collectAsStateWithLifecycle()

    val showBottomBar = currentRoute in listOf(Screen.Home.route, Screen.History.route)

    // Handle scan result navigation
    LaunchedEffect(scanState) {
        when (val state = scanState) {
            is ScanState.Found -> {
                navController.navigate(Screen.Product.withBarcode(state.barcode)) {
                    popUpTo(Screen.Scanner.route) { inclusive = true }
                }
                viewModel.resetScanState()
            }
            is ScanState.NotFound -> {
                navController.navigate(Screen.Manual.route) {
                    popUpTo(Screen.Scanner.route) { inclusive = true }
                }
                viewModel.resetScanState()
            }
            else -> {}
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.screen.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    navController.navigate(item.screen.route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    if (selected) item.selectedIcon else item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    recentScans = history,
                    onScanClick = { navController.navigate(Screen.Scanner.route) },
                    onItemClick = { barcode ->
                        viewModel.loadProduct(barcode)
                        navController.navigate(Screen.Product.withBarcode(barcode))
                    },
                    onManualEntryClick = { navController.navigate(Screen.Manual.route) }
                )
            }

            composable(Screen.Scanner.route) {
                Box {
                    // Loading overlay during scan processing
                    ScannerScreen(
                        onBarcodeScanned = { barcode ->
                            viewModel.onBarcodeDetected(barcode)
                        }
                    )
                    if (scanState is ScanState.Processing) {
                        ScanProcessingOverlay()
                    }
                }
            }

            composable(Screen.History.route) {
                HistoryScreen(
                    history = history,
                    favorites = favorites,
                    onItemClick = { barcode ->
                        viewModel.loadProduct(barcode)
                        navController.navigate(Screen.Product.withBarcode(barcode))
                    },
                    onDeleteItem = { barcode ->
                        viewModel.deleteFromHistory(barcode)
                    }
                )
            }

            composable(Screen.Manual.route) {
                ManualEntryScreen(
                    onBack = { navController.popBackStack() },
                    onSubmit = { barcode, name, brand, category, ingredients ->
                        viewModel.createManualSupplement(barcode, name, brand, category, ingredients)
                        navController.navigate(Screen.Product.withBarcode(barcode)) {
                            popUpTo(Screen.Manual.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                route = Screen.Product.route,
                arguments = listOf(navArgument("barcode") { type = NavType.StringType })
            ) {
                val state = productState
                when {
                    state.isLoading -> LoadingScreen()
                    state.supplement != null && state.score != null -> {
                        ProductDetailScreen(
                            supplement = state.supplement,
                            score = state.score,
                            isFavorite = state.isFavorite,
                            onBack = { navController.popBackStack() },
                            onToggleFavorite = { viewModel.toggleFavorite() },
                            onShare = {
                                val shareText = """
                                    🔬 ${state.supplement.name} - ${state.supplement.brand}
                                    📊 Punteggio SuppleScan: ${state.score.overall}/100 (${state.score.label})

                                    Analizzato con SuppleScan 💊
                                """.trimIndent()
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                context.startActivity(Intent.createChooser(intent, "Condividi con"))
                            }
                        )
                    }
                    state.error != null -> ErrorScreen(message = state.error, onRetry = { navController.popBackStack() })
                    else -> LoadingScreen()
                }
            }
        }
    }
}

@Composable
fun ScanProcessingOverlay() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Surface(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(Modifier.height(12.dp))
                Text("Analizzando il prodotto...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.ErrorOutline, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(16.dp))
        Text(message, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) { Text("Indietro") }
    }
}
