package com.rezazavareh7.common.domain

sealed interface Result<out D, out E : DataError> {
    data class Success<out D>(
        val data: D,
    ) : Result<D, Nothing>

    data class Error<out E : DataError>(
        val error: E,
    ) : Result<Nothing, E>
}

inline fun <T, E : DataError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> =
    when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }

fun <T, E : DataError> Result<T, E>.asEmptyDataResult(): EmptyResult<E> = map { }

inline fun <T, E : DataError, F> Result<T, E>.handleResult(
    onSuccessAction: (T) -> F,
    onErrorAction: (E) -> F,
): F =
    when (this) {
        is Result.Error -> onErrorAction(error)
        is Result.Success -> onSuccessAction(data)
    }

typealias EmptyResult<E> = Result<Unit, E>
