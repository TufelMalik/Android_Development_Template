package com.techquantum.template.network.ktor.di

import com.techquantum.template.network.ktor.api.ApiService
import com.techquantum.template.network.ktor.api.ApiServiceImpl
import com.techquantum.template.network.ktor.auth.NoOpTokenProvider
import com.techquantum.template.network.ktor.auth.TokenProvider
import com.techquantum.template.network.ktor.client.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module

val ktorModule = module {
    single<TokenProvider> {
        NoOpTokenProvider()
    }

    single<HttpClient> {
        HttpClientFactory.create(
            tokenProvider = get(),
            appLogger = get(),
        )
    }

    single<ApiService> {
        ApiServiceImpl(
            client = get(),
            dispatcherProvider = get(),
            appLogger = get(),
        )
    }
}
