package com.rezazavareh7.movies.ui.moviedetails

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.model.MediaDetailData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val movieDetailsData: MediaDetailData? = null,
    val isFavorite: Boolean = false,
    val mediaCredits: List<Credit> = emptyList(),
    val mediaSimilarList: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
)
