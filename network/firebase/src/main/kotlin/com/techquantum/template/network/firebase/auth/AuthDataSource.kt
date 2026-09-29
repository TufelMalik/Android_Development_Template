package com.techquantum.template.network.firebase.auth

import com.techquantum.template.common.result.AppResult
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun signInWithEmailAndPassword(email: String, password: String): AppResult<String>
    suspend fun createUserWithEmailAndPassword(email: String, password: String): AppResult<String>
    suspend fun signOut(): AppResult<Unit>
    fun getCurrentUserId(): String?
    fun observeAuthState(): Flow<String?>
}
