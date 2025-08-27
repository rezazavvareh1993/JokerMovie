package com.rezazavareh7.movies.domain.networkstate

import com.rezazavareh7.movies.domain.model.MediaData

sealed class GetMoviesNetworkState {
    data class Success(
        val data: List<MediaData>,
    ) : GetMoviesNetworkState()

    data class Error(
        val message: String,
    ) : GetMoviesNetworkState()
}
