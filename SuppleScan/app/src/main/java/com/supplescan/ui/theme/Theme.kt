package com.supplescan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ─── Brand Colors ─────────────────────────────────────────────────────────────
val SuppleScanGreen = Color(0xFF00BFA5)        // Teal primary
val SuppleScanGreenDark = Color(0xFF00897B)
val SuppleScanGreenLight = Color(0xFF4EECDA)
val SuppleScanBackground = Color(0xFFF5F7FA)
val SuppleScanSurface = Color(0xFFFFFFFF)

// Score colors
val ScoreExcellent = Color(0xFF2E7D32)
val ScoreGood = Color(0xFF66BB6A)
val ScoreNeutral = Color(0xFFFFA726)
val ScoreCaution = Color(0xFFFF7043)
val ScoreAvoid = Color(0xFFD32F2F)

private val LightColorScheme = lightColorScheme(
    primary = SuppleScanGreen,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2DFDB),
    onPrimaryContainer = Color(0xFF00251F),
    secondary = Color(0xFF4DB6AC),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2F1),
    onSecondaryContainer = Color(0xFF00201D),
    tertiary = Color(0xFF7986CB),
    background = SuppleScanBackground,
    onBackground = Color(0xFF1A1A2E),
    surface = SuppleScanSurface,
    onSurface = Color(0xFF1A1A2E),
    surfaceVariant = Color(0xFFECEFF1),
    onSurfaceVariant = Color(0xFF455A64),
    outline = Color(0xFFB0BEC5)
)

private val DarkColorScheme = darkColorScheme(
    primary = SuppleScanGreenLight,
    onPrimary = Color(0xFF00251F),
    primaryContainer = Color(0xFF00897B),
    onPrimaryContainer = Color(0xFFB2DFDB),
    secondary = Color(0xFF80CBC4),
    onSecondary = Color(0xFF00201D),
    background = Color(0xFF0D1117),
    onBackground = Color(0xFFECEFF1),
    surface = Color(0xFF161B22),
    onSurface = Color(0xFFECEFF1),
    surfaceVariant = Color(0xFF1F2937),
    onSurfaceVariant = Color(0xFF90A4AE)
)

@Composable
fun SuppleScanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
