package com.rezazavareh7.movies.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.usecase.GetFavoritesUseCase
import com.rezazavareh7.movies.domain.usecase.GetMediaCreditsUseCase
import com.rezazavareh7.movies.domain.usecase.GetMediaDetailsUseCase
import com.rezazavareh7.movies.domain.usecase.RemoveFavoriteItemUseCase
import com.rezazavareh7.movies.domain.usecase.SaveFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailsViewModel
    @Inject
    constructor(
        private val getMediaDetailsUseCase: GetMediaDetailsUseCase,
        private val getMediaCreditsUseCase: GetMediaCreditsUseCase,
        private val saveFavoriteItemUseCase: SaveFavoriteItemUseCase,
        private val getFavoritesUseCase: GetFavoritesUseCase,
        private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase,
    ) : ViewModel() {
        private var mMediaDetailsState = MutableStateFlow(MovieDetailsUiState(isLoading = true))
        val mediaDetailsState = mMediaDetailsState.asStateFlow()

        fun onEvent(event: MediaDetailsUiEvent) {
            when (event) {
                is MediaDetailsUiEvent.OnGetMediaDetailsCalled -> {
                    getFavorites(event.mediaData)
                    getMediaDetails(event.mediaData)
                    getMediaCredits(event.mediaData)
                }

                is MediaDetailsUiEvent.OnToastMessageShown ->
                    mMediaDetailsState.update { it.copy(errorMessage = "") }

                is MediaDetailsUiEvent.OnLikeMedia -> saveFavoriteMovie(event.mediaData)

                is MediaDetailsUiEvent.OnDislikeMedia -> removeFavoriteMovie(event.mediaData)
            }
        }

        private fun getMediaCredits(mediaData: MediaData) {
            viewModelScope.launch {
                val result = getMediaCreditsUseCase(mediaData.id, mediaData.mediaCategory)
                when (result.hasError) {
                    false -> {
                        mMediaDetailsState.update {
                            it.copy(
                                isLoading = false,
                                mediaCredits = result.mediaCredits,
                            )
                        }
                    }

                    true -> {
                        mMediaDetailsState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                            )
                        }
                    }
                }
            }
        }

        private fun getMediaDetails(mediaData: MediaData) {
            viewModelScope.launch {
                val result =
                    getMediaDetailsUseCase(mediaData.id, mediaCategory = mediaData.mediaCategory)
                when (result.hasError) {
                    false -> {
                        mMediaDetailsState.update {
                            it.copy(movieDetailsData = result.mediaDetailsData, isLoading = false)
                        }
                    }

                    true -> {
                        mMediaDetailsState.update {
                            it.copy(errorMessage = result.errorMessage, isLoading = false)
                        }
                    }
                }
            }
        }

        private fun getFavorites(mediaData: MediaData) {
            viewModelScope.launch {
                getFavoritesUseCase().collect { favoriteItems ->
                    val isFavorite = favoriteItems.contains(mediaData)
                    mMediaDetailsState.update { it.copy(isFavorite = isFavorite) }
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

        private fun removeFavoriteMovie(mediaData: MediaData) {
            viewModelScope.launch {
                viewModelScope.launch {
                    removeFavoriteItemUseCase(mediaData.id)
                }
            }
        }
    }
