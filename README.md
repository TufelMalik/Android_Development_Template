# Android Reusable Template

Base package: `com.techquantum.<app-name>`  
Target: **Android only** | DI: **Koin** | Min SDK: **24** | Target/Compile SDK: **37**

A high-performance, modular, crash-safe Android architecture designed for rapid development and clean scalability.

---

## 1. Repository Layout

```
android-template/
├── build-logic/
│   └── convention/                       # Included build, holds all Gradle convention plugins
│       └── src/main/kotlin/
│           ├── AndroidLibraryConventionPlugin
│           ├── AndroidComposeConventionPlugin
│           ├── KoinConventionPlugin
│           └── KotlinAndroid             # Shared helper (compileSdk, minSdk, javaVersion)
├── gradle/
│   └── libs.versions.toml                # Single version catalog for the whole repository
├── core-common/                          # Primitives: AppResult, AppError, safeRun, Dispatchers
├── ui/                                   # Design tokens, theme, and window size classifier
├── ui-components/                        # Responsive AppLazyGrid, buttons, cards, feedback
├── network/
│   ├── ktor/                             # Generic Ktor REST client with idempotent retries
│   └── firebase/                         # Firestore, Realtime DB, Auth, and Storage data sources
├── local-db/                             # Room persistence, BaseDao, and LocalDataSource
├── app/                                  # Demo application integrating every module
├── settings.gradle.kts
├── build.gradle.kts
└── README.md
```

### Module Dependency Graph

```
app ──────────────┬─> ui-components ─> ui ─> core-common
                   ├─> network:ktor ─────────> core-common
                   ├─> network:firebase ─────> core-common
                   └─> local-db ─────────────> core-common
```

> **Isolation Guarantee:** `ui`, `network:ktor`, `network:firebase`, and `local-db` never depend on each other. You can remove any one of them from `settings.gradle.kts` and `app/build.gradle.kts` without touching the others.

---

## 2. Modules Summary

### 2.1 `build-logic`
Centralized Gradle convention plugins:
- `template.android.library`: Configures `com.android.library`, SDK targets, and Java 21 compatibility.
- `template.android.compose`: Enables Compose and configures Compose BOM & UI artifacts.
- `template.koin`: Provides Koin core and Android dependencies.
- `KotlinAndroid`: Single helper defining `compileSdk = 37`, `minSdk = 24`, and JVM targets.

### 2.2 `core-common`
- **`AppResult<T>`**: Outcome type with `Success` / `Failure` branches, `map`, and monadic chaining (`onSuccess`, `onFailure`).
- **`AppError`**: Normalized error vocabulary (`Network`, `Timeout`, `Serialization`, `NotFound`, `Database`, `Auth`, `Unknown`).
- **`safeRun` Primitive**: Central suspend runner that catches non-cancellation exceptions into `AppError` while preserving coroutine cancellation.
- **`DispatcherProvider` & `AppLogger`**: Injected interfaces for testability.
- **`commonModule`**: Self-contained Koin module.

### 2.3 `ui` — Design System Tokens
- **`UiCore`**: Single Core file defining base spacing unit (8.dp), touch target minimum (48.dp), corner radius scale, and light/dark palette seeds.
- **`AppTheme`**: Top-level theme wrapper exposing unified static accessors (`AppTheme.colors`, `AppTheme.spacing`, `AppTheme.shapes`, `AppTheme.windowSizeClass`).
- **`WindowSizeClass`**: Responsive width classifier categorizing screens into `COMPACT`, `MEDIUM`, or `EXPANDED`.

