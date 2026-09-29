package com.techquantum.ui.theme.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techquantum.ui.core.UiCore

@Immutable
data class Sizing(
    // Touch targets
    val minTouchTarget: Dp = UiCore.minTouchTargetSize,

    // Icon sizes
    val iconXs: Dp = 16.dp,
    val iconSm: Dp = 20.dp,
    val iconMd: Dp = 24.dp,
    val iconLg: Dp = 32.dp,
    val iconXl: Dp = 48.dp,

    // Avatar sizes
    val avatarSm: Dp = 32.dp,
    val avatarMd: Dp = 40.dp,
    val avatarLg: Dp = 56.dp,
    val avatarXl: Dp = 72.dp,

    // Button heights
    val buttonHeightSm: Dp = 36.dp,
    val buttonHeightMd: Dp = 44.dp,
    val buttonHeightLg: Dp = 52.dp,

    // Input heights
    val inputHeight: Dp = 56.dp,
    val searchBarHeight: Dp = 48.dp,
)
