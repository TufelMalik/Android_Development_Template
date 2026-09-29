package com.techquantum.template.network.firebase.storage

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.techquantum.template.common.dispatcher.DispatcherProvider
import com.techquantum.template.common.ext.safeRun
import com.techquantum.template.common.result.AppResult
import com.techquantum.template.network.firebase.error.FirebaseErrorMapper
import kotlinx.coroutines.tasks.await

internal class StorageDataSourceImpl(
    private val storage: FirebaseStorage,
    private val dispatcherProvider: DispatcherProvider,
) : StorageDataSource {

    override suspend fun uploadBytes(
        path: String,
        bytes: ByteArray,
        contentType: String?,
    ): AppResult<String> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val ref = storage.reference.child(path)
        if (contentType != null) {
            val metadata = StorageMetadata.Builder()
                .setContentType(contentType)
                .build()
            ref.putBytes(bytes, metadata).await()
        } else {
            ref.putBytes(bytes).await()
        }
        ref.downloadUrl.await().toString()
    }

    override suspend fun downloadBytes(
        path: String,
        maxDownloadSizeBytes: Long,
    ): AppResult<ByteArray> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val ref = storage.reference.child(path)
        ref.getBytes(maxDownloadSizeBytes).await()
    }

    override suspend fun deleteFile(path: String): AppResult<Unit> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val ref = storage.reference.child(path)
        ref.delete().await()
    }

    override suspend fun getDownloadUrl(path: String): AppResult<String> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val ref = storage.reference.child(path)
        ref.downloadUrl.await().toString()
    }
}
