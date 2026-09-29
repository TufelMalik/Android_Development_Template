package com.techquantum.ui.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.techquantum.ui.core.UiCore

fun lightAppColors(): AppColors = AppColors(
    primary = UiCore.lightPrimary,
    onPrimary = UiCore.lightOnPrimary,
    primaryContainer = UiCore.lightPrimaryContainer,
    onPrimaryContainer = UiCore.lightOnPrimaryContainer,
    secondary = UiCore.lightSecondary,
    onSecondary = UiCore.lightOnSecondary,
    secondaryContainer = UiCore.lightSecondaryContainer,
    onSecondaryContainer = UiCore.lightOnSecondaryContainer,
    background = UiCore.lightBackground,
    onBackground = UiCore.lightOnBackground,
    surface = UiCore.lightSurface,
    onSurface = UiCore.lightOnSurface,
    surfaceVariant = UiCore.lightSurfaceVariant,
    onSurfaceVariant = UiCore.lightOnSurfaceVariant,
    outline = UiCore.lightOutline,
    outlineVariant = UiCore.lightOutlineVariant,
    error = UiCore.lightError,
    onError = UiCore.lightOnError,
    errorContainer = UiCore.lightErrorContainer,
    onErrorContainer = UiCore.lightOnErrorContainer,
    success = UiCore.lightSuccess,
    onSuccess = UiCore.lightOnSuccess,
    warning = UiCore.lightWarning,
    onWarning = UiCore.lightOnWarning,
    isDark = false,
)

fun darkAppColors(): AppColors = AppColors(
    primary = UiCore.darkPrimary,
    onPrimary = UiCore.darkOnPrimary,
    primaryContainer = UiCore.darkPrimaryContainer,
    onPrimaryContainer = UiCore.darkOnPrimaryContainer,
    secondary = UiCore.darkSecondary,
    onSecondary = UiCore.darkOnSecondary,
    secondaryContainer = UiCore.darkSecondaryContainer,
    onSecondaryContainer = UiCore.darkOnSecondaryContainer,
    background = UiCore.darkBackground,
    onBackground = UiCore.darkOnBackground,
    surface = UiCore.darkSurface,
    onSurface = UiCore.darkOnSurface,
    surfaceVariant = UiCore.darkSurfaceVariant,
    onSurfaceVariant = UiCore.darkOnSurfaceVariant,
    outline = UiCore.darkOutline,
    outlineVariant = UiCore.darkOutlineVariant,
    error = UiCore.darkError,
    onError = UiCore.darkOnError,
    errorContainer = UiCore.darkErrorContainer,
    onErrorContainer = UiCore.darkOnErrorContainer,
    success = UiCore.darkSuccess,
    onSuccess = UiCore.darkOnSuccess,
    warning = UiCore.darkWarning,
    onWarning = UiCore.darkOnWarning,
    isDark = true,
)

fun AppColors.toMaterialColorScheme(): ColorScheme {
    return if (isDark) {
        darkColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            outlineVariant = outlineVariant,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
        )
    } else {
        lightColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            outlineVariant = outlineVariant,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
        )
    }
}
