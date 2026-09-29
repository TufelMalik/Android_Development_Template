package com.techquantum.components.feedback.snackbar

import androidx.compose.material3.SnackbarDuration

data class SnackbarEvent(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val onActionPerformed: (() -> Unit)? = null,
    val onDismissed: (() -> Unit)? = null,
)
