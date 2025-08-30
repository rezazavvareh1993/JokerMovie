package com.rezazavareh7.movies.ui.favorite

import com.rezazavareh7.movies.domain.model.FavoriteData

sealed class FavoriteUiEvent {
    data class OnRemoveFavorite(
        val favoriteData: FavoriteData,
    ) : FavoriteUiEvent()

    data object GetFavorites : FavoriteUiEvent()

    data object OnToastMessageShown : FavoriteUiEvent()
}
