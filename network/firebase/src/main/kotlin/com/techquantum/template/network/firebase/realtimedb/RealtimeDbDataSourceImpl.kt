package com.techquantum.template.network.firebase.realtimedb

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.techquantum.template.common.dispatcher.DispatcherProvider
import com.techquantum.template.common.ext.safeRun
import com.techquantum.template.common.logger.AppLogger
import com.techquantum.template.common.result.AppError
import com.techquantum.template.common.result.AppResult
import com.techquantum.template.network.firebase.error.FirebaseErrorMapper
import com.techquantum.template.network.firebase.model.WriteResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

internal class RealtimeDbDataSourceImpl(
    private val database: FirebaseDatabase,
    private val dispatcherProvider: DispatcherProvider,
    private val appLogger: AppLogger,
) : RealtimeDbDataSource {

    override suspend fun <T : Any> getValue(path: String, clazz: Class<T>): AppResult<T> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val snapshot = database.getReference(path).get().await()
        if (!snapshot.exists()) {
            throw NoSuchElementException("Value at path '$path' does not exist")
        }
        snapshot.getValue(clazz) ?: throw IllegalStateException("Failed to deserialize value at '$path' to ${clazz.simpleName}")
    }

    override suspend fun <T : Any> getChildren(path: String, clazz: Class<T>): AppResult<List<T>> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val snapshot = database.getReference(path).get().await()
        mapSnapshotChildren(snapshot, clazz)
    }

    override fun <T : Any> observeValue(path: String, clazz: Class<T>): Flow<AppResult<T>> = callbackFlow {
        val ref = database.getReference(path)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    trySend(AppResult.Failure(AppError.NotFound("Value at path '$path' does not exist")))
                    return
                }
                val value = snapshot.getValue(clazz)
                if (value != null) {
                    trySend(AppResult.Success(value))
                } else {
                    trySend(AppResult.Failure(AppError.Serialization("Failed to deserialize value at '$path' to ${clazz.simpleName}")))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(AppResult.Failure(FirebaseErrorMapper.map(error.toException())))
            }
        }

        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }.flowOn(dispatcherProvider.io)

    override fun <T : Any> observeChildren(path: String, clazz: Class<T>): Flow<AppResult<List<T>>> = callbackFlow {
        val ref = database.getReference(path)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mapSnapshotChildren(snapshot, clazz)
                trySend(AppResult.Success(list))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(AppResult.Failure(FirebaseErrorMapper.map(error.toException())))
            }
        }

        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }.flowOn(dispatcherProvider.io)

    override suspend fun <T : Any> setValue(path: String, data: T): AppResult<WriteResult> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val ref = database.getReference(path)
        ref.setValue(data).await()
        WriteResult(id = ref.key)
    }

    override suspend fun updateChildren(
        path: String,
        updates: Map<String, Any?>,
    ): AppResult<WriteResult> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val ref = database.getReference(path)
        ref.updateChildren(updates).await()
        WriteResult(id = ref.key)
    }

    override suspend fun removeValue(path: String): AppResult<Unit> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        database.getReference(path).removeValue().await()
    }

    private fun <T : Any> mapSnapshotChildren(
        snapshot: DataSnapshot,
        clazz: Class<T>,
    ): List<T> {
        val results = mutableListOf<T>()
        for (child in snapshot.children) {
            try {
                val item = child.getValue(clazz)
                if (item != null) {
                    results.add(item)
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (throwable: Throwable) {
                appLogger.w(
                    "RealtimeDbDataSource",
                    "Dropped child '${child.key}' due to parsing error: ${throwable.message}",
                    throwable,
                )
            }
        }
        return results
    }
}
