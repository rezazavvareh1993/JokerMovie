package com.rezazavareh7.movies.domain.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MediaDetailsResult(
    val hasError: Boolean = false,
    val mediaDetailsData: MediaDetailData? = null,
    val errorMessage: String = "",
    val mediaCredits: List<Credit> = emptyList(),
    val mediaSimilar: Flow<PagingData<MediaData>> = flowOf(PagingData.empty()),
)
