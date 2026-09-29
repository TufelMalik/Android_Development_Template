package com.techquantum.template.app.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.techquantum.template.components.button.AppButton
import com.techquantum.template.components.button.ButtonSize
import com.techquantum.template.components.button.ButtonVariant
import com.techquantum.template.components.card.AppCard
import com.techquantum.template.components.card.CardVariant
import com.techquantum.template.ui.core.UiCore
import com.techquantum.template.ui.theme.AppTheme
import com.techquantum.template.ui.theme.ThemeMode

@Composable
fun ThemeDemoScreen(
    viewModel: ThemeDemoViewModel,
    modifier: Modifier = Modifier,
) {
    val currentMode by viewModel.themeMode.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(AppTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.lg),
    ) {
        // Section: Theme Mode Selector
        AppCard(variant = CardVariant.Elevated) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Theme Mode Selection",
                    style = AppTheme.typography.titleLarge,
                    color = AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
                Text(
                    text = "Current mode: $currentMode. Configured default is ${UiCore.defaultThemeMode}.",
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.md))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
                ) {
                    AppButton(
                        text = "Light",
                        onClick = { viewModel.setThemeMode(ThemeMode.LIGHT) },
                        variant = if (currentMode == ThemeMode.LIGHT) ButtonVariant.Primary else ButtonVariant.Outline,
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    AppButton(
                        text = "Dark",
                        onClick = { viewModel.setThemeMode(ThemeMode.DARK) },
                        variant = if (currentMode == ThemeMode.DARK) ButtonVariant.Primary else ButtonVariant.Outline,
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    AppButton(
                        text = "System",
                        onClick = { viewModel.setThemeMode(ThemeMode.SYSTEM) },
                        variant = if (currentMode == ThemeMode.SYSTEM) ButtonVariant.Primary else ButtonVariant.Outline,
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        // Section: Semantic Color Palette
        AppCard(variant = CardVariant.Outlined) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Semantic Colors (AppColors)",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.md))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
                ) {
                    ColorSwatch("Primary", AppTheme.colors.primary, AppTheme.colors.onPrimary, Modifier.weight(1f))
                    ColorSwatch("Secondary", AppTheme.colors.secondary, AppTheme.colors.onSecondary, Modifier.weight(1f))
                    ColorSwatch("Surface", AppTheme.colors.surfaceVariant, AppTheme.colors.onSurfaceVariant, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
                ) {
                    ColorSwatch("Success", AppTheme.colors.success, AppTheme.colors.onSuccess, Modifier.weight(1f))
                    ColorSwatch("Warning", AppTheme.colors.warning, AppTheme.colors.onWarning, Modifier.weight(1f))
                    ColorSwatch("Error", AppTheme.colors.error, AppTheme.colors.onError, Modifier.weight(1f))
                }
            }
        }

        // Section: Typography Hierarchy
        AppCard(variant = CardVariant.Filled) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Typography Hierarchy",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.sm))
                Text(text = "Headline Medium", style = AppTheme.typography.headlineMedium)
                Text(text = "Title Large", style = AppTheme.typography.titleLarge)
                Text(text = "Body Large: The quick brown fox jumps over the lazy dog.", style = AppTheme.typography.bodyLarge)
                Text(text = "Label Small: DESIGN TOKENS SYSTEM", style = AppTheme.typography.labelSmall)
            }
        }

        // Section: Spacing and Corner Radii
        AppCard(variant = CardVariant.Elevated) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Radius & Spacing Tokens",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.sm))
                Text(
                    text = "Base Spacing Unit: ${UiCore.baseSpacingUnit} | Touch Target: ${UiCore.minTouchTargetSize}",
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.md))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    RadiusBox("Small", UiCore.radiusSmall)
                    RadiusBox("Medium", UiCore.radiusMedium)
                    RadiusBox("Large", UiCore.radiusLarge)
                }
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    label: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .background(containerColor, AppTheme.shapes.small)
            .border(1.dp, AppTheme.colors.outlineVariant, AppTheme.shapes.small)
            .padding(AppTheme.spacing.xs),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = AppTheme.typography.labelMedium,
            color = contentColor,
        )
    }
}

@Composable
private fun RadiusBox(
    label: String,
    radius: androidx.compose.ui.unit.Dp,
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .border(2.dp, AppTheme.colors.primary, RoundedCornerShape(radius))
            .background(AppTheme.colors.primaryContainer.copy(alpha = 0.4f), RoundedCornerShape(radius)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colors.onSurface,
        )
    }
}
