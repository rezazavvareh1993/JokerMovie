package com.rezazavareh7.movies.domain.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MoviesResult(
    val hasError: Boolean = false,
    val moviesData: List<MovieData> = emptyList(),
    val moviesPagedData: Flow<PagingData<MovieData>> = flowOf(PagingData.empty()),
    val errorMessage: String = "",
)
