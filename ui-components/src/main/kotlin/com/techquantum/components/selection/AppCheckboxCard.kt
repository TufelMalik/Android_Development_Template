package com.techquantum.components.selection

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.techquantum.components.core.ComponentsCore
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppCheckboxCard(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
) {
    val animatedBorderColor by animateColorAsState(
        targetValue = when {
            !enabled -> AppTheme.colors.outline.copy(alpha = 0.2f)
            checked -> AppTheme.colors.primary
            else -> AppTheme.colors.outline.copy(alpha = 0.4f)
        },
        animationSpec = tween(durationMillis = 200),
        label = "CheckboxCardBorderColor",
    )

    val animatedBorderWidth by animateDpAsState(
        targetValue = if (checked) 2.dp else 1.dp,
        animationSpec = tween(durationMillis = 150),
        label = "CheckboxCardBorderWidth",
    )

    val animatedBackgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> AppTheme.colors.surface.copy(alpha = 0.5f)
            checked -> AppTheme.colors.primaryContainer.copy(alpha = 0.22f)
            else -> AppTheme.colors.surface
        },
        animationSpec = tween(durationMillis = 200),
        label = "CheckboxCardBgColor",
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ComponentsCore.minTouchTargetSize)
            .clip(AppTheme.shapes.medium)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = AppTheme.colors.primary),
                onValueChange = onCheckedChange,
            ),
        shape = AppTheme.shapes.medium,
        color = animatedBackgroundColor,
        border = BorderStroke(animatedBorderWidth, animatedBorderColor),
        shadowElevation = if (checked) 2.dp else 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppCheckboxIndicator(
                checked = checked,
                checkedColor = AppTheme.colors.primary,
                uncheckedBorderColor = if (enabled) AppTheme.colors.outline else AppTheme.colors.outline.copy(alpha = 0.3f),
            )

            Spacer(modifier = Modifier.width(AppTheme.spacing.sm))

            if (leadingIcon != null) {
                leadingIcon()
                Spacer(modifier = Modifier.width(AppTheme.spacing.sm))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = AppTheme.typography.titleMedium,
                    color = if (enabled) AppTheme.colors.onSurface else AppTheme.colors.onSurface.copy(alpha = 0.4f),
                )

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
