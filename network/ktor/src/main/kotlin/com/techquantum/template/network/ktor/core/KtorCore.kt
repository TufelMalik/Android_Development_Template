package com.techquantum.template.network.ktor.core

/**
 * KtorCore is the single Core file for the network:ktor module.
 * Holds the base URL, timeout thresholds, retry policy, and logging configurations.
 */
object KtorCore {
    var defaultBaseUrl: String = "https://jsonplaceholder.typicode.com/"
    const val connectTimeoutMs: Long = 15_000L
    const val requestTimeoutMs: Long = 15_000L
    const val socketTimeoutMs: Long = 15_000L
    const val maxRetries: Int = 3
    const val isLoggingEnabled: Boolean = true
}
