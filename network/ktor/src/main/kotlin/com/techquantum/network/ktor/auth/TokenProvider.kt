package com.techquantum.network.ktor.auth

interface TokenProvider {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun refreshToken(): Boolean
}

internal class NoOpTokenProvider : TokenProvider {
    override suspend fun getAccessToken(): String? = null
    override suspend fun getRefreshToken(): String? = null
    override suspend fun refreshToken(): Boolean = false
}
