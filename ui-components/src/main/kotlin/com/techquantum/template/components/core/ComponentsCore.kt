package com.techquantum.template.components.core

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techquantum.template.ui.window.WindowSizeClass
import com.techquantum.template.ui.window.WindowWidthSizeClass

/**
 * ComponentsCore is the single place to retune the grid responsiveness,
 * component spacing, and touch target constraints globally.
 */
object ComponentsCore {
    // Grid column counts for each window width class
    const val compactColumnCount: Int = 1
    const val mediumColumnCount: Int = 2
    const val expandedColumnCount: Int = 3

    // Default spacing between grid items
    val gridItemSpacing: Dp = 16.dp

    // Minimum accessible touch target
    val minTouchTargetSize: Dp = 48.dp

    /**
     * Resolves the responsive column count from a given [WindowSizeClass].
     */
    fun resolveColumnCount(windowSizeClass: WindowSizeClass): Int = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.COMPACT -> compactColumnCount
        WindowWidthSizeClass.MEDIUM -> mediumColumnCount
        WindowWidthSizeClass.EXPANDED -> expandedColumnCount
    }
}
