package com.techquantum.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    variant: AppTextFieldVariant = AppTextFieldVariant.OUTLINED,
    label: String? = null,
    placeholder: String? = null,
    helperText: String? = null,
    errorMessage: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    showClearIcon: Boolean = false,
    isError: Boolean = errorMessage != null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val resolvedTrailingIcon: (@Composable () -> Unit)? = when {
        showClearIcon && value.isNotEmpty() && enabled && !readOnly -> {
            {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear text",
                        tint = AppTheme.colors.onSurfaceVariant,
                    )
                }
            }
        }
        else -> trailingIcon
    }

    Column(modifier = modifier) {
        when (variant) {
            AppTextFieldVariant.OUTLINED -> {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    readOnly = readOnly,
                    singleLine = singleLine,
                    label = label?.let { { Text(text = it) } },
                    placeholder = placeholder?.let { { Text(text = it) } },
                    leadingIcon = leadingIcon,
                    trailingIcon = resolvedTrailingIcon,
                    isError = isError,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
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
                        disabledTextColor = AppTheme.colors.onSurfaceVariant,
                    ),
                )
            }
            AppTextFieldVariant.FILLED -> {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    readOnly = readOnly,
                    singleLine = singleLine,
                    label = label?.let { { Text(text = it) } },
                    placeholder = placeholder?.let { { Text(text = it) } },
                    leadingIcon = leadingIcon,
                    trailingIcon = resolvedTrailingIcon,
                    isError = isError,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
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

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))
            Text(
                text = errorMessage,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.error,
            )
        } else if (helperText != null) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))
            Text(
                text = helperText,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.onSurfaceVariant,
            )
        }
    }
}
