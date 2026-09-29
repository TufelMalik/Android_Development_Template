package com.techquantum.template.network.ktor.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorEnvelope(
    val code: String? = null,
    val message: String? = null,
    val details: String? = null,
)
