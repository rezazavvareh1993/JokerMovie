package com.rezazavareh7.movies.ui.favorite

import com.rezazavareh7.movies.domain.model.FavoriteData

sealed class FavoriteUiEvent {
    data class OnRemoveFavorite(
        val favoriteData: FavoriteData,
    ) : FavoriteUiEvent()

    data class GetFavorites(val category: String) : FavoriteUiEvent()

    data object OnToastMessageShown : FavoriteUiEvent()
}
