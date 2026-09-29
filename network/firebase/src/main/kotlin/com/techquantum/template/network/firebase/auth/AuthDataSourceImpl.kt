package com.techquantum.template.network.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.techquantum.template.common.dispatcher.DispatcherProvider
import com.techquantum.template.common.ext.safeRun
import com.techquantum.template.common.result.AppResult
import com.techquantum.template.network.firebase.error.FirebaseErrorMapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

internal class AuthDataSourceImpl(
    private val auth: FirebaseAuth,
    private val dispatcherProvider: DispatcherProvider,
) : AuthDataSource {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): AppResult<String> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user?.uid ?: throw IllegalStateException("Signed in user UID is null")
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): AppResult<String> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user?.uid ?: throw IllegalStateException("Created user UID is null")
    }

    override suspend fun signOut(): AppResult<Unit> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = FirebaseErrorMapper::map,
    ) {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? = auth.currentUser?.uid

    override fun observeAuthState(): Flow<String?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.uid)
        }
        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }.flowOn(dispatcherProvider.io)
}
