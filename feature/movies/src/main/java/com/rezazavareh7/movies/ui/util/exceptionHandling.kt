package com.rezazavareh7.movies.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rezazavareh7.movies.R
import java.net.ConnectException
import java.net.SocketException

@Composable
fun exceptionHandling(exceptionType: Throwable): String =
    when (exceptionType) {
        is SocketException ->
            when (exceptionType) {
                is ConnectException -> stringResource(R.string.error_connection)
                else -> stringResource(R.string.default_error)
            }

        else -> stringResource(R.string.default_error)
    }
