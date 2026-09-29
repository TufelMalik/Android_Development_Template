package com.techquantum.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import com.techquantum.components.card.AppCard
import com.techquantum.components.card.CardVariant
import com.techquantum.ui.theme.AppTheme

@Composable
fun AppExpandableCard(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    variant: CardVariant = CardVariant.Outlined,
    initiallyExpanded: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    badgeText: String? = null,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
        label = "ChevronRotation",
    )

    AppCard(
        variant = variant,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(AppTheme.shapes.medium)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = AppTheme.colors.primary),
                        onClick = { expanded = !expanded },
                    )
                    .padding(AppTheme.spacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
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
                            color = AppTheme.colors.onSurface,
                        )
                        if (badgeText != null) {
                            Text(
                                text = badgeText,
                                style = AppTheme.typography.labelSmall,
                                color = AppTheme.colors.primary,
                            )
                        }
                    }

                    if (subtitle != null) {
                        Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))
                        Text(
                            text = subtitle,
                            style = AppTheme.typography.bodySmall,
                            color = AppTheme.colors.onSurfaceVariant,
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    modifier = Modifier.rotate(rotationAngle),
                    tint = AppTheme.colors.onSurfaceVariant,
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(200)) + expandVertically(animationSpec = tween(250)),
                exit = fadeOut(animationSpec = tween(150)) + shrinkVertically(animationSpec = tween(200)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.md, vertical = AppTheme.spacing.sm),
                ) {
                    content()
                }
            }
        }
    }
}
