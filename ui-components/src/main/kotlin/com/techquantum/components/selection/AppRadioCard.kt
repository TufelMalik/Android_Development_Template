package com.techquantum.components.selection

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.techquantum.components.core.ComponentsCore
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppRadioIndicator(
    selected: Boolean,
    modifier: Modifier = Modifier,
    selectedColor: Color = AppTheme.colors.primary,
    unselectedColor: Color = AppTheme.colors.outline,
) {
    val animatedBorderColor by animateColorAsState(
        targetValue = if (selected) selectedColor else unselectedColor,
        animationSpec = tween(durationMillis = 200),
        label = "RadioBorderColor",
    )
    val dotScale by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 500f),
        label = "RadioDotScale",
    )

    Box(
        modifier = modifier
            .size(22.dp)
            .border(
                width = 2.dp,
                color = animatedBorderColor,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (dotScale > 0f) {
            Box(
                modifier = Modifier
                    .size(11.dp)
                    .scale(dotScale)
                    .clip(CircleShape)
                    .background(selectedColor),
            )
        }
    }
}

@Composable
fun AppRadioCard(
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    badgeText: String? = null,
    enabled: Boolean = true,
) {
    val animatedBorderColor by animateColorAsState(
        targetValue = when {
            !enabled -> AppTheme.colors.outline.copy(alpha = 0.2f)
            selected -> AppTheme.colors.primary
            else -> AppTheme.colors.outline.copy(alpha = 0.4f)
        },
        animationSpec = tween(durationMillis = 200),
        label = "CardBorderColor",
    )

    val animatedBorderWidth by animateDpAsState(
        targetValue = if (selected) 2.dp else 1.dp,
        animationSpec = tween(durationMillis = 150),
        label = "CardBorderWidth",
    )

    val animatedBackgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> AppTheme.colors.surface.copy(alpha = 0.5f)
            selected -> AppTheme.colors.primaryContainer.copy(alpha = 0.22f)
            else -> AppTheme.colors.surface
        },
        animationSpec = tween(durationMillis = 200),
        label = "CardBackgroundColor",
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ComponentsCore.minTouchTargetSize)
            .clip(AppTheme.shapes.medium)
            .selectable(
                selected = selected,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = AppTheme.colors.primary),
                onClick = onClick,
            ),
        shape = AppTheme.shapes.medium,
        color = animatedBackgroundColor,
        border = BorderStroke(animatedBorderWidth, animatedBorderColor),
        shadowElevation = if (selected) 2.dp else 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppRadioIndicator(
                selected = selected,
                selectedColor = AppTheme.colors.primary,
                unselectedColor = if (enabled) AppTheme.colors.outline else AppTheme.colors.outline.copy(alpha = 0.3f),
            )

            Spacer(modifier = Modifier.width(AppTheme.spacing.sm))

            if (leadingIcon != null) {
                leadingIcon()
                Spacer(modifier = Modifier.width(AppTheme.spacing.sm))
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs),
                ) {
                    Text(
                        text = title,
                        style = AppTheme.typography.titleMedium,
                        color = if (enabled) AppTheme.colors.onSurface else AppTheme.colors.onSurface.copy(alpha = 0.4f),
                    )
                    if (badgeText != null) {
                        Box(
                            modifier = Modifier
                                .clip(AppTheme.shapes.small)
                                .background(AppTheme.colors.primary.copy(alpha = 0.15f))
                                .padding(horizontal = AppTheme.spacing.xs, vertical = 2.dp),
                        ) {
                            Text(
                                text = badgeText,
                                style = AppTheme.typography.labelSmall,
                                color = AppTheme.colors.primary,
                            )
                        }
                    }
                }

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = AppTheme.typography.bodySmall,
                        color = if (enabled) AppTheme.colors.onSurfaceVariant else AppTheme.colors.onSurfaceVariant.copy(alpha = 0.4f),
                    )
                }
            }

            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(AppTheme.spacing.xs))
                trailingContent()
            }
        }
    }
}

@Composable
fun <T> AppRadioCardGroup(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    titleProvider: (T) -> String,
    modifier: Modifier = Modifier,
    subtitleProvider: ((T) -> String?)? = null,
    badgeProvider: ((T) -> String?)? = null,
    trailingContentProvider: ((@Composable (T) -> Unit))? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs),
    ) {
        items.forEach { item ->
            AppRadioCard(
                selected = item == selectedItem,
                onClick = { onItemSelected(item) },
                title = titleProvider(item),
                subtitle = subtitleProvider?.invoke(item),
                badgeText = badgeProvider?.invoke(item),
                trailingContent = trailingContentProvider?.let { { it(item) } },
            )
        }
    }
}
