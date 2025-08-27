package com.rezazavareh7.movies.domain.networkstate

sealed class BasicNetworkState<out T> {
    data class Success<T>(val data: T) : BasicNetworkState<T>()

    data class Error(
        val message: String,
        val errorCode: Int? = null,
    ) : BasicNetworkState<Nothing>()
}
