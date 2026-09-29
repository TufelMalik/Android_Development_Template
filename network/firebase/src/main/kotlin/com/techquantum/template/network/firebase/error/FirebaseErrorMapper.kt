package com.techquantum.template.network.firebase.error

import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.techquantum.template.common.result.AppError
import kotlinx.coroutines.CancellationException

object FirebaseErrorMapper {
    fun map(throwable: Throwable): AppError {
        if (throwable is CancellationException) {
            throw throwable
        }

        return when (throwable) {
            is FirebaseAuthException -> {
                AppError.Auth(
                    message = throwable.message ?: "Authentication failed: ${throwable.errorCode}",
                    cause = throwable,
                )
            }
            is FirebaseFirestoreException -> {
                when (throwable.code) {
                    FirebaseFirestoreException.Code.NOT_FOUND -> {
                        AppError.NotFound(
                            message = throwable.message ?: "Document not found",
                            cause = throwable,
                        )
                    }
                    FirebaseFirestoreException.Code.PERMISSION_DENIED,
                    FirebaseFirestoreException.Code.UNAUTHENTICATED -> {
                        AppError.Auth(
                            message = throwable.message ?: "Permission denied or unauthenticated",
                            cause = throwable,
                        )
                    }
                    FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> {
                        AppError.Timeout(
                            message = throwable.message ?: "Firestore operation timed out",
                            cause = throwable,
                        )
                    }
                    FirebaseFirestoreException.Code.UNAVAILABLE -> {
                        AppError.Network(
                            message = throwable.message ?: "Firestore service currently unavailable",
                            cause = throwable,
                        )
                    }
                    else -> {
                        AppError.Database(
                            message = throwable.message ?: "Firestore database error: ${throwable.code}",
                            cause = throwable,
                        )
                    }
                }
            }
            is FirebaseNetworkException -> {
                AppError.Network(
                    message = throwable.message ?: "Firebase network connection error",
                    cause = throwable,
                )
            }
            is FirebaseTooManyRequestsException -> {
                AppError.Network(
                    message = throwable.message ?: "Too many requests to Firebase. Please try again later.",
                    cause = throwable,
                )
            }
            is FirebaseApiNotAvailableException -> {
                AppError.Network(
                    message = throwable.message ?: "Firebase API not available on this device",
                    cause = throwable,
                )
            }
            is FirebaseException -> {
                AppError.Unknown(
                    message = throwable.message ?: "Firebase SDK error",
                    cause = throwable,
                )
            }
            else -> {
                AppError.Unknown(
                    message = throwable.message ?: "An unexpected error occurred",
                    cause = throwable,
                )
            }
        }
    }
}
