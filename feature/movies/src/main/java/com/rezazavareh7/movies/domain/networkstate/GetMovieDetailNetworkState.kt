package com.rezazavareh7.movies.domain.networkstate

import com.rezazavareh7.movies.domain.model.MovieDetailData

sealed class GetMovieDetailNetworkState {
    data class Success(val data: MovieDetailData) : GetMovieDetailNetworkState()

    data class Error(val message: String) : GetMovieDetailNetworkState()
}
