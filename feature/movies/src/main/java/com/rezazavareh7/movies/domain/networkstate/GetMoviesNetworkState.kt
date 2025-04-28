package com.rezazavareh7.movies.domain.networkstate

import com.rezazavareh7.movies.domain.model.MovieData

sealed class GetMoviesNetworkState {
    data class Success(val data: List<MovieData>) : GetMoviesNetworkState()

    data class Error(val message: String) : GetMoviesNetworkState()
}
