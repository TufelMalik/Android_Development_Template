package com.techquantum.template.app.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.techquantum.template.components.button.AppButton
import com.techquantum.template.components.button.ButtonSize
import com.techquantum.template.components.button.ButtonVariant
import com.techquantum.template.components.card.AppCard
import com.techquantum.template.components.card.CardVariant
import com.techquantum.template.components.core.ComponentsCore
import com.techquantum.template.components.feedback.snackbar.DefaultSnackbarController
import com.techquantum.template.components.feedback.toast.DefaultToastController
import com.techquantum.template.components.item.AppAvatar
import com.techquantum.template.components.item.AppListItem
import com.techquantum.template.components.list.AppLazyGrid
import com.techquantum.template.components.list.AppLazyRow
import com.techquantum.template.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun GridDemoScreen(
    viewModel: GridDemoViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = AppTheme.windowSizeClass
    val columnCount = ComponentsCore.resolveColumnCount(windowSizeClass)
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf("All", "Core", "Network", "Database", "UI", "Design")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.md),
    ) {
        // Responsiveness Metrics Banner
        AppCard(variant = CardVariant.Outlined) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Responsive AppLazyGrid Status",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))
                Text(
                    text = "Screen Window Class: ${windowSizeClass.widthSizeClass} | Grid Columns: $columnCount",
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.primary,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.sm))
                // State Switcher Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs),
                ) {
                    AppButton(
                        text = "Content",
                        onClick = { viewModel.showContent() },
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    AppButton(
                        text = "Loading",
                        onClick = { viewModel.showLoading() },
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    AppButton(
                        text = "Empty",
                        onClick = { viewModel.showEmpty() },
                        variant = ButtonVariant.Outline,
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    AppButton(
                        text = "Error",
                        onClick = { viewModel.showError() },
                        variant = ButtonVariant.Danger,
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        // Horizontal Carousel (AppLazyRow)
        Text(
            text = "Categories (AppLazyRow):",
            style = AppTheme.typography.labelLarge,
            color = AppTheme.colors.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
        AppLazyRow(items = categories) { category ->
            AppButton(
                text = category,
                onClick = {
                    coroutineScope.launch {
                        DefaultToastController.instance.showToast("Selected category: $category")
                    }
                },
                variant = ButtonVariant.Outline,
                size = ButtonSize.Small,
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.md))

        // Responsive Grid
        AppLazyGrid(
            state = uiState,
            key = { it.id },
            modifier = Modifier.weight(1f),
        ) { item ->
            AppCard(
                variant = CardVariant.Elevated,
                onClick = {
                    coroutineScope.launch {
                        DefaultSnackbarController.instance.showSnackbar(
                            message = "Tapped '${item.title}'",
                            actionLabel = "OK",
                        )
                    }
                },
            ) {
                AppListItem(
                    headline = item.title,
                    supportingText = item.subtitle,
                    leadingContent = {
                        AppAvatar(name = item.title)
                    },
                )
            }
        }
    }
}
