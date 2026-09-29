package com.techquantum.components.feedback.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.techquantum.components.button.AppButton
import com.techquantum.components.button.ButtonSize
import com.techquantum.components.button.ButtonVariant
import com.techquantum.ui.theme.AppTheme

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    isDestructive: Boolean = false,
) {
    AppDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colors.onSurface,
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.sm))
            Text(
                text = message,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.lg))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppButton(
                    text = cancelText,
                    onClick = onDismissRequest,
                    variant = ButtonVariant.Text,
                    size = ButtonSize.Small,
                )
                Spacer(modifier = Modifier.width(AppTheme.spacing.sm))
                AppButton(
                    text = confirmText,
                    onClick = {
                        onConfirm()
                        onDismissRequest()
                    },
                    variant = if (isDestructive) ButtonVariant.Danger else ButtonVariant.Primary,
                    size = ButtonSize.Small,
                )
            }
        }
    }
}
