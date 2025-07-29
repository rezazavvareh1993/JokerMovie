package com.rezazavareh7.movies.domain

import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import com.rezazavareh7.movies.domain.networkstate.GetMoviesNetworkState

interface MoviesRepository {
//    suspend fun getMovies(): GetMoviesNetworkState

    suspend fun searchMovie(query: String): GetMoviesNetworkState

    suspend fun getMovieDetail(movieId: Long): GetMovieDetailNetworkState
}
