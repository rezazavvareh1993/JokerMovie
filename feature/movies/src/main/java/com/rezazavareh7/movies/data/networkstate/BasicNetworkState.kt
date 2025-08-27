package com.rezazavareh7.movies.data.networkstate

sealed class BasicNetworkState<out T> {
    data class Success<out T>(
        val data: T,
    ) : BasicNetworkState<T>()

    data class Error(
        val throwable: Throwable,
        val message: String,
        val errorCode: Int? = null,
    ) : BasicNetworkState<Nothing>()
}
