package com.techquantum.network.ktor.api

import com.techquantum.template.common.result.AppResult
import com.techquantum.network.ktor.request.ApiRequest
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

interface ApiService {
    suspend fun <T> get(request: ApiRequest, typeInfo: TypeInfo): AppResult<T>
    suspend fun <T> post(request: ApiRequest, body: Any? = null, typeInfo: TypeInfo): AppResult<T>
    suspend fun <T> put(request: ApiRequest, body: Any? = null, typeInfo: TypeInfo): AppResult<T>
    suspend fun <T> delete(request: ApiRequest, typeInfo: TypeInfo): AppResult<T>
}

suspend inline fun <reified T> ApiService.get(request: ApiRequest): AppResult<T> =
    get(request, typeInfo<T>())

suspend inline fun <reified T> ApiService.post(request: ApiRequest, body: Any? = null): AppResult<T> =
    post(request, body, typeInfo<T>())

suspend inline fun <reified T> ApiService.put(request: ApiRequest, body: Any? = null): AppResult<T> =
    put(request, body, typeInfo<T>())

suspend inline fun <reified T> ApiService.delete(request: ApiRequest): AppResult<T> =
    delete(request, typeInfo<T>())
