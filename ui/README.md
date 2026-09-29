# ui Module — Design System

Design tokens and the top-level theme wrapper for the application.

## What it does
- **`UiCore`**: The single Core file defining base spacing, touch targets, radius scales, seed colors, and default theme mode.
- **`AppTheme`**: Top-level theme composable providing semantic colors, typography, shapes, spacing, sizing, elevation, and window size classes via `CompositionLocalProvider`. Exposes unified `AppTheme.colors`, `AppTheme.spacing`, `AppTheme.shapes`, etc. accessors.
- **`WindowSizeClass`**: Responsive width classifier categorizing screens into `COMPACT`, `MEDIUM`, and `EXPANDED` using fixed breakpoints (600dp, 840dp).
- **Tokens**: `Spacing`, `Sizing`, `Elevation`, `Motion`, `AppTypography`, `AppShapes`, `AppColors`.

## Safe-to-Delete Files
`ui` provides tokens for `ui-components` and `app`.
If you are replacing the design system tokens, modify `UiCore.kt`.

## Tunable Core Values (`UiCore.kt`)
`ui/src/main/kotlin/com/techquantum/template/ui/core/UiCore.kt` is the single place to re-brand every screen in every project built from this template:
- `baseSpacingUnit`: Base spacing multiplier (default: 8.dp).
- `minTouchTargetSize`: Minimum accessible touch target (default: 48.dp).
- `radiusNone`, `radiusSmall`, `radiusMedium`, `radiusLarge`, `radiusFull`: Corner radius scale.
- `defaultThemeMode`: `ThemeMode.SYSTEM`, `ThemeMode.LIGHT`, or `ThemeMode.DARK`.
- `lightPrimary`, `darkPrimary`, and other semantic palette seed colors.
