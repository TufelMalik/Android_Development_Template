package com.techquantum.ui.theme.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.techquantum.ui.core.UiCore

@Immutable
data class Spacing(
    val xxs: Dp = UiCore.baseSpacingUnit * 0.25f,
    val xs: Dp = UiCore.baseSpacingUnit * 0.5f,
    val sm: Dp = UiCore.baseSpacingUnit * 1.0f,
    val md: Dp = UiCore.baseSpacingUnit * 2.0f,
    val lg: Dp = UiCore.baseSpacingUnit * 3.0f,
    val xl: Dp = UiCore.baseSpacingUnit * 4.0f,
    val xxl: Dp = UiCore.baseSpacingUnit * 6.0f,
)
