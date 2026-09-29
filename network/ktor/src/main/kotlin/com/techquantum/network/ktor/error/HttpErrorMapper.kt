package com.techquantum.network.ktor.error

import com.techquantum.template.common.result.AppError
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

object HttpErrorMapper {
    fun map(throwable: Throwable): AppError {
        if (throwable is CancellationException) {
            throw throwable
        }

        return when (throwable) {
            is ResponseException -> {
                mapStatusCode(
                    statusCode = throwable.response.status,
                    message = throwable.message ?: "HTTP ${throwable.response.status.value}",
                    cause = throwable,
                )
            }
            is HttpRequestTimeoutException,
            is ConnectTimeoutException,
            is SocketTimeoutException -> {
                AppError.Timeout(
                    message = throwable.message ?: "Connection timed out",
                    cause = throwable,
                )
            }
            is UnknownHostException -> {
                AppError.Network(
                    message = "No internet connection or server unreachable: ${throwable.message}",
                    cause = throwable,
                )
            }
            is ConnectException, is SocketException, is IOException -> {
                AppError.Network(
                    message = throwable.message ?: "Network I/O error occurred",
                    cause = throwable,
                )
            }
            is SerializationException -> {
                AppError.Serialization(
                    message = throwable.message ?: "Failed to parse network response",
                    cause = throwable,
                )
            }
            else -> {
                AppError.Unknown(
                    message = throwable.message ?: "An unexpected error occurred",
                    cause = throwable,
                )
            }
        }
    }

    fun mapStatusCode(
        statusCode: HttpStatusCode,
        message: String = "HTTP ${statusCode.value}",
        cause: Throwable? = null,
    ): AppError = when (statusCode) {
        HttpStatusCode.Unauthorized, HttpStatusCode.Forbidden -> {
            AppError.Auth(
                message = "Authentication failure: $message (${statusCode.value})",
                cause = cause,
            )
        }
        HttpStatusCode.NotFound -> {
            AppError.NotFound(
                message = "Resource not found: $message (${statusCode.value})",
                cause = cause,
            )
        }
        HttpStatusCode.RequestTimeout, HttpStatusCode.GatewayTimeout -> {
            AppError.Timeout(
                message = "Network timeout: $message (${statusCode.value})",
                cause = cause,
            )
        }
        else -> {
            if (statusCode.value in 500..599) {
                AppError.Network(
                    message = "Server error: $message (${statusCode.value})",
                    cause = cause,
                )
            } else {
                AppError.Unknown(
                    message = "HTTP error: $message (${statusCode.value})",
                    cause = cause,
                )
            }
        }
    }
}
