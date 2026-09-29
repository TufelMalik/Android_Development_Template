package com.techquantum.template.network.ktor.client

import com.techquantum.template.common.logger.AppLogger
import com.techquantum.template.network.ktor.auth.TokenProvider
import com.techquantum.template.network.ktor.client.plugins.configureAuth
import com.techquantum.template.network.ktor.client.plugins.configureLogging
import com.techquantum.template.network.ktor.client.plugins.configureTimeouts
import com.techquantum.template.network.ktor.core.KtorCore
import com.techquantum.template.network.ktor.serialization.JsonProvider
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
