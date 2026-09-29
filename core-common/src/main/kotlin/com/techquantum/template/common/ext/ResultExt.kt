package com.techquantum.template.common.ext

import com.techquantum.template.common.result.AppError
import com.techquantum.template.common.result.AppResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The crash-safe suspend primitive.
 * Executes [block] on [dispatcher], re-throwing [CancellationException] to preserve
 * coroutine cancellation, while catching any other [Throwable] and wrapping it into an [AppResult.Failure].
 */
suspend inline fun <T> safeRun(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    noinline errorMapper: ((Throwable) -> AppError)? = null,
    crossinline block: suspend CoroutineScope.() -> T,
): AppResult<T> = withContext(dispatcher) {
    try {
        AppResult.Success(block())
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (throwable: Throwable) {
        val appError = errorMapper?.invoke(throwable)
            ?: AppError.Unknown(throwable.message ?: "Unknown error occurred", throwable)
        AppResult.Failure(appError)
    }
}

/**
 * Non-suspending crash-safe execution helper.
 */
inline fun <T> safeRunSync(
    noinline errorMapper: ((Throwable) -> AppError)? = null,
    block: () -> T,
): AppResult<T> {
    return try {
        AppResult.Success(block())
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (throwable: Throwable) {
        val appError = errorMapper?.invoke(throwable)
            ?: AppError.Unknown(throwable.message ?: "Unknown error occurred", throwable)
        AppResult.Failure(appError)
    }
}
