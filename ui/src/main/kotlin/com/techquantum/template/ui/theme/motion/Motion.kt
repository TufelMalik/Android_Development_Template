package com.techquantum.template.ui.theme.motion

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing

object Motion {
    // Durations in milliseconds
    const val durationShort1: Int = 50
    const val durationShort2: Int = 100
    const val durationMedium1: Int = 200
    const val durationMedium2: Int = 300
    const val durationLong1: Int = 400
    const val durationLong2: Int = 500

    // Standard Easings
    val emphasized: Easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
    val standard: Easing = FastOutSlowInEasing
    val standardDecelerate: Easing = LinearOutSlowInEasing
    val standardAccelerate: Easing = FastOutLinearInEasing
}
