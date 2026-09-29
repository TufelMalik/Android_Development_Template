package com.techquantum.template.network.firebase.storage

import com.techquantum.template.common.result.AppResult

interface StorageDataSource {
    suspend fun uploadBytes(path: String, bytes: ByteArray, contentType: String? = null): AppResult<String>
    suspend fun downloadBytes(path: String, maxDownloadSizeBytes: Long = 10 * 1024 * 1024): AppResult<ByteArray>
    suspend fun deleteFile(path: String): AppResult<Unit>
    suspend fun getDownloadUrl(path: String): AppResult<String>
}
