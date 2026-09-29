# core-common Module

Framework-agnostic, project-agnostic primitives every other module builds on.

## What it does
- **`AppResult<T>`**: Two-branch outcome type (`Success` / `Failure`) with monadic transform (`map`) and side-effect helpers (`onSuccess`, `onFailure`, `fold`).
- **`AppError`**: Closed set of normalized domain error categories (`Network`, `Timeout`, `Serialization`, `NotFound`, `Database`, `Auth`, `Unknown`).
- **Crash-safe Primitive (`safeRun`)**: Central suspend runner that executes code on a specified coroutine dispatcher, catches exceptions, preserves `CancellationException`, and produces typed `AppResult.Failure`.
- **`DispatcherProvider`**: Injected dispatcher abstraction for testability.
- **`AppLogger`**: Unified logging interface.
- **`Mapper<From, To>`**: Object transformation contract.
- **`commonModule`**: Koin module providing `DispatcherProvider` and `AppLogger`.

## Safe-to-Delete Files
`core-common` is the root dependency for all modules in the repository. Do not delete files from this module unless removing corresponding features across all downstream modules.

## Tunable Core Values
`core-common` is intentionally lean and provides default implementations (`DefaultDispatcherProvider`, `DefaultAppLogger`). To customize dispatchers or logging across the entire app, bind alternative implementations in `commonModule` or override bindings in test suites.
