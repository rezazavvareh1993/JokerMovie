package com.rezazavareh7.movies.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.usecase.GetFavoritesUseCase
import com.rezazavareh7.movies.domain.usecase.GetMediaCreditsUseCase
import com.rezazavareh7.movies.domain.usecase.GetMediaDetailsUseCase
import com.rezazavareh7.movies.domain.usecase.GetSimilarListUseCase
import com.rezazavareh7.movies.domain.usecase.RemoveFavoriteItemUseCase
import com.rezazavareh7.movies.domain.usecase.SaveFavoriteItemUseCase
import com.rezazavareh7.ui.util.toUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
        private val getSimilarListUseCase: GetSimilarListUseCase,
    ) : ViewModel() {
        private var mMediaDetailsState = MutableStateFlow(MediaDetailsUiState(isLoading = true))
        val mediaDetailsState = mMediaDetailsState.asStateFlow()

        private var mediaJob: Job? = null

        fun onEvent(event: MediaDetailsUiEvent) {
            when (event) {
                is MediaDetailsUiEvent.OnGetMediaDetailsCalled -> {
                    getFavorites(event.mediaData)
                    getMediaDetails(event.mediaData)
                    getMediaCredits(event.mediaData)
//                    getMediaSimilarList(event.mediaData)
                }

                is MediaDetailsUiEvent.OnToastMessageShown ->
                    mMediaDetailsState.update { it.copy(errorMessage = null) }

                is MediaDetailsUiEvent.OnLikeMedia -> saveFavoriteMovie(event.mediaData)

                is MediaDetailsUiEvent.OnDislikeMedia -> removeFavoriteMovie(event.mediaData)

                is MediaDetailsUiEvent.OnShowMore -> mMediaDetailsState.update { it.copy(showOverview = true) }
                is MediaDetailsUiEvent.OnShowLess -> mMediaDetailsState.update { it.copy(showOverview = false) }
                is MediaDetailsUiEvent.MediaDataSelected -> {
                    mediaJob?.cancel()
                    mediaSelected(event.mediaData)
                }
            }
        }

        private fun mediaSelected(mediaData: MediaData) {
            mediaJob =
                viewModelScope.launch {
                    mMediaDetailsState.update {
                        it.copy(
                            mediaDataSelected = mediaData,
                            isLoading = true,
                        )
                    }
                    getFavorites(mediaData)
                    getMediaDetails(mediaData)
                    getMediaCredits(mediaData)
                    getMediaSimilarList(mediaData)
                }
        }

        private fun getMediaCredits(mediaData: MediaData) {
            viewModelScope.launch {
                val result = getMediaCreditsUseCase(mediaData.id, mediaData.mediaCategory)
                when (result.hasError) {
                    false -> {
                        mMediaDetailsState.update {
                            it.copy(mediaCredits = result.mediaCredits)
                        }
                    }

                    true -> {
                        mMediaDetailsState.update {
                            it.copy(errorMessage = result.dataError.toUiText())
                        }
                    }
                }
            }
        }

        private fun getMediaSimilarList(mediaData: MediaData) {
            viewModelScope.launch {
                val response = getSimilarListUseCase.invoke(mediaData)
                mMediaDetailsState.update {
                    it.copy(
                        mediaSimilarList = response.mediaSimilar.cachedIn(viewModelScope),
                    )
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
                            it.copy(errorMessage = result.dataError.toUiText(), isLoading = false)
                        }
                    }
                }
            }
        }

        private fun getFavorites(mediaData: MediaData) {
            viewModelScope.launch {
                getFavoritesUseCase().collect { favoriteItems ->
                    val isFavorite = favoriteItems.contains(mediaData)
                    mMediaDetailsState.update {
                        it.copy(
                            isFavorite = isFavorite,
                            favoriteIds =
                                favoriteItems.map { favoriteItem ->
                                    favoriteItem.id
                                },
                        )
                    }
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
