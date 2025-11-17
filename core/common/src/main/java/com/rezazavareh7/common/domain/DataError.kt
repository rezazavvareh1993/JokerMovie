package com.rezazavareh7.common.domain

sealed interface DataError {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_UNAVAILABLE,
        SERIALIZATION,
        UNKNOWN,
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
    }

    enum class Local : DataError {
        NO_DATA,
        DISK_FULL,
        UNKNOWN,
    }
}
