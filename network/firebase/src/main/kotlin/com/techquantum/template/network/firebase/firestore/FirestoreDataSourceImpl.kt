package com.techquantum.template.network.firebase.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
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

internal class FirestoreDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val dispatcherProvider: DispatcherProvider,
    private val appLogger: AppLogger,
) : FirestoreDataSource {

    override suspend fun <T : Any> getDocument(path: String, clazz: Class<T>): AppResult<T> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val snapshot = firestore.document(path).get().await()
        if (!snapshot.exists()) {
            throw NoSuchElementException("Document at $path does not exist")
        }
        snapshot.toObject(clazz) ?: throw IllegalStateException("Failed to deserialize document at $path to ${clazz.simpleName}")
    }

    override suspend fun <T : Any> getList(querySpec: QuerySpec, clazz: Class<T>): AppResult<List<T>> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val query = buildQuery(querySpec)
        val snapshot = query.get().await()
        mapSnapshotList(snapshot.documents, clazz)
    }

    override fun <T : Any> observeDocument(path: String, clazz: Class<T>): Flow<AppResult<T>> = callbackFlow {
        val docRef = firestore.document(path)
        val registration = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(AppResult.Failure(FirebaseErrorMapper.map(error)))
                return@addSnapshotListener
            }

            if (snapshot == null || !snapshot.exists()) {
                trySend(AppResult.Failure(AppError.NotFound("Document at $path not found")))
                return@addSnapshotListener
            }

            val item = snapshot.toObject(clazz)
            if (item != null) {
                trySend(AppResult.Success(item))
            } else {
                trySend(AppResult.Failure(AppError.Serialization("Failed to deserialize document at $path to ${clazz.simpleName}")))
            }
        }

        awaitClose {
            registration.remove()
        }
    }.flowOn(dispatcherProvider.io)

    override fun <T : Any> observeList(querySpec: QuerySpec, clazz: Class<T>): Flow<AppResult<List<T>>> = callbackFlow {
        val query = buildQuery(querySpec)
        val registration = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(AppResult.Failure(FirebaseErrorMapper.map(error)))
                return@addSnapshotListener
            }

            val documents = snapshot?.documents ?: emptyList()
            val list = mapSnapshotList(documents, clazz)
            trySend(AppResult.Success(list))
        }

        awaitClose {
            registration.remove()
        }
    }.flowOn(dispatcherProvider.io)

    override suspend fun <T : Any> setDocument(
        path: String,
        data: T,
        merge: Boolean,
    ): AppResult<WriteResult> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val docRef = firestore.document(path)
        if (merge) {
            docRef.set(data, SetOptions.merge()).await()
        } else {
            docRef.set(data).await()
        }
        WriteResult(id = docRef.id)
    }

    override suspend fun updateDocument(
        path: String,
        fields: Map<String, Any>,
    ): AppResult<WriteResult> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val docRef = firestore.document(path)
        docRef.update(fields).await()
        WriteResult(id = docRef.id)
    }

    override suspend fun deleteDocument(path: String): AppResult<Unit> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        firestore.document(path).delete().await()
    }

    override suspend fun <T : Any> batchWrite(
        writes: List<BatchWriteOperation<T>>,
    ): AppResult<WriteResult> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val batch = firestore.batch()
        for (op in writes) {
            val docRef = firestore.document(op.path)
            if (op.merge) {
                batch.set(docRef, op.data, SetOptions.merge())
            } else {
                batch.set(docRef, op.data)
            }
        }
        batch.commit().await()
        WriteResult()
    }

    private fun <T : Any> mapSnapshotList(
        documents: List<DocumentSnapshot>,
        clazz: Class<T>,
    ): List<T> {
        val results = ArrayList<T>(documents.size)
        for (doc in documents) {
            try {
                val item = doc.toObject(clazz)
                if (item != null) {
                    results.add(item)
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (throwable: Throwable) {
                appLogger.w(
                    "FirestoreDataSource",
                    "Dropped document '${doc.id}' due to parsing error: ${throwable.message}",
                    throwable,
                )
            }
        }
        return results
    }

    private fun buildQuery(spec: QuerySpec): Query {
        var query: Query = firestore.collection(spec.collectionPath)

        for (filter in spec.filters) {
            query = when (filter) {
                is FilterCondition.Equals -> query.whereEqualTo(filter.field, filter.value)
                is FilterCondition.GreaterThan -> query.whereGreaterThan(filter.field, filter.value)
                is FilterCondition.GreaterThanOrEqual -> query.whereGreaterThanOrEqualTo(filter.field, filter.value)
                is FilterCondition.LessThan -> query.whereLessThan(filter.field, filter.value)
                is FilterCondition.LessThanOrEqual -> query.whereLessThanOrEqualTo(filter.field, filter.value)
                is FilterCondition.InSet -> {
                    val chunked = filter.values.take(QuerySpec.FIRESTORE_IN_LIMIT)
                    query.whereIn(filter.field, chunked)
                }
            }
        }

        if (spec.orderBy != null) {
            val direction = if (spec.orderDirection == OrderDirection.ASCENDING) {
                Query.Direction.ASCENDING
            } else {
                Query.Direction.DESCENDING
            }
            query = query.orderBy(spec.orderBy, direction)
        }

        if (spec.cursor != null) {
            query = query.startAfter(spec.cursor)
        }

        if (spec.limit != null && spec.limit > 0) {
            query = query.limit(spec.limit)
        }

        return query
    }
}
