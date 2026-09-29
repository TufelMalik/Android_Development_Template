package com.techquantum.ui.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowWidthSizeClass {
    COMPACT,
    MEDIUM,
    EXPANDED,
}

@Immutable
data class WindowSizeClass(
    val widthSizeClass: WindowWidthSizeClass,
) {
    val isCompact: Boolean get() = widthSizeClass == WindowWidthSizeClass.COMPACT
    val isMedium: Boolean get() = widthSizeClass == WindowWidthSizeClass.MEDIUM
    val isExpanded: Boolean get() = widthSizeClass == WindowWidthSizeClass.EXPANDED

    companion object {
        val compactBreakpoint: Dp = 600.dp
        val mediumBreakpoint: Dp = 840.dp

        fun calculateFromWidth(widthDp: Dp): WindowSizeClass {
            val widthClass = when {
                widthDp < compactBreakpoint -> WindowWidthSizeClass.COMPACT
                widthDp < mediumBreakpoint -> WindowWidthSizeClass.MEDIUM
                else -> WindowWidthSizeClass.EXPANDED
            }
            return WindowSizeClass(widthClass)
        }
    }
}

@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    return remember(screenWidthDp) {
        WindowSizeClass.calculateFromWidth(screenWidthDp)
    }
}
