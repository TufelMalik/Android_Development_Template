package com.techquantum.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    variant: AppTextFieldVariant = AppTextFieldVariant.OUTLINED,
    label: String? = null,
    placeholder: String? = "Enter your notes here...",
    helperText: String? = null,
    errorMessage: String? = null,
    minLines: Int = 3,
    maxLines: Int = 6,
    maxLength: Int? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
) {
    val isOverLimit = maxLength != null && value.length > maxLength
    val isError = errorMessage != null || isOverLimit

    Column(modifier = modifier) {
        when (variant) {
            AppTextFieldVariant.OUTLINED -> {
                OutlinedTextField(
                    value = value,
                    onValueChange = {
                        if (maxLength == null || it.length <= maxLength) {
                            onValueChange(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    readOnly = readOnly,
                    minLines = minLines,
                    maxLines = maxLines,
                    label = label?.let { { Text(text = it) } },
                    placeholder = placeholder?.let { { Text(text = it) } },
                    isError = isError,
                    shape = AppTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppTheme.colors.primary,
                        unfocusedBorderColor = AppTheme.colors.outline,
                        errorBorderColor = AppTheme.colors.error,
                        focusedLabelColor = AppTheme.colors.primary,
                        unfocusedLabelColor = AppTheme.colors.onSurfaceVariant,
                        errorLabelColor = AppTheme.colors.error,
                        focusedTextColor = AppTheme.colors.onSurface,
                        unfocusedTextColor = AppTheme.colors.onSurface,
                    ),
                )
            }
            AppTextFieldVariant.FILLED -> {
                TextField(
                    value = value,
                    onValueChange = {
                        if (maxLength == null || it.length <= maxLength) {
                            onValueChange(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    readOnly = readOnly,
                    minLines = minLines,
                    maxLines = maxLines,
                    label = label?.let { { Text(text = it) } },
                    placeholder = placeholder?.let { { Text(text = it) } },
                    isError = isError,
                    shape = AppTheme.shapes.medium,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = AppTheme.colors.primary,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = AppTheme.colors.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = AppTheme.colors.surfaceVariant.copy(alpha = 0.35f),
                        errorIndicatorColor = AppTheme.colors.error,
                        focusedLabelColor = AppTheme.colors.primary,
                        unfocusedLabelColor = AppTheme.colors.onSurfaceVariant,
                        errorLabelColor = AppTheme.colors.error,
                        focusedTextColor = AppTheme.colors.onSurface,
                        unfocusedTextColor = AppTheme.colors.onSurface,
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val textToShow = errorMessage ?: helperText
            if (textToShow != null) {
                Text(
                    text = textToShow,
                    style = AppTheme.typography.bodySmall,
                    color = if (errorMessage != null) AppTheme.colors.error else AppTheme.colors.onSurfaceVariant,
                    modifier = Modifier.weight(1f, fill = false),
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            if (maxLength != null) {
                Text(
                    text = "${value.length}/$maxLength",
                    style = AppTheme.typography.bodySmall,
                    color = if (isOverLimit) AppTheme.colors.error else AppTheme.colors.onSurfaceVariant,
                )
            }
        }
    }
}
