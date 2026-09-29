package com.techquantum.template.network.ktor.serialization

import kotlinx.serialization.json.Json

/**
 * JsonProvider holds the unified lenient Json configuration.
 * Ignores unknown keys, coerces invalid inputs, and never breaks when backend schemas expand.
 */
object JsonProvider {
    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        encodeDefaults = true
        prettyPrint = false
    }
}
