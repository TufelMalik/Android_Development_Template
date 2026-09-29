package com.techquantum.template.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import com.techquantum.template.ui.theme.AppTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    errorMessage: String? = null,
    isError: Boolean = errorMessage != null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(modifier = modifier) {
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
            trailingIcon = trailingIcon,
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
            ),
        )
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))
            Text(
                text = errorMessage,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.error,
            )
        }
    }
}
