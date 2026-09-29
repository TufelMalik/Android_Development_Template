package com.techquantum.template.components.feedback.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techquantum.template.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun AppToastHost(
    modifier: Modifier = Modifier,
    controller: ToastController = DefaultToastController.instance,
) {
    var currentToast by remember { mutableStateOf<ToastEvent?>(null) }

    LaunchedEffect(controller) {
        controller.events.collect { event ->
            currentToast = event
            delay(event.durationMs)
            if (currentToast == event) {
                currentToast = null
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AnimatedVisibility(
            visible = currentToast != null,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
            modifier = Modifier.padding(bottom = 64.dp, start = 24.dp, end = 24.dp),
        ) {
            currentToast?.let { toast ->
                Surface(
                    shape = AppTheme.shapes.full,
                    color = AppTheme.colors.onSurface.copy(alpha = 0.9f),
                    contentColor = AppTheme.colors.surface,
                    shadowElevation = AppTheme.elevation.level3,
                ) {
                    Text(
                        text = toast.message,
                        style = AppTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = AppTheme.spacing.lg, vertical = AppTheme.spacing.sm),
                    )
                }
            }
        }
    }
}
