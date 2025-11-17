package com.rezazavareh7.ui.util

import com.rezazavareh7.common.domain.DataError
import com.rezazavareh7.designsystem.R

fun DataError?.toUiText(): UiText? =
    this?.let { dataError ->
        val resourceId =
            when (dataError) {
                DataError.Remote.REQUEST_TIMEOUT -> R.string.request_timeout_error
                DataError.Remote.TOO_MANY_REQUESTS -> R.string.too_many_request_error
                DataError.Remote.NO_INTERNET -> R.string.no_internet_error
                DataError.Remote.SERVER_UNAVAILABLE -> R.string.service_unavailable_error
                DataError.Remote.SERIALIZATION -> R.string.serialization_error
                DataError.Remote.UNKNOWN -> R.string.unknown_error
                DataError.Remote.BAD_REQUEST -> R.string.bad_request_error
                DataError.Remote.UNAUTHORIZED -> R.string.unauthorized_error
                DataError.Remote.FORBIDDEN -> R.string.forbidden_error
                DataError.Remote.NOT_FOUND -> R.string.not_found_error
                DataError.Local.NO_DATA -> R.string.no_data_error
                DataError.Local.DISK_FULL -> R.string.disk_full_error
                DataError.Local.UNKNOWN -> R.string.unknown_error
            }
        UiText.StringResourceId(resourceId)
    } ?: null
