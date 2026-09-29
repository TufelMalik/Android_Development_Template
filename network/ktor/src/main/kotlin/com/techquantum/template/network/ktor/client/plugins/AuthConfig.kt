package com.techquantum.template.network.ktor.client.plugins

import com.techquantum.template.network.ktor.auth.TokenProvider
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

fun HttpClientConfig<*>.configureAuth(tokenProvider: TokenProvider) {
    install(Auth) {
        bearer {
            loadTokens {
                val access = tokenProvider.getAccessToken()
                val refresh = tokenProvider.getRefreshToken()
                if (access != null) {
                    BearerTokens(accessToken = access, refreshToken = refresh ?: "")
                } else {
                    null
                }
            }
            refreshTokens {
                val refreshed = tokenProvider.refreshToken()
                if (refreshed) {
                    val access = tokenProvider.getAccessToken()
                    val refresh = tokenProvider.getRefreshToken()
                    if (access != null) {
                        BearerTokens(accessToken = access, refreshToken = refresh ?: "")
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        }
    }
}
