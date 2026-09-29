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
import com.techquantum.components.button.AppButton
import com.techquantum.components.button.ButtonSize
import com.techquantum.components.button.ButtonVariant
import com.techquantum.components.card.AppCard
import com.techquantum.components.card.CardVariant
import com.techquantum.components.core.ComponentsCore
import com.techquantum.components.feedback.snackbar.DefaultSnackbarController
import com.techquantum.components.feedback.toast.DefaultToastController
import com.techquantum.components.item.AppAvatar
import com.techquantum.components.item.AppListItem
import com.techquantum.components.list.AppLazyGrid
import com.techquantum.components.list.AppLazyRow
import com.techquantum.ui.theme.AppTheme
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

@Composable
fun GridDemoScreen(
    viewModel: GridDemoViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = com.techquantum.ui.theme.AppTheme.windowSizeClass
    val columnCount = com.techquantum.components.core.ComponentsCore.resolveColumnCount(windowSizeClass)
    val coroutineScope = rememberCoroutineScope()

    var selectedSection by remember { mutableIntStateOf(0) } // 0: Grid, 1: Inputs & Animations
    val categories = listOf("All", "Core", "Network", "Database", "UI", "Design")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(com.techquantum.ui.theme.AppTheme.spacing.md),
    ) {
        // Section Navigation Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(com.techquantum.ui.theme.AppTheme.spacing.xs),
        ) {
            com.techquantum.components.button.AppButton(
                text = "Responsive Grid",
                onClick = { selectedSection = 0 },
                variant = if (selectedSection == 0) com.techquantum.components.button.ButtonVariant.Primary else com.techquantum.components.button.ButtonVariant.Outline,
                size = com.techquantum.components.button.ButtonSize.Small,
                modifier = Modifier.weight(1f),
            )
            com.techquantum.components.button.AppButton(
                text = "Inputs & Animations",
                onClick = { selectedSection = 1 },
                variant = if (selectedSection == 1) com.techquantum.components.button.ButtonVariant.Primary else com.techquantum.components.button.ButtonVariant.Outline,
                size = com.techquantum.components.button.ButtonSize.Small,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(com.techquantum.ui.theme.AppTheme.spacing.sm))

        if (selectedSection == 1) {
            InputsAndAnimationsShowcase(
                modifier = Modifier.weight(1f),
            )
        } else {
            // Responsiveness Metrics Banner
            com.techquantum.components.card.AppCard(variant = com.techquantum.components.card.CardVariant.Outlined) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Responsive AppLazyGrid Status",
                    style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.titleMedium,
                    color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.onSurface,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xxs))
                Text(
                    text = "Screen Window Class: ${windowSizeClass.widthSizeClass} | Grid Columns: $columnCount",
                    style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.bodyMedium,
                    color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.primary,
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.sm))
                // State Switcher Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xs),
                ) {
                    _root_ide_package_.com.techquantum.components.button.AppButton(
                        text = "Content",
                        onClick = { viewModel.showContent() },
                        size = _root_ide_package_.com.techquantum.components.button.ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    _root_ide_package_.com.techquantum.components.button.AppButton(
                        text = "Loading",
                        onClick = { viewModel.showLoading() },
                        variant = _root_ide_package_.com.techquantum.components.button.ButtonVariant.Secondary,
                        size = _root_ide_package_.com.techquantum.components.button.ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    _root_ide_package_.com.techquantum.components.button.AppButton(
                        text = "Empty",
                        onClick = { viewModel.showEmpty() },
                        variant = _root_ide_package_.com.techquantum.components.button.ButtonVariant.Outline,
                        size = _root_ide_package_.com.techquantum.components.button.ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                    _root_ide_package_.com.techquantum.components.button.AppButton(
                        text = "Error",
                        onClick = { viewModel.showError() },
                        variant = _root_ide_package_.com.techquantum.components.button.ButtonVariant.Danger,
                        size = _root_ide_package_.com.techquantum.components.button.ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.md))

        // Horizontal Carousel (AppLazyRow)
        Text(
            text = "Categories (AppLazyRow):",
            style = _root_ide_package_.com.techquantum.ui.theme.AppTheme.typography.labelLarge,
            color = _root_ide_package_.com.techquantum.ui.theme.AppTheme.colors.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.xs))
        _root_ide_package_.com.techquantum.components.list.AppLazyRow(items = categories) { category ->
            _root_ide_package_.com.techquantum.components.button.AppButton(
                text = category,
                onClick = {
                    coroutineScope.launch {
                        _root_ide_package_.com.techquantum.components.feedback.toast.DefaultToastController.instance.showToast(
                            "Selected category: $category"
                        )
                    }
                },
                variant = _root_ide_package_.com.techquantum.components.button.ButtonVariant.Outline,
                size = _root_ide_package_.com.techquantum.components.button.ButtonSize.Small,
            )
        }

        Spacer(modifier = Modifier.height(_root_ide_package_.com.techquantum.ui.theme.AppTheme.spacing.md))

        // Responsive Grid
        _root_ide_package_.com.techquantum.components.list.AppLazyGrid(
            state = uiState,
            key = { it.id },
            modifier = Modifier.weight(1f),
        ) { item ->
            _root_ide_package_.com.techquantum.components.card.AppCard(
                variant = _root_ide_package_.com.techquantum.components.card.CardVariant.Elevated,
                onClick = {
                    coroutineScope.launch {
                        _root_ide_package_.com.techquantum.components.feedback.snackbar.DefaultSnackbarController.instance.showSnackbar(
                            message = "Tapped '${item.title}'",
                            actionLabel = "OK",
                        )
                    }
                },
            ) {
                _root_ide_package_.com.techquantum.components.item.AppListItem(
                    headline = item.title,
                    supportingText = item.subtitle,
                    leadingContent = {
                        _root_ide_package_.com.techquantum.components.item.AppAvatar(name = item.title)
                    },
                )
            }
        }
    }
}
}
