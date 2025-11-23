package com.rezazavareh7.movies.ui.util

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import com.rezazavareh7.common.domain.DataError
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

@Composable
fun LoadState.Error.toDataError(): DataError {
    val pagingError: Throwable = this.error
    return when (pagingError) {
        is SocketException -> pagingError.handleSocketExceptions()
        is IOException -> pagingError.handleIOExceptions()
        else -> DataError.Remote.UNKNOWN
    }
}

private fun SocketException.handleSocketExceptions(): DataError =
    when (this) {
        is ConnectException -> DataError.Remote.NO_INTERNET
        else -> DataError.Remote.UNKNOWN
    }

private fun IOException.handleIOExceptions(): DataError =
    when (this) {
        is UnknownHostException -> DataError.Remote.NOT_FOUND
        else -> DataError.Remote.UNKNOWN
    }
