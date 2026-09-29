# network:firebase Module

Firebase integrations covering Cloud Firestore, Realtime Database, Firebase Authentication, and Cloud Storage.

## What it does
- **`FirebaseCore`**: Single Core file defining the Realtime Database URL, default pagination size, offline persistence flag, and Storage bucket location.
- **`FirestoreDataSource` / `FirestoreDataSourceImpl`**: Generic Cloud Firestore CRUD and real-time observer.
  - Multi-item queries deserialize documents independently and drop malformed documents without failing the entire stream or batch.
  - Snapshot listeners automatically detach on coroutine cancellation (`awaitClose { registration.remove() }`), preventing memory and connection leaks.
  - Multi-document writes run atomically using `firestore.batch()`.
- **`RealtimeDbDataSource` / `RealtimeDbDataSourceImpl`**: Firebase Realtime Database CRUD with identical child-drop resilience and leak-free stream listeners.
- **`AuthDataSource` / `AuthDataSourceImpl`**: Email/password authentication and reactive auth state observation.
- **`StorageDataSource` / `StorageDataSourceImpl`**: File upload, download, URL retrieval, and deletion in Firebase Storage.
- **`QuerySpec`**: Framework-independent query description model supporting equality, inequality, ordering, limits, and automatic chunking for `in` queries.
- **`FirebaseErrorMapper`**: Maps Firebase SDK exceptions into normalized domain `AppError` categories.
- **`firebaseModule`**: Koin module providing Firebase service instances and data source bindings.

## Safe-to-Delete Files
- To drop this module completely: remove `include(":network:firebase")` from `settings.gradle.kts`, remove `implementation(project(":network:firebase"))` from `app/build.gradle.kts`, and remove `firebaseModule` from the app's `startKoin` block.

## Tunable Core Values (`FirebaseCore.kt`)
`network/firebase/src/main/kotlin/com/techquantum/template/network/firebase/core/FirebaseCore.kt`:
- `realtimeDbUrl`: Firebase Realtime Database URL.
- `storageBucket`: Google Cloud Storage bucket URL for Firebase Storage.
- `defaultPageSize`: Default query limit for paginated data sets (default: 20).
- `isOfflinePersistenceEnabled`: Whether offline cache persistence is enabled (default: true).
