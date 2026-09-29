package com.techquantum.components.input

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    variant: AppTextFieldVariant = AppTextFieldVariant.OUTLINED,
    label: String? = "Quantity",
    placeholder: String? = "0",
    helperText: String? = null,
    errorMessage: String? = null,
    allowDecimals: Boolean = false,
    showSteppers: Boolean = true,
    step: Double = 1.0,
    minValue: Double? = 0.0,
    maxValue: Double? = null,
    prefix: String? = null,
    suffix: String? = null,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val numericValue = value.toDoubleOrNull() ?: 0.0

    val onIncrement = {
        val next = numericValue + step
        if (maxValue == null || next <= maxValue) {
            onValueChange(if (allowDecimals) String.format("%.2f", next) else next.toInt().toString())
        }
    }

    val onDecrement = {
        val prev = numericValue - step
        if (minValue == null || prev >= minValue) {
            onValueChange(if (allowDecimals) String.format("%.2f", prev) else prev.toInt().toString())
        }
    }

    AppTextField(
        value = value,
        onValueChange = { input ->
            val filtered = if (allowDecimals) {
                input.filter { it.isDigit() || it == '.' }
            } else {
                input.filter { it.isDigit() }
            }
            onValueChange(filtered)
        },
        modifier = modifier,
        variant = variant,
        label = label,
        placeholder = placeholder,
        helperText = helperText,
        errorMessage = errorMessage,
        leadingIcon = prefix?.let {
            {
                Text(
                    text = it,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onSurfaceVariant,
                )
            }
        },
        trailingIcon = {
            if (showSteppers && enabled) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onDecrement,
                        enabled = minValue == null || numericValue > minValue,
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrement",
                            tint = if (minValue != null && numericValue <= minValue) {
                                AppTheme.colors.onSurfaceVariant.copy(alpha = 0.4f)
                            } else {
                                AppTheme.colors.primary
                            },
                        )
                    }
                    IconButton(
                        onClick = onIncrement,
                        enabled = maxValue == null || numericValue < maxValue,
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increment",
                            tint = if (maxValue != null && numericValue >= maxValue) {
                                AppTheme.colors.onSurfaceVariant.copy(alpha = 0.4f)
                            } else {
                                AppTheme.colors.primary
                            },
                        )
                    }
                }
            } else if (suffix != null) {
                Text(
                    text = suffix,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onSurfaceVariant,
                )
            }
        },
        enabled = enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (allowDecimals) KeyboardType.Decimal else KeyboardType.Number,
        ),
        keyboardActions = keyboardActions,
    )
}
