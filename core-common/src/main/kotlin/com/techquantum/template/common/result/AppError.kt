package com.techquantum.template.common.result

sealed interface AppError {
    val message: String
    val cause: Throwable?

    data class Network(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError

    data class Timeout(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError

    data class Serialization(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError

    data class NotFound(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError

    data class Database(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError

    data class Auth(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError

    data class Unknown(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError
}
