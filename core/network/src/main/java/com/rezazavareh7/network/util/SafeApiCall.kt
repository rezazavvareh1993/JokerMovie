package com.rezazavareh7.network.util

import com.rezazavareh7.common.domain.DataError
import com.rezazavareh7.common.util.extensions.timberLog
import kotlinx.coroutines.ensureActive
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext
import com.rezazavareh7.common.domain.Result as DomainResult
import kotlin.Result as KotlinResult

suspend inline fun <reified T> safeApiCall(execute: () -> KotlinResult<T>): DomainResult<T, DataError.Remote> {
    val response =
        try {
            execute()
        } catch (e: SocketTimeoutException) {
            e.timberLog()
            return DomainResult.Error(DataError.Remote.REQUEST_TIMEOUT)
        } catch (e: UnresolvedAddressException) {
            e.timberLog()
            return DomainResult.Error(DataError.Remote.NO_INTERNET)
        } catch (e: Exception) {
            e.timberLog()
            coroutineContext.ensureActive()
            return DomainResult.Error(DataError.Remote.UNKNOWN)
        }
    return responseToDomainResult(response)
}

suspend inline fun <reified T> responseToDomainResult(response: KotlinResult<T>): DomainResult<T, DataError.Remote> =
    response.fold(
        onSuccess = {
            DomainResult.Success(it)
        },
        onFailure = {
            it.timberLog()
            when (it) {
                is HttpException -> {
                    when (it.response()?.code()) {
                        in 200..299 -> DomainResult.Error(DataError.Remote.SERIALIZATION)
                        400 -> DomainResult.Error(DataError.Remote.BAD_REQUEST)
                        401 -> DomainResult.Error(DataError.Remote.UNAUTHORIZED)
                        403 -> DomainResult.Error(DataError.Remote.FORBIDDEN)
                        404 -> DomainResult.Error(DataError.Remote.NOT_FOUND)
                        408 -> DomainResult.Error(DataError.Remote.REQUEST_TIMEOUT)
                        429 -> DomainResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
                        in 500..599 -> DomainResult.Error(DataError.Remote.SERVER_UNAVAILABLE)
                        else -> DomainResult.Error(DataError.Remote.UNKNOWN)
                    }
                }

                is SocketTimeoutException -> DomainResult.Error(DataError.Remote.REQUEST_TIMEOUT)
                is UnresolvedAddressException -> DomainResult.Error(DataError.Remote.NO_INTERNET)
                is UnknownHostException -> DomainResult.Error(DataError.Remote.NO_INTERNET)
                else -> DomainResult.Error(DataError.Remote.UNKNOWN)
            }
        },
    )
