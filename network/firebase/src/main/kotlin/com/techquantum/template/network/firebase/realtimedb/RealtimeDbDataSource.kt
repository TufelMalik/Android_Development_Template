package com.techquantum.template.network.firebase.realtimedb

import com.techquantum.template.common.result.AppResult
import com.techquantum.template.network.firebase.model.WriteResult
import kotlinx.coroutines.flow.Flow

interface RealtimeDbDataSource {
    suspend fun <T : Any> getValue(path: String, clazz: Class<T>): AppResult<T>
    suspend fun <T : Any> getChildren(path: String, clazz: Class<T>): AppResult<List<T>>
    fun <T : Any> observeValue(path: String, clazz: Class<T>): Flow<AppResult<T>>
    fun <T : Any> observeChildren(path: String, clazz: Class<T>): Flow<AppResult<List<T>>>
    suspend fun <T : Any> setValue(path: String, data: T): AppResult<WriteResult>
    suspend fun updateChildren(path: String, updates: Map<String, Any?>): AppResult<WriteResult>
    suspend fun removeValue(path: String): AppResult<Unit>
}

suspend inline fun <reified T : Any> RealtimeDbDataSource.getValue(path: String): AppResult<T> =
    getValue(path, T::class.java)

suspend inline fun <reified T : Any> RealtimeDbDataSource.getChildren(path: String): AppResult<List<T>> =
    getChildren(path, T::class.java)

inline fun <reified T : Any> RealtimeDbDataSource.observeValue(path: String): Flow<AppResult<T>> =
    observeValue(path, T::class.java)

inline fun <reified T : Any> RealtimeDbDataSource.observeChildren(path: String): Flow<AppResult<List<T>>> =
    observeChildren(path, T::class.java)
