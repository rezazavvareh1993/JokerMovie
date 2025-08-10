package com.rezazavareh7.movies.domain

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovie(query: String): Flow<PagingData<MovieData>>

    suspend fun getMovieDetail(movieId: Long): GetMovieDetailNetworkState

    fun getPagedMovies(): Flow<PagingData<MovieData>>
}
