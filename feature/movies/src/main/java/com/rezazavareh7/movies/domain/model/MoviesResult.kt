package com.rezazavareh7.movies.domain.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MoviesResult(
    val hasError: Boolean = false,
    val moviesData: List<MovieData> = emptyList(),
    val topRatedMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val upcomingMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val popularMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val nowPlayingMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val errorMessage: String = "",
)
