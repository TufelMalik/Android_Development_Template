package com.techquantum.template.common.ext

import com.techquantum.template.common.result.AppError
import com.techquantum.template.common.result.AppResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Transforms a Flow<T> into Flow<AppResult<T>>, catching any non-cancellation exception
 * and emitting it as an AppResult.Failure.
 */
fun <T> Flow<T>.asResult(
    errorMapper: ((Throwable) -> AppError)? = null,
): Flow<AppResult<T>> = this
    .map<T, AppResult<T>> { AppResult.Success(it) }
    .catch { cause ->
        if (cause is CancellationException) {
            throw cause
        }
        val appError = errorMapper?.invoke(cause)
            ?: AppError.Unknown(cause.message ?: "Unknown error occurred", cause)
        emit(AppResult.Failure(appError))
    }
