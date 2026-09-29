package com.techquantum.components.feedback.toast

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

data class ToastEvent(
    val message: String,
    val durationMs: Long = 2500L,
)

interface ToastController {
    val events: Flow<ToastEvent>
    suspend fun showToast(message: String, durationMs: Long = 2500L)
    suspend fun sendEvent(event: ToastEvent)
}

class DefaultToastController : ToastController {
    private val channel = Channel<ToastEvent>(Channel.BUFFERED)
    override val events: Flow<ToastEvent> = channel.receiveAsFlow()

    override suspend fun showToast(message: String, durationMs: Long) {
        channel.send(ToastEvent(message, durationMs))
    }

    override suspend fun sendEvent(event: ToastEvent) {
        channel.send(event)
    }

    companion object {
        val instance: ToastController by lazy { DefaultToastController() }
    }
}
