package com.techquantum.template.network.firebase.firestore

import com.techquantum.template.common.result.AppResult
import com.techquantum.template.network.firebase.model.WriteResult
import kotlinx.coroutines.flow.Flow

data class BatchWriteOperation<T : Any>(
    val path: String,
    val data: T,
    val merge: Boolean = false,
)

interface FirestoreDataSource {
    suspend fun <T : Any> getDocument(path: String, clazz: Class<T>): AppResult<T>
    suspend fun <T : Any> getList(querySpec: QuerySpec, clazz: Class<T>): AppResult<List<T>>
    fun <T : Any> observeDocument(path: String, clazz: Class<T>): Flow<AppResult<T>>
    fun <T : Any> observeList(querySpec: QuerySpec, clazz: Class<T>): Flow<AppResult<List<T>>>
    suspend fun <T : Any> setDocument(path: String, data: T, merge: Boolean = false): AppResult<WriteResult>
    suspend fun updateDocument(path: String, fields: Map<String, Any>): AppResult<WriteResult>
    suspend fun deleteDocument(path: String): AppResult<Unit>
    suspend fun <T : Any> batchWrite(writes: List<BatchWriteOperation<T>>): AppResult<WriteResult>
}

suspend inline fun <reified T : Any> FirestoreDataSource.getDocument(path: String): AppResult<T> =
    getDocument(path, T::class.java)

suspend inline fun <reified T : Any> FirestoreDataSource.getList(querySpec: QuerySpec): AppResult<List<T>> =
    getList(querySpec, T::class.java)

inline fun <reified T : Any> FirestoreDataSource.observeDocument(path: String): Flow<AppResult<T>> =
    observeDocument(path, T::class.java)

inline fun <reified T : Any> FirestoreDataSource.observeList(querySpec: QuerySpec): Flow<AppResult<List<T>>> =
    observeList(querySpec, T::class.java)
