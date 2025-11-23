package com.rezazavareh7.movies.ui.favorite

import com.rezazavareh7.movies.domain.model.MediaData

sealed class FavoriteUiEvent {
    data class OnRemoveFavorite(
        val favoriteData: MediaData,
    ) : FavoriteUiEvent()

    data object GetFavorites : FavoriteUiEvent()

    data object OnToastMessageShown : FavoriteUiEvent()
}
