package com.techquantum.template.network.ktor.api

import com.techquantum.template.common.dispatcher.DispatcherProvider
import com.techquantum.template.common.ext.safeRun
import com.techquantum.template.common.logger.AppLogger
import com.techquantum.template.common.result.AppResult
import com.techquantum.template.network.ktor.core.KtorCore
import com.techquantum.template.network.ktor.error.HttpErrorMapper
import com.techquantum.template.network.ktor.request.ApiRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

internal class ApiServiceImpl(
    private val client: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val appLogger: AppLogger,
) : ApiService {

    override suspend fun <T> get(request: ApiRequest, typeInfo: TypeInfo): AppResult<T> =
        executeWithRetry(isIdempotent = true) {
            executeRequest(HttpMethod.Get, request, body = null, typeInfo = typeInfo)
        }

    override suspend fun <T> post(request: ApiRequest, body: Any?, typeInfo: TypeInfo): AppResult<T> =
        executeWithRetry(isIdempotent = false) {
            executeRequest(HttpMethod.Post, request, body = body, typeInfo = typeInfo)
        }

    override suspend fun <T> put(request: ApiRequest, body: Any?, typeInfo: TypeInfo): AppResult<T> =
        executeWithRetry(isIdempotent = true) {
            executeRequest(HttpMethod.Put, request, body = body, typeInfo = typeInfo)
        }

    override suspend fun <T> delete(request: ApiRequest, typeInfo: TypeInfo): AppResult<T> =
        executeWithRetry(isIdempotent = true) {
            executeRequest(HttpMethod.Delete, request, body = null, typeInfo = typeInfo)
        }

    private suspend fun <T> executeRequest(
        method: HttpMethod,
        request: ApiRequest,
        body: Any?,
        typeInfo: TypeInfo,
    ): T {
        val httpRequestBuilder = HttpRequestBuilder().apply {
            this.method = method
            url(request.path)
            request.queryParams.forEach { (key, value) ->
                if (value != null) {
                    parameter(key, value)
                }
            }
            headers {
                request.headers.forEach { (key, value) ->
                    append(key, value)
                }
            }
            if (body != null) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }

        val response: HttpResponse = client.request(httpRequestBuilder)

        if (response.status.value >= 400) {
            throw ResponseException(response, "HTTP error ${response.status.value}: ${response.status.description}")
        }

        @Suppress("UNCHECKED_CAST")
        return response.body(typeInfo) as T
    }

    private suspend fun <T> executeWithRetry(
        isIdempotent: Boolean,
        block: suspend () -> T,
    ): AppResult<T> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = HttpErrorMapper::map,
    ) {
        val maxAttempts = if (isIdempotent) KtorCore.maxRetries else 1
        var lastThrowable: Throwable? = null

        for (attempt in 1..maxAttempts) {
            try {
                return@safeRun block()
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (throwable: Throwable) {
                lastThrowable = throwable
                if (!isIdempotent || attempt >= maxAttempts) {
                    throw throwable
                }
                appLogger.w("ApiService", "Request attempt $attempt failed, retrying...", throwable)
                delay(100L * attempt)
            }
        }

        throw lastThrowable ?: IllegalStateException("Request execution exhausted without result")
    }
}
