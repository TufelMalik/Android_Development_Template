package com.techquantum.template.localdb.error

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.techquantum.template.common.result.AppError
import kotlinx.coroutines.CancellationException

object DbErrorMapper {
    fun map(throwable: Throwable): AppError {
        if (throwable is CancellationException) {
            throw throwable
        }

        return when (throwable) {
            is NoSuchElementException -> AppError.NotFound(
                message = throwable.message ?: "Entity not found in database",
                cause = throwable,
            )
            is SQLiteConstraintException -> AppError.Database(
                message = "Database constraint violation: ${throwable.message}",
                cause = throwable,
            )
            is SQLiteFullException, is SQLiteDiskIOException -> AppError.Database(
                message = "Disk or database storage error: ${throwable.message}",
                cause = throwable,
            )
            is SQLiteDatabaseCorruptException -> AppError.Database(
                message = "Database corruption error: ${throwable.message}",
                cause = throwable,
            )
            is SQLiteException -> AppError.Database(
                message = throwable.message ?: "SQLite database error",
                cause = throwable,
            )
            else -> AppError.Database(
                message = throwable.message ?: "An unexpected database error occurred",
                cause = throwable,
            )
        }
    }
}
