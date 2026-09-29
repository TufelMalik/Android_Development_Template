package com.techquantum.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput

enum class AnimationPreset {
    FADE_EXPAND,
    SCALE_FADE,
    SLIDE_HORIZONTALLY,
}

@Composable
fun AppAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    preset: AnimationPreset = AnimationPreset.FADE_EXPAND,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val enter: EnterTransition = when (preset) {
        AnimationPreset.FADE_EXPAND -> fadeIn(animationSpec = tween(220, easing = FastOutSlowInEasing)) +
            expandVertically(animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f))
        AnimationPreset.SCALE_FADE -> fadeIn(animationSpec = tween(180)) +
            scaleIn(initialScale = 0.82f, animationSpec = spring(dampingRatio = 0.65f, stiffness = 500f))
        AnimationPreset.SLIDE_HORIZONTALLY -> fadeIn(animationSpec = tween(200)) +
            slideInHorizontally(initialOffsetX = { -it / 2 }, animationSpec = tween(250, easing = FastOutSlowInEasing))
    }

    val exit: ExitTransition = when (preset) {
        AnimationPreset.FADE_EXPAND -> fadeOut(animationSpec = tween(180)) + shrinkVertically(animationSpec = tween(200))
        AnimationPreset.SCALE_FADE -> fadeOut(animationSpec = tween(150)) + scaleOut(targetScale = 0.82f)
        AnimationPreset.SLIDE_HORIZONTALLY -> fadeOut(animationSpec = tween(180)) + slideOutHorizontally(targetOffsetX = { it / 2 })
    }

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enter,
        exit = exit,
        content = content,
    )
}

/**
 * Micro-interaction modifier that provides an intuitive bouncing press effect.
 */
fun Modifier.bounceClick(
    scaleDown: Float = 0.94f,
    onClick: (() -> Unit)? = null,
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDown else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 600f),
        label = "BounceScale",
    )

    this
        .scale(scale)
        .pointerInput(Unit) {
            while (true) {
                awaitPointerEventScope {
                    awaitFirstDown(requireUnconsumed = false)
                    isPressed = true
                    val up = waitForUpOrCancellation()
                    isPressed = false
                    if (up != null && onClick != null) {
                        onClick()
                    }
                }
            }
        }
}

/**
 * Continuous subtle pulsing animation modifier, ideal for badges, notifications, or call-to-actions.
 */
fun Modifier.pulseEffect(
    minScale: Float = 0.96f,
    maxScale: Float = 1.04f,
    durationMs: Int = 1200,
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "PulseTransition")
    val scale by infiniteTransition.animateFloat(
        initialValue = minScale,
        targetValue = maxScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "PulseScale",
    )

    this.scale(scale)
}
