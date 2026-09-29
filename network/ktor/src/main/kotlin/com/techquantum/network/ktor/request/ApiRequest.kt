package com.techquantum.network.ktor.request

data class ApiRequest(
    val path: String,
    val queryParams: Map<String, Any?> = emptyMap(),
    val headers: Map<String, String> = emptyMap(),
)
