package com.techquantum.ui.theme.shape

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import com.techquantum.ui.core.UiCore

@Immutable
data class AppShapes(
    val none: CornerBasedShape,
    val small: CornerBasedShape,
    val medium: CornerBasedShape,
    val large: CornerBasedShape,
    val full: CornerBasedShape,
)

fun defaultAppShapes(): AppShapes = AppShapes(
    none = RoundedCornerShape(UiCore.radiusNone),
    small = RoundedCornerShape(UiCore.radiusSmall),
    medium = RoundedCornerShape(UiCore.radiusMedium),
    large = RoundedCornerShape(UiCore.radiusLarge),
    full = RoundedCornerShape(UiCore.radiusFull),
)

fun AppShapes.toMaterialShapes(): Shapes = Shapes(
    extraSmall = none,
    small = small,
    medium = medium,
    large = large,
    extraLarge = full,
)
