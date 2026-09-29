package com.techquantum.template.network.ktor.response

import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<T>(
    val items: List<T> = emptyList(),
    val page: Int = 1,
    val totalPages: Int = 1,
    val totalItems: Int = 0,
)
