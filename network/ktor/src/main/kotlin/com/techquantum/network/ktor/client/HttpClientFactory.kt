package com.techquantum.network.ktor.client

import com.techquantum.template.common.logger.AppLogger
import com.techquantum.network.ktor.auth.TokenProvider
import com.techquantum.network.ktor.client.plugins.configureAuth
import com.techquantum.network.ktor.client.plugins.configureLogging
import com.techquantum.network.ktor.client.plugins.configureTimeouts
import com.techquantum.network.ktor.core.KtorCore
import com.techquantum.network.ktor.serialization.JsonProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json

object HttpClientFactory {
    fun create(
        tokenProvider: TokenProvider,
        appLogger: AppLogger,
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    retryOnConnectionFailure(true)
                }
            }
            defaultRequest {
                url(KtorCore.defaultBaseUrl)
            }

            install(ContentNegotiation) {
                json(JsonProvider.json)
            }

            configureTimeouts()
            configureLogging(appLogger)
            configureAuth(tokenProvider)
        }
    }
}
