package com.techquantum.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.Elevated,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val shape = AppTheme.shapes.large

    when (variant) {
        CardVariant.Elevated -> {
            if (onClick != null) {
                ElevatedCard(
                    onClick = onClick,
                    modifier = modifier,
                    shape = shape,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = AppTheme.colors.surface,
                        contentColor = AppTheme.colors.onSurface,
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = AppTheme.elevation.level1,
                        pressedElevation = AppTheme.elevation.level2,
                    ),
                ) {
                    Box(modifier = Modifier.padding(AppTheme.spacing.md)) {
                        content()
                    }
                }
            } else {
                ElevatedCard(
                    modifier = modifier,
                    shape = shape,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = AppTheme.colors.surface,
                        contentColor = AppTheme.colors.onSurface,
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = AppTheme.elevation.level1,
                    ),
                ) {
                    Box(modifier = Modifier.padding(AppTheme.spacing.md)) {
                        content()
                    }
                }
            }
        }
        CardVariant.Outlined -> {
            val border = BorderStroke(1.dp, AppTheme.colors.outlineVariant)
            if (onClick != null) {
                OutlinedCard(
                    onClick = onClick,
                    modifier = modifier,
                    shape = shape,
                    border = border,
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = AppTheme.colors.surface,
                        contentColor = AppTheme.colors.onSurface,
                    ),
                ) {
                    Box(modifier = Modifier.padding(AppTheme.spacing.md)) {
                        content()
                    }
                }
            } else {
                OutlinedCard(
                    modifier = modifier,
                    shape = shape,
                    border = border,
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = AppTheme.colors.surface,
                        contentColor = AppTheme.colors.onSurface,
                    ),
                ) {
                    Box(modifier = Modifier.padding(AppTheme.spacing.md)) {
                        content()
                    }
                }
            }
        }
        CardVariant.Filled -> {
            if (onClick != null) {
                Card(
                    onClick = onClick,
                    modifier = modifier,
                    shape = shape,
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.colors.surfaceVariant,
                        contentColor = AppTheme.colors.onSurfaceVariant,
                    ),
                ) {
                    Box(modifier = Modifier.padding(AppTheme.spacing.md)) {
                        content()
                    }
                }
            } else {
                Card(
                    modifier = modifier,
                    shape = shape,
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.colors.surfaceVariant,
                        contentColor = AppTheme.colors.onSurfaceVariant,
                    ),
                ) {
                    Box(modifier = Modifier.padding(AppTheme.spacing.md)) {
                        content()
                    }
                }
            }
        }
    }
}
