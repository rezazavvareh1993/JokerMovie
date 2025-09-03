package com.rezazavareh7.movies.domain.networkstate

sealed class SharePhotoState {
    data class Success(val absolutePath: String) : SharePhotoState()

    data class Error(val message: String) : SharePhotoState()
}
