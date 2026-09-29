package com.techquantum.template.components.feedback.snackbar

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface SnackbarController {
    val events: Flow<SnackbarEvent>

    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onActionPerformed: (() -> Unit)? = null,
        onDismissed: (() -> Unit)? = null,
    )

    suspend fun sendEvent(event: SnackbarEvent)
}

class DefaultSnackbarController : SnackbarController {
    private val channel = Channel<SnackbarEvent>(Channel.BUFFERED)
    override val events: Flow<SnackbarEvent> = channel.receiveAsFlow()

    override suspend fun showSnackbar(
        message: String,
        actionLabel: String?,
        duration: SnackbarDuration,
        onActionPerformed: (() -> Unit)?,
        onDismissed: (() -> Unit)?,
    ) {
        val event = SnackbarEvent(
            message = message,
            actionLabel = actionLabel,
            duration = duration,
            onActionPerformed = onActionPerformed,
            onDismissed = onDismissed,
        )
        sendEvent(event)
    }

    override suspend fun sendEvent(event: SnackbarEvent) {
        channel.send(event)
    }

    companion object {
        val instance: SnackbarController by lazy { DefaultSnackbarController() }
    }
}
