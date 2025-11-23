package com.rezazavareh7.movies.ui.moviedetails

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.model.MediaDetailData
import com.rezazavareh7.ui.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MediaDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val movieDetailsData: MediaDetailData? = null,
    val favoriteIds: List<Long> = emptyList(),
    val isFavorite: Boolean = false,
    val mediaCredits: List<Credit> = emptyList(),
    val mediaSimilarList: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
    val showOverview: Boolean = false,
    val mediaDataSelected: MediaData? = null,
)
