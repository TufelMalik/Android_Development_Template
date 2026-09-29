# ui-components Module

Reusable, screen-agnostic composable UI components built on `ui` tokens and the `AppLazyGrid` responsive list architecture.

## What it does
- **`ComponentsCore`**: Single Core file defining grid column counts (`compactColumnCount`, `mediumColumnCount`, `expandedColumnCount`), item spacing, and touch target constraints.
- **`AppLazyGrid`**: The primary responsive list component. Automatically renders single-column on phones and multi-column on foldables/tablets based on `WindowSizeClass`. Handles 4-state lifecycle (`Loading`, `Empty`, `Error`, `Content`) via `ListUiState`.
- **`AppLazyRow`**: Horizontal carousel for one-dimensional items.
- **`AppButton` / `AppButtonDefaults`**: Themed button supporting variants (`Primary`, `Secondary`, `Outline`, `Text`, `Danger`), sizes, loading spinner, and minimum touch targets.
- **`AppCard`**: Elevated, outlined, and filled surface cards.
- **`AppTextField` / `AppTextFieldVariant`**: Form inputs supporting `OUTLINED` and `FILLED` variants, helper texts, and clear buttons.
- **`AppPasswordTextField`**: Password input with animated visibility (eye icon) toggle.
- **`AppNumberTextField`**: Numeric input with +/- stepper buttons, min/max bounds, and prefix/suffix labels.
- **`AppTextArea`**: Multiline text input with character counters and max length restrictions.
- **`AppRadioCard` / `AppRadioCardGroup`**: Selectable single-choice cards with animated border, surface tint, and spring radio indicator.
- **`AppCheckbox` / `AppCheckboxCard`**: Custom rounded checkboxes with animated spring checkmarks and multi-select cards.
- **`AppAnimations` / `AppExpandableCard`**: Motion primitives including `bounceClick()` spring modifier, `pulseEffect()` breathing modifier, `AppAnimatedVisibility` presets, and animated expanding cards with rotating chevrons.
- **`AppListItem` / `AppAvatar`**: Standard list item and circular avatar fallback.
- **`SnackbarController` / `AppSnackbarHost`**: Decoupled snackbar event system for ViewModels.
- **`ToastController` / `AppToastHost`**: Self-dismissing Compose toast overlay.
- **`AppDialog` / `ConfirmDialog`**: Base dialog and confirm/cancel action dialogs.
- **`AppLoader` / `ShimmerBox`**: Progress indicator and skeleton shimmer box.

## Safe-to-Delete Files
- Any widget not used in your app can be safely removed or refactored.
- `AppLazyGrid` and `ComponentsCore` should be retained as the foundational responsive list mechanism.

## Tunable Core Values (`ComponentsCore.kt`)
`ui-components/src/main/kotlin/com/techquantum/template/components/core/ComponentsCore.kt`:
- `compactColumnCount`: Default column count on compact screens (default: 1).
- `mediumColumnCount`: Default column count on medium screens (default: 2).
- `expandedColumnCount`: Default column count on expanded screens (default: 3).
- `gridItemSpacing`: Spacing between grid items (default: 16.dp).
- `minTouchTargetSize`: Enforced minimum touch target size (default: 48.dp).
