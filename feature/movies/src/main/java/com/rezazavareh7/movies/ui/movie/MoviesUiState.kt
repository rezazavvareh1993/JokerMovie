package com.rezazavareh7.movies.ui.movie

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.MovieData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MoviesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val movieNameInput: String = "",
    val moviesData: List<MovieData> = emptyList(),
    val topRatedMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val upcomingMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val popularMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val nowPlayingMovies: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val hasSearchResult: Boolean = false,
    val favoriteIds: List<Long> = emptyList(),
)
