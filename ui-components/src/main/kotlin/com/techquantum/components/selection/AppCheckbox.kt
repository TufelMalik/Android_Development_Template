package com.techquantum.components.selection

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
fun AppCheckboxIndicator(
    checked: Boolean,
    modifier: Modifier = Modifier,
    checkedColor: Color = AppTheme.colors.primary,
    uncheckedBorderColor: Color = AppTheme.colors.outline,
    checkmarkColor: Color = AppTheme.colors.onPrimary,
) {
    val animatedBgColor by animateColorAsState(
        targetValue = if (checked) checkedColor else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "CheckboxBgColor",
    )
    val animatedBorderColor by animateColorAsState(
        targetValue = if (checked) checkedColor else uncheckedBorderColor,
        animationSpec = tween(durationMillis = 200),
        label = "CheckboxBorderColor",
    )
    val checkmarkScale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.55f, stiffness = 600f),
        label = "CheckmarkScale",
    )

    Box(
        modifier = modifier
            .size(22.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(animatedBgColor)
            .border(
                width = 2.dp,
                color = animatedBorderColor,
                shape = RoundedCornerShape(6.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (checkmarkScale > 0f) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = checkmarkColor,
                modifier = Modifier
                    .size(16.dp)
                    .scale(checkmarkScale),
            )
        }
    }
}

@Composable
fun AppCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    supportingText: String? = null,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = ComponentsCore.minTouchTargetSize)
            .clip(AppTheme.shapes.small)
            .clickable(
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = AppTheme.colors.primary),
                onClick = { onCheckedChange(!checked) },
            )
            .padding(vertical = AppTheme.spacing.xs, horizontal = AppTheme.spacing.xxs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppCheckboxIndicator(
            checked = checked,
            checkedColor = if (enabled) AppTheme.colors.primary else AppTheme.colors.primary.copy(alpha = 0.4f),
            uncheckedBorderColor = if (enabled) AppTheme.colors.outline else AppTheme.colors.outline.copy(alpha = 0.3f),
        )

        if (label != null) {
            Spacer(modifier = Modifier.width(AppTheme.spacing.sm))
            Column {
                Text(
                    text = label,
                    style = AppTheme.typography.bodyMedium,
                    color = if (enabled) AppTheme.colors.onSurface else AppTheme.colors.onSurface.copy(alpha = 0.4f),
                )
                if (supportingText != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = supportingText,
                        style = AppTheme.typography.bodySmall,
                        color = if (enabled) AppTheme.colors.onSurfaceVariant else AppTheme.colors.onSurfaceVariant.copy(alpha = 0.4f),
                    )
                }
            }
        }
    }
}
