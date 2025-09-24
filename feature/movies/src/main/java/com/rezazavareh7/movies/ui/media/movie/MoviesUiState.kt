package com.rezazavareh7.movies.ui.media.movie

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.MediaData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MoviesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val queryInput: String = "",
    val moviesData: List<MediaData> = emptyList(),
    val topRatedMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val upcomingMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val popularMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val nowPlayingMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val searchResult: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val isSearchBarExpanded: Boolean = false,
    val hasSearched: Boolean = false,
    val searchQueriesHistory: List<String> = emptyList(),
    val shouldShowHistoryQueries: Boolean = false,
)
