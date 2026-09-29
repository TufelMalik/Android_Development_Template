package com.techquantum.network.ktor.client.plugins

import com.techquantum.network.ktor.core.KtorCore
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout

fun HttpClientConfig<*>.configureTimeouts() {
    install(HttpTimeout) {
        requestTimeoutMillis = KtorCore.requestTimeoutMs
        connectTimeoutMillis = KtorCore.connectTimeoutMs
        socketTimeoutMillis = KtorCore.socketTimeoutMs
    }
}
