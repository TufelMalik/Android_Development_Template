package com.techquantum.ui.core

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techquantum.ui.theme.ThemeMode

/**
 * UiCore holds the single source of truth for design tokens.
 * Modifying values here re-brands the entire application.
 */
object UiCore {
    // Spacing
    val baseSpacingUnit: Dp = 8.dp

    // Touch Target
    val minTouchTargetSize: Dp = 48.dp

    // Corner Radius Scale
    val radiusNone: Dp = 0.dp
    val radiusSmall: Dp = 4.dp
    val radiusMedium: Dp = 8.dp
    val radiusLarge: Dp = 16.dp
    val radiusFull: Dp = 9999.dp

    // Theme Mode
    val defaultThemeMode: ThemeMode = ThemeMode.SYSTEM

    // Light Theme Seed Colors
    val lightPrimary: Color = Color(0xFF0061A4)
    val lightOnPrimary: Color = Color(0xFFFFFFFF)
    val lightPrimaryContainer: Color = Color(0xFFD1E4FF)
    val lightOnPrimaryContainer: Color = Color(0xFF001D36)

    val lightSecondary: Color = Color(0xFF535F70)
    val lightOnSecondary: Color = Color(0xFFFFFFFF)
    val lightSecondaryContainer: Color = Color(0xFFD7E3F7)
    val lightOnSecondaryContainer: Color = Color(0xFF101C2B)

    val lightBackground: Color = Color(0xFFFDFCFF)
    val lightOnBackground: Color = Color(0xFF1A1C1E)

    val lightSurface: Color = Color(0xFFFDFCFF)
    val lightOnSurface: Color = Color(0xFF1A1C1E)
    val lightSurfaceVariant: Color = Color(0xFFDFE2EB)
    val lightOnSurfaceVariant: Color = Color(0xFF43474E)

    val lightOutline: Color = Color(0xFF73777F)
    val lightOutlineVariant: Color = Color(0xFFC3C7D0)

    val lightError: Color = Color(0xFFBA1A1A)
    val lightOnError: Color = Color(0xFFFFFFFF)
    val lightErrorContainer: Color = Color(0xFFFFDAD6)
    val lightOnErrorContainer: Color = Color(0xFF410002)

    val lightSuccess: Color = Color(0xFF1B6B2F)
    val lightOnSuccess: Color = Color(0xFFFFFFFF)

    val lightWarning: Color = Color(0xFF8A5100)
    val lightOnWarning: Color = Color(0xFFFFFFFF)

    // Dark Theme Seed Colors
    val darkPrimary: Color = Color(0xFF9ECAFF)
    val darkOnPrimary: Color = Color(0xFF003258)
    val darkPrimaryContainer: Color = Color(0xFF00497D)
    val darkOnPrimaryContainer: Color = Color(0xFFD1E4FF)

    val darkSecondary: Color = Color(0xFFBBC7DB)
    val darkOnSecondary: Color = Color(0xFF253140)
    val darkSecondaryContainer: Color = Color(0xFF3B4858)
    val darkOnSecondaryContainer: Color = Color(0xFFD7E3F7)

    val darkBackground: Color = Color(0xFF1A1C1E)
    val darkOnBackground: Color = Color(0xFFE2E2E6)

    val darkSurface: Color = Color(0xFF1A1C1E)
    val darkOnSurface: Color = Color(0xFFE2E2E6)
    val darkSurfaceVariant: Color = Color(0xFF43474E)
    val darkOnSurfaceVariant: Color = Color(0xFFC3C7D0)

    val darkOutline: Color = Color(0xFF8D9199)
    val darkOutlineVariant: Color = Color(0xFF43474E)

    val darkError: Color = Color(0xFFFFB4AB)
    val darkOnError: Color = Color(0xFF690005)
    val darkErrorContainer: Color = Color(0xFF93000A)
    val darkOnErrorContainer: Color = Color(0xFFFFDAD6)

    val darkSuccess: Color = Color(0xFF66DC78)
    val darkOnSuccess: Color = Color(0xFF003913)

    val darkWarning: Color = Color(0xFFFFB77C)
    val darkOnWarning: Color = Color(0xFF4B2800)
}
