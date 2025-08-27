package com.rezazavareh7.movies.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.FavoriteData
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.usecase.GetFavoritesUseCase
import com.rezazavareh7.movies.domain.usecase.RemoveFavoriteItemUseCase
import com.rezazavareh7.movies.domain.usecase.SaveFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
    @Inject
    constructor(
        private val saveFavoriteItemUseCase: SaveFavoriteItemUseCase,
        private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase,
        private val getFavoritesUseCase: GetFavoritesUseCase,
    ) : ViewModel() {
        private var mFavoriteState = MutableStateFlow(FavoriteUiState(isLoading = true))
        val favoriteState = mFavoriteState.asStateFlow()

        fun onEvent(event: FavoriteUiEvent) {
            when (event) {
                is FavoriteUiEvent.OnRemoveFavorite -> removeFavoriteMovie(event.favoriteData)
                is FavoriteUiEvent.OnToastMessageShown ->
                    mFavoriteState.update { it.copy(errorMessage = "") }

                is FavoriteUiEvent.GetFavorites -> getFavorites(event.category)
            }
        }

        private fun getFavorites(category: String) {
            viewModelScope.launch {
                val favoriteList = getFavoritesUseCase.invoke(category = category)
                favoriteList.collect { favorites ->
                    mFavoriteState.update { it.copy(favorites = favorites, isLoading = false) }
                }
            }
        }

        private fun saveFavoriteMovie(mediaData: MediaData) {
            viewModelScope.launch {
                viewModelScope.launch {
                    saveFavoriteItemUseCase(mediaData)
                }
            }
        }

        private fun removeFavoriteMovie(favoriteData: FavoriteData) {
            viewModelScope.launch {
                viewModelScope.launch {
                    removeFavoriteItemUseCase(favoriteData.id)
                }
            }
        }
    }
