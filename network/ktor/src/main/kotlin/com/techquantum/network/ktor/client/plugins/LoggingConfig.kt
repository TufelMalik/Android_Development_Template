package com.techquantum.network.ktor.client.plugins

import com.techquantum.template.common.logger.AppLogger
import com.techquantum.network.ktor.core.KtorCore
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

fun HttpClientConfig<*>.configureLogging(appLogger: AppLogger) {
    if (KtorCore.isLoggingEnabled) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    appLogger.d("KtorClient", message)
                }
            }
            level = LogLevel.INFO
        }
    }
}
