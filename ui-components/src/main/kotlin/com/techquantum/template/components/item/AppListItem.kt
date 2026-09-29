package com.techquantum.template.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.techquantum.template.components.core.ComponentsCore
import com.techquantum.template.ui.theme.AppTheme

@Composable
fun AppListItem(
    headline: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    containerColor: Color = Color.Transparent,
) {
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Surface(
        color = containerColor,
        modifier = modifier
            .fillMaxWidth()
            .then(clickableModifier)
            .defaultMinSize(minHeight = ComponentsCore.minTouchTargetSize),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.md, vertical = AppTheme.spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingContent != null) {
                Box(contentAlignment = Alignment.Center) {
                    leadingContent()
                }
                Spacer(modifier = Modifier.width(AppTheme.spacing.md))
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = headline,
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colors.onSurface,
                )
                if (supportingText != null) {
                    Text(
                        text = supportingText,
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.onSurfaceVariant,
                    )
                }
            }

            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(AppTheme.spacing.md))
                Box(contentAlignment = Alignment.Center) {
                    trailingContent()
                }
            }
        }
    }
}
