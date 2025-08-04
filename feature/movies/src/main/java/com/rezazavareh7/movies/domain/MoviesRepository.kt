package com.rezazavareh7.movies.domain

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import com.rezazavareh7.movies.domain.networkstate.GetMoviesNetworkState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun searchMovie(query: String): GetMoviesNetworkState

    suspend fun getMovieDetail(movieId: Long): GetMovieDetailNetworkState

    fun getPagedMovies(): Flow<PagingData<MovieData>>
}
