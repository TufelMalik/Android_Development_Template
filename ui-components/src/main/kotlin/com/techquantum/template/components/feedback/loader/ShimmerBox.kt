package com.techquantum.template.components.feedback.loader

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.techquantum.template.ui.theme.AppTheme

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = AppTheme.shapes.medium,
) {
    val shimmerColors = listOf(
        AppTheme.colors.surfaceVariant.copy(alpha = 0.6f),
        AppTheme.colors.surfaceVariant.copy(alpha = 0.2f),
        AppTheme.colors.surfaceVariant.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "ShimmerTransition")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "ShimmerAnimation",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 1000f, translateAnim - 1000f),
        end = Offset(translateAnim, translateAnim),
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(brush),
    )
}
