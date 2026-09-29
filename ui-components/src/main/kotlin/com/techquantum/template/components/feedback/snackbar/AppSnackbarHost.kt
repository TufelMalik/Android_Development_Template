package com.techquantum.template.components.feedback.snackbar

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AppSnackbarHost(
    modifier: Modifier = Modifier,
    controller: SnackbarController = DefaultSnackbarController.instance,
    hostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    LaunchedEffect(controller, hostState) {
        controller.events.collect { event ->
            val result = hostState.showSnackbar(
                message = event.message,
                actionLabel = event.actionLabel,
                duration = event.duration,
            )
            when (result) {
                SnackbarResult.ActionPerformed -> event.onActionPerformed?.invoke()
                SnackbarResult.Dismissed -> event.onDismissed?.invoke()
            }
        }
    }

    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
    )
}
