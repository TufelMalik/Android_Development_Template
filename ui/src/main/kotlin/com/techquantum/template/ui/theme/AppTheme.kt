package com.techquantum.template.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.techquantum.template.ui.core.UiCore
import com.techquantum.template.ui.theme.color.AppColors
import com.techquantum.template.ui.theme.color.darkAppColors
import com.techquantum.template.ui.theme.color.lightAppColors
import com.techquantum.template.ui.theme.color.toMaterialColorScheme
import com.techquantum.template.ui.theme.dimension.Elevation
import com.techquantum.template.ui.theme.dimension.Sizing
import com.techquantum.template.ui.theme.dimension.Spacing
import com.techquantum.template.ui.theme.shape.AppShapes
import com.techquantum.template.ui.theme.shape.defaultAppShapes
import com.techquantum.template.ui.theme.shape.toMaterialShapes
import com.techquantum.template.ui.theme.typography.AppTypography
import com.techquantum.template.ui.theme.typography.defaultAppTypography
import com.techquantum.template.ui.theme.typography.toMaterialTypography
import com.techquantum.template.ui.window.WindowSizeClass
import com.techquantum.template.ui.window.WindowWidthSizeClass
import com.techquantum.template.ui.window.rememberWindowSizeClass

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided")
}

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("No AppTypography provided")
}

val LocalAppShapes = staticCompositionLocalOf<AppShapes> {
    error("No AppShapes provided")
}

val LocalAppSpacing = staticCompositionLocalOf<Spacing> {
    Spacing()
}

val LocalAppSizing = staticCompositionLocalOf<Sizing> {
    Sizing()
}

val LocalAppElevation = staticCompositionLocalOf<Elevation> {
    Elevation()
}

val LocalWindowSizeClass = staticCompositionLocalOf<WindowSizeClass> {
    WindowSizeClass(WindowWidthSizeClass.COMPACT)
}

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

    val spacing: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSpacing.current

    val sizing: Sizing
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSizing.current

    val elevation: Elevation
        @Composable
        @ReadOnlyComposable
        get() = LocalAppElevation.current

    val windowSizeClass: WindowSizeClass
        @Composable
        @ReadOnlyComposable
        get() = LocalWindowSizeClass.current
}

@Composable
fun AppTheme(
    themeMode: ThemeMode = UiCore.defaultThemeMode,
    content: @Composable () -> Unit,
) {
    val systemInDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> systemInDark
    }

    val appColors = if (isDark) darkAppColors() else lightAppColors()
    val appTypography = defaultAppTypography()
    val appShapes = defaultAppShapes()
    val spacing = Spacing()
    val sizing = Sizing()
    val elevation = Elevation()
    val windowSizeClass = rememberWindowSizeClass()

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppTypography provides appTypography,
        LocalAppShapes provides appShapes,
        LocalAppSpacing provides spacing,
        LocalAppSizing provides sizing,
        LocalAppElevation provides elevation,
        LocalWindowSizeClass provides windowSizeClass,
    ) {
        MaterialTheme(
            colorScheme = appColors.toMaterialColorScheme(),
            typography = appTypography.toMaterialTypography(),
            shapes = appShapes.toMaterialShapes(),
            content = content,
        )
    }
}
