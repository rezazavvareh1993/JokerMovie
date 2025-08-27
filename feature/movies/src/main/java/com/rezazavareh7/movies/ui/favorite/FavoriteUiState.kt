package com.rezazavareh7.movies.ui.favorite

import com.rezazavareh7.movies.domain.model.FavoriteData

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val favorites: List<FavoriteData> = emptyList(),
)
