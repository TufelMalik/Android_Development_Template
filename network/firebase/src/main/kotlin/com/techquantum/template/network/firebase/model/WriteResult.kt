package com.techquantum.template.network.firebase.model

data class WriteResult(
    val id: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)
