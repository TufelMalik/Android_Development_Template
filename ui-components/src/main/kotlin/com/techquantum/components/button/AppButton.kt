package com.techquantum.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techquantum.components.core.ComponentsCore
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val buttonColors = AppButtonDefaults.buttonColors(variant)
    val contentPadding = AppButtonDefaults.contentPadding(size)
    val height = AppButtonDefaults.minHeight(size)
    val shape = AppTheme.shapes.medium
    val isActionable = enabled && !loading

    // Enforce minimum touch target from ComponentsCore
    val buttonModifier = modifier
        .defaultMinSize(
            minWidth = ComponentsCore.minTouchTargetSize,
            minHeight = maxOf(height, ComponentsCore.minTouchTargetSize),
        )

    val content: @Composable () -> Unit = {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = if (variant == ButtonVariant.Primary || variant == ButtonVariant.Danger) {
                    AppTheme.colors.onPrimary
                } else {
                    AppTheme.colors.primary
                },
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(AppTheme.spacing.xs))
                }
                Text(
                    text = text,
                    style = when (size) {
                        ButtonSize.Small -> AppTheme.typography.labelMedium
                        ButtonSize.Medium -> AppTheme.typography.labelLarge
                        ButtonSize.Large -> AppTheme.typography.titleMedium
                    },
                )
            }
        }
    }

    when (variant) {
        ButtonVariant.Outline -> {
            OutlinedButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = isActionable,
                shape = shape,
                colors = buttonColors,
                border = AppButtonDefaults.borderStroke(variant, isActionable),
                contentPadding = contentPadding,
            ) {
                content()
            }
        }
        ButtonVariant.Text -> {
            TextButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = isActionable,
                shape = shape,
                colors = buttonColors,
                contentPadding = contentPadding,
            ) {
                content()
            }
        }
        else -> {
            Button(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = isActionable,
                shape = shape,
                colors = buttonColors,
                contentPadding = contentPadding,
            ) {
                content()
            }
        }
    }
}
