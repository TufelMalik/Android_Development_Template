# network:ktor Module

Generic REST/HTTP networking engine built on Ktor with crash-safe execution, centralized error mapping, and idempotent retry handling.

## What it does
- **`KtorCore`**: Single Core file defining base URL, timeout durations, maximum retries, and logging toggles.
- **`HttpClientFactory`**: Builds a single, long-lived `HttpClient` configured with JSON Content Negotiation (`JsonProvider`), OkHttp engine, request timeouts, request logging, and Bearer token authentication.
- **`ApiService` / `ApiServiceImpl`**: Generic typed interface for `GET`, `POST`, `PUT`, `DELETE`.
  - Automatically executes requests inside `safeRun` on `Dispatchers.IO`.
  - Maps HTTP error codes and network failures into `AppError` via `HttpErrorMapper`.
  - Idempotent operations (`GET`, `PUT`, `DELETE`) retry automatically up to `KtorCore.maxRetries`.
  - `POST` requests are never auto-retried to protect against duplicate server mutations.
- **`TokenProvider`**: Decoupled contract for retrieving access/refresh tokens.
- **`ktorModule`**: Koin module providing `TokenProvider`, `HttpClient`, and `ApiService`.

## Safe-to-Delete Files
- To drop this module completely: remove `include(":network:ktor")` in `settings.gradle.kts`, remove `implementation(project(":network:ktor"))` from `app/build.gradle.kts`, and remove `ktorModule` from the app's `startKoin` block.

## Tunable Core Values (`KtorCore.kt`)
`network/ktor/src/main/kotlin/com/techquantum/template/network/ktor/core/KtorCore.kt`:
- `defaultBaseUrl`: Base API endpoint (default: "https://jsonplaceholder.typicode.com/").
- `connectTimeoutMs`, `requestTimeoutMs`, `socketTimeoutMs`: Network timeout thresholds (default: 15,000ms).
- `maxRetries`: Maximum automatic retries for idempotent calls (default: 3).
- `isLoggingEnabled`: HTTP request/response logging toggle (default: true).
