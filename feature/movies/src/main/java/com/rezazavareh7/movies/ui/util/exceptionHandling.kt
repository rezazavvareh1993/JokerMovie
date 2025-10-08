package com.rezazavareh7.movies.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rezazavareh7.movies.R
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

@Composable
fun exceptionHandling(exceptionType: Throwable): String =
    when (exceptionType) {
        is SocketException ->
            when (exceptionType) {
                is ConnectException -> stringResource(R.string.vpn_error_connection)
                else -> stringResource(R.string.default_error)
            }

        is IOException -> {
            when (exceptionType) {
                is UnknownHostException -> stringResource(R.string.internet_error_connection)
                else -> stringResource(R.string.default_error)
            }
        }

        else -> stringResource(R.string.default_error)
    }
