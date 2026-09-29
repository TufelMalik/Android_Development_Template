package com.techquantum.template.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techquantum.template.ui.theme.AppTheme

object AppButtonDefaults {
    @Composable
    fun buttonColors(variant: ButtonVariant): ButtonColors = when (variant) {
        ButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.onPrimary,
            disabledContainerColor = AppTheme.colors.surfaceVariant.copy(alpha = 0.38f),
            disabledContentColor = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.38f),
        )
        ButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.secondaryContainer,
            contentColor = AppTheme.colors.onSecondaryContainer,
            disabledContainerColor = AppTheme.colors.surfaceVariant.copy(alpha = 0.38f),
            disabledContentColor = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.38f),
        )
        ButtonVariant.Outline -> ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.primary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.38f),
        )
        ButtonVariant.Text -> ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.primary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.38f),
        )
        ButtonVariant.Danger -> ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.error,
            contentColor = AppTheme.colors.onError,
            disabledContainerColor = AppTheme.colors.surfaceVariant.copy(alpha = 0.38f),
            disabledContentColor = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.38f),
        )
    }

    @Composable
    fun borderStroke(variant: ButtonVariant, enabled: Boolean): BorderStroke? = when (variant) {
        ButtonVariant.Outline -> {
            val color = if (enabled) AppTheme.colors.outline else AppTheme.colors.outlineVariant
            BorderStroke(1.dp, color)
        }
        else -> null
    }

    @Composable
    fun minHeight(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> AppTheme.sizing.buttonHeightSm
        ButtonSize.Medium -> AppTheme.sizing.buttonHeightMd
        ButtonSize.Large -> AppTheme.sizing.buttonHeightLg
    }

    @Composable
    fun contentPadding(size: ButtonSize): PaddingValues = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = AppTheme.spacing.sm, vertical = AppTheme.spacing.xs)
        ButtonSize.Medium -> PaddingValues(horizontal = AppTheme.spacing.md, vertical = AppTheme.spacing.sm)
        ButtonSize.Large -> PaddingValues(horizontal = AppTheme.spacing.lg, vertical = AppTheme.spacing.md)
    }
}
