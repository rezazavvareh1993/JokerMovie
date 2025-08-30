package com.rezazavareh7.movies.ui.media.series

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.MediaData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SeriesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val seriesNameInput: String = "",
    val topRatedSeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val onTheAirSeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val popularSeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val airingTodaySeries: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val hasSearchResult: Boolean = false,
    val searchResult: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
)
