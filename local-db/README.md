# local-db Module (Room)

Room persistence layer with generic CRUD abstraction and crash-safe transactional execution.

## What it does
- **`DatabaseCore`**: Single Core file defining database file name, schema version, WAL journal mode, and migration options.
- **`BaseDao<T>`**: Reusable CRUD DAO interface providing `insert`, `insertAll`, `update`, and `delete`.
- **`LocalDataSource<T>`**: Crash-safe repository interface that wraps DAO operations in coroutines on `Dispatchers.IO`, performs multi-item inserts in an atomic transaction (`database.withTransaction`), and maps errors through `DbErrorMapper`.
- **`DatabaseFactory`**: Builds the `RoomDatabase` instance based on `DatabaseCore` settings and provided migrations.
- **`Converters`**: Type converters for Dates, Lists, and Maps using `kotlinx.serialization`.
- **`AppDatabase` / `SampleEntity` / `SampleDao`**: Example database implementation showing per-project setup.
- **`databaseModule`**: Koin module providing the database instance, DAOs, and data source wrappers.

## Safe-to-Delete Files
- When adapting for a real project, replace `SampleEntity.kt` and `SampleDao.kt` with your project's specific entities and DAOs.
- Update `AppDatabase.kt` to list your entities and declare DAO accessors.
- To drop this module completely: remove `include(":local-db")` in `settings.gradle.kts`, remove `implementation(project(":local-db"))` in `app/build.gradle.kts`, and remove `databaseModule` from Koin initialization.

## Tunable Core Values (`DatabaseCore.kt`)
`local-db/src/main/kotlin/com/techquantum/template/localdb/core/DatabaseCore.kt`:
- `databaseName`: SQLite database file name (default: "app_database.db").
- `databaseVersion`: Current database schema version.
- `exportSchema`: Whether to export Room schema json (default: false).
- `enableWal`: Write-Ahead Logging mode (default: true).
- `fallbackToDestructiveMigration`: Wipe and recreate fallback (disabled for production safety).
