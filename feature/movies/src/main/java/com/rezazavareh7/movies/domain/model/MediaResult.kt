package com.rezazavareh7.movies.domain.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MediaResult(
    val hasError: Boolean = false,
    val moviesData: List<MediaData> = emptyList(),
    val topRatedMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val upcomingMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val popularMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val nowPlayingMovies: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val topRatedSeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val onTheAirSeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val popularSeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val airingTodaySeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val searchResult: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val errorMessage: String = "",
    val movieSearchHistory: List<String> = emptyList(),
    val seriesSearchHistory: List<String> = emptyList(),
)
