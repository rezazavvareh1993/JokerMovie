package com.rezazavareh7.movies.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class FavoriteResult(
    val hasError: Boolean = false,
    val favoriteList: Flow<List<FavoriteData>> = flowOf(emptyList()),
    val errorMessage: String = "",
)