### 2.4 `ui-components`
- **`ComponentsCore`**: Defines grid column counts (`compact = 1`, `medium = 2`, `expanded = 3`), spacing, and touch target constraints.
- **`AppLazyGrid<T>`**: The responsive list/grid primitive. Renders 1-column on phones and scales to multi-column on foldables/tablets automatically. Handles `ListUiState` (`Loading`, `Empty`, `Error`, `Content`).
- **`AppLazyRow<T>`**: Horizontal carousel for tags, chips, or media.
- **Components**: `AppButton` (with variants, sizes, and loading state), `AppCard`, `AppTextField`, `AppSearchBar`, `AppListItem`, `AppAvatar`.
- **Feedback**: `SnackbarController` / `AppSnackbarHost`, `ToastController` / `AppToastHost`, `AppDialog` / `ConfirmDialog`, `AppLoader`, `ShimmerBox`.

### 2.5 `network:ktor`
- **`KtorCore`**: Single Core file defining base URL, timeout durations, max retries, and logging.
- **`HttpClientFactory`**: Builds a single, shared `HttpClient` with JSON Content Negotiation, timeouts, and Bearer token refresh.
- **`ApiService` / `ApiServiceImpl`**: Generic typed contract for GET, POST, PUT, DELETE with automatic idempotent retries (POST is never auto-retried).
- **`HttpErrorMapper`**: Maps HTTP statuses and network exceptions to `AppError`.
- **`ktorModule`**: Koin module providing networking dependencies.

### 2.6 `network:firebase`
- **`FirebaseCore`**: Single Core file defining RTDB URL, Storage bucket, and offline persistence.
- **`FirestoreDataSource`**: Resilient Firestore CRUD and real-time observer. Drops malformed documents independently without failing batches. Cleanly detaches listeners on coroutine cancellation.
- **`RealtimeDbDataSource`**: Resilient Realtime Database tree/children data source.
- **`AuthDataSource` & `StorageDataSource`**: Authentication and Cloud Storage operations.
- **`QuerySpec`**: Framework-independent query description model.
- **`firebaseModule`**: Koin module providing Firebase data sources.

### 2.7 `local-db` (Room)
- **`DatabaseCore`**: Single Core file defining DB file name, version, and WAL mode.
- **`BaseDao<T>`**: Reusable CRUD DAO (`insert`, `insertAll`, `update`, `delete`).
- **`LocalDataSource<T>`**: Crash-safe wrapper executing writes in database transactions.
- **`DatabaseFactory` & `Converters`**: Type-safe Room builder and JSON converters.
- **`databaseModule`**: Koin module providing Room database and DAOs.

### 2.8 `app` (Demo Application)
- **`TemplateApplication`**: Initializes Koin with all module bindings.
- **`MainActivity`**: Hosts responsive tabs showcasing:
  1. **Theme**: Dynamic theme switching (Light/Dark/System) and token swatches.
  2. **Grid & UI**: Responsive `AppLazyGrid` scaling with four-state switcher and components.
  3. **Room DB**: Insert, observe, delete, and clear operations using Room.
  4. **Ktor API**: Live GET and POST API calls against public test endpoints.
  5. **Firebase**: Firestore collection querying, document insertion, and real-time observation.

---

## 3. How to Drop or Add Modules

To remove a feature module (e.g., `network:firebase`):
1. Remove `include(":network:firebase")` from `settings.gradle.kts`.
2. Remove `implementation(project(":network:firebase"))` from `app/build.gradle.kts`.
3. Remove `firebaseModule` from the `startKoin` block in `TemplateApplication.kt`.

Nothing else needs to change.

---

## 4. Checklist & Quality Verification

- [x] Module has no dependency on any sibling feature module (only on `core-common`).
- [x] Module has exactly one Core file holding every tunable constant.
- [x] All public entry points are interfaces; implementations are internal.
- [x] Every suspend call that can throw goes through the shared crash-safe primitive (`safeRun`).
- [x] Cancellation is always re-thrown, never wrapped as a failure.
- [x] Multi-item reads map each item independently and drop bad items instead of failing the whole batch.
- [x] Multi-item writes use a transaction/batch, not a loop of single writes.
- [x] Listeners clean up correctly when their observing stream is cancelled (`awaitClose`).
- [x] Module's Koin module is self-contained and only depends on `core-common`'s module.
- [x] Comprehensive READMEs written for root and each individual module.
