package com.techquantum.template.app.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techquantum.components.animation.AnimationPreset
import com.techquantum.components.animation.AppAnimatedVisibility
import com.techquantum.components.animation.AppExpandableCard
import com.techquantum.components.animation.bounceClick
import com.techquantum.components.button.AppButton
import com.techquantum.components.button.ButtonSize
import com.techquantum.components.button.ButtonVariant
import com.techquantum.components.card.AppCard
import com.techquantum.components.card.CardVariant
import com.techquantum.components.feedback.snackbar.DefaultSnackbarController
import com.techquantum.components.feedback.toast.DefaultToastController
import com.techquantum.components.input.AppNumberTextField
import com.techquantum.components.input.AppPasswordTextField
import com.techquantum.components.input.AppTextArea
import com.techquantum.components.input.AppTextField
import com.techquantum.components.input.AppTextFieldVariant
import com.techquantum.components.selection.AppCheckbox
import com.techquantum.components.selection.AppCheckboxCard
import com.techquantum.components.selection.AppRadioCard
import com.techquantum.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun InputsAndAnimationsShowcase(
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Form inputs state
    var outlinedText by remember { mutableStateOf("TechQuantum Template") }
    var filledText by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("SuperSecret123") }
    var quantity by remember { mutableStateOf("2") }
    var notes by remember { mutableStateOf("Scalable multi-module Android template built with Jetpack Compose.") }

    // Radio card state
    var selectedPlan by remember { mutableIntStateOf(1) } // 0: Starter, 1: Pro, 2: Enterprise

    // Checkbox state
    var singleChecked by remember { mutableStateOf(true) }
    var pushNotificationsChecked by remember { mutableStateOf(true) }
    var biometricChecked by remember { mutableStateOf(false) }

    // Animation visibility state
    var showAnimatedBox by remember { mutableStateOf(true) }
    var selectedPreset by remember { mutableStateOf(AnimationPreset.FADE_EXPAND) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = AppTheme.spacing.xl),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.md),
    ) {
        // Section 1: Input Box Variants
        AppCard(variant = CardVariant.Elevated) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
            ) {
                Text(
                    text = "1. Input Box Types",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Text(
                    text = "Custom text fields with Outlined & Filled variants, password toggle, number stepper, and character counter.",
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant,
                )

                // Outlined Text Field
                AppTextField(
                    value = outlinedText,
                    onValueChange = { outlinedText = it },
                    label = "Outlined Text Field",
                    placeholder = "Type something...",
                    helperText = "Standard bordered input with clear action",
                    showClearIcon = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = AppTheme.colors.primary,
                        )
                    },
                )

                // Filled Text Field
                AppTextField(
                    value = filledText,
                    onValueChange = { filledText = it },
                    variant = AppTextFieldVariant.FILLED,
                    label = "Filled Text Field",
                    placeholder = "Filled background style",
                    helperText = "Modern background tint without borders",
                    showClearIcon = true,
                )

                // Password with Animated Eye Toggle
                AppPasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password Input",
                    helperText = "Click the eye icon to toggle visibility",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = AppTheme.colors.primary,
                        )
                    },
                )

                // Number Stepper Input
                AppNumberTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = "Quantity / Counter",
                    showSteppers = true,
                    step = 1.0,
                    minValue = 1.0,
                    maxValue = 100.0,
                    suffix = "items",
                    helperText = "Numeric keyboard with +/- stepper buttons",
                )

                // Multiline Text Area
                AppTextArea(
                    value = notes,
                    onValueChange = { notes = it },
                    label = "Multiline Text Area",
                    placeholder = "Write detailed notes...",
                    maxLength = 150,
                    minLines = 3,
                    maxLines = 5,
                    helperText = "Supports character limits and auto-expansion",
                )
            }
        }

        // Section 2: Radio Buttons as Cards
        AppCard(variant = CardVariant.Elevated) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
            ) {
                Text(
                    text = "2. Radio Buttons as Cards",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Text(
                    text = "Selectable options with animated borders, surface tints, and custom radio indicators.",
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant,
                )

                AppRadioCard(
                    selected = selectedPlan == 0,
                    onClick = {
                        selectedPlan = 0
                        coroutineScope.launch {
                            DefaultToastController.instance.showToast("Selected Starter Plan")
                        }
                    },
                    title = "Starter Tier",
                    subtitle = "Basic features for personal projects and prototypes",
                    trailingContent = {
                        Text(
                            text = "Free",
                            style = AppTheme.typography.titleMedium,
                            color = AppTheme.colors.primary,
                        )
                    },
                )

                AppRadioCard(
                    selected = selectedPlan == 1,
                    onClick = {
                        selectedPlan = 1
                        coroutineScope.launch {
                            DefaultToastController.instance.showToast("Selected Pro Plan")
                        }
                    },
                    title = "Pro Tier",
                    subtitle = "Advanced networking, offline sync, and Room persistence",
                    badgeText = "Popular",
                    trailingContent = {
                        Text(
                            text = "\$12/mo",
                            style = AppTheme.typography.titleMedium,
                            color = AppTheme.colors.primary,
                        )
                    },
                )

                AppRadioCard(
                    selected = selectedPlan == 2,
                    onClick = {
                        selectedPlan = 2
                        coroutineScope.launch {
                            DefaultToastController.instance.showToast("Selected Enterprise Plan")
                        }
                    },
                    title = "Enterprise Tier",
                    subtitle = "Dedicated support, custom backend, and SLA guarantees",
                    trailingContent = {
                        Text(
                            text = "\$49/mo",
                            style = AppTheme.typography.titleMedium,
                            color = AppTheme.colors.primary,
                        )
                    },
                )
            }
        }

        // Section 3: Custom Checkboxes
        AppCard(variant = CardVariant.Elevated) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
            ) {
                Text(
                    text = "3. Custom Checkboxes",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Text(
                    text = "Custom rounded checkboxes with spring animations, and multi-select Checkbox Cards.",
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant,
                )

                AppCheckbox(
                    checked = singleChecked,
                    onCheckedChange = { singleChecked = it },
                    label = "I agree to the terms and privacy policy",
                    supportingText = "Animated spring checkmark with 48dp minimum touch target",
                )

                Spacer(modifier = Modifier.height(AppTheme.spacing.xxs))

                // Checkbox Cards
                AppCheckboxCard(
                    checked = pushNotificationsChecked,
                    onCheckedChange = { pushNotificationsChecked = it },
                    title = "Push Notifications",
                    subtitle = "Receive real-time alerts and sync updates",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = AppTheme.colors.primary,
                        )
                    },
                )

                AppCheckboxCard(
                    checked = biometricChecked,
                    onCheckedChange = { biometricChecked = it },
                    title = "Biometric Lock",
                    subtitle = "Protect sensitive offline database records",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = AppTheme.colors.primary,
                        )
                    },
                )
            }
        }

        // Section 4: Animations Showcase
        AppCard(variant = CardVariant.Elevated) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
            ) {
                Text(
                    text = "4. Micro-Interactions & Animations",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                )
                Text(
                    text = "Expandable cards, spring bounce click modifiers, and animated visibility transitions.",
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant,
                )

                // Expandable Card
                AppExpandableCard(
                    title = "Expandable Architecture Info",
                    subtitle = "Tap to toggle animated details",
                    initiallyExpanded = true,
                    badgeText = "M3 Motion",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = AppTheme.colors.primary,
                        )
                    },
                ) {
                    Text(
                        text = "â€¢ Smooth vertical height expansion\nâ€¢ 180Â° chevron rotation with tween easing\nâ€¢ Fully reactive and accessible",
                        style = AppTheme.typography.bodySmall,
                        color = AppTheme.colors.onSurfaceVariant,
                    )
                }

                // Bounce Click Effect Demo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.sm),
                ) {
                    AppCard(
                        variant = CardVariant.Filled,
                        modifier = Modifier
                            .weight(1f)
                            .bounceClick {
                                coroutineScope.launch {
                                    DefaultSnackbarController.instance.showSnackbar("Bounced with Spring Physics!")
                                }
                            },
                    ) {
                        Column(
                            modifier = Modifier.padding(AppTheme.spacing.sm),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Bounce Card",
                                style = AppTheme.typography.titleSmall,
                                color = AppTheme.colors.primary,
                            )
                            Text(
                                text = "Press me",
                                style = AppTheme.typography.labelSmall,
                                color = AppTheme.colors.onSurfaceVariant,
                            )
                        }
                    }

                    AppButton(
                        text = if (showAnimatedBox) "Hide Content" else "Show Content",
                        onClick = { showAnimatedBox = !showAnimatedBox },
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Small,
                        modifier = Modifier.weight(1f),
                    )
                }

                // Animated Visibility Demo
                AppAnimatedVisibility(
                    visible = showAnimatedBox,
                    preset = selectedPreset,
                ) {
                    AppCard(
                        variant = CardVariant.Outlined,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppTheme.spacing.md),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Animated content with spring physics and alpha fade.",
                                style = AppTheme.typography.bodyMedium,
                                color = AppTheme.colors.primary,
                            )
                        }
                    }
                }
            }
        }
    }
}
