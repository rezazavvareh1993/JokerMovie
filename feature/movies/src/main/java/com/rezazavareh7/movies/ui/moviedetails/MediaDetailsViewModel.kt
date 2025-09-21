package com.rezazavareh7.movies.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.usecase.GetFavoritesUseCase
import com.rezazavareh7.movies.domain.usecase.GetMediaCreditsUseCase
import com.rezazavareh7.movies.domain.usecase.GetMediaDetailsUseCase
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
    ) : ViewModel() {
        private var mMediaDetailsState = MutableStateFlow(MovieDetailsUiState(isLoading = true))
        val mediaDetailsState = mMediaDetailsState.asStateFlow()

        fun onEvent(event: MediaDetailsUiEvent) {
            when (event) {
                is MediaDetailsUiEvent.OnGetMediaDetailsCalled -> {
                    getMediaDetails(event.mediaId, event.mediaCategory)
                    getMediaCredits(event.mediaId, event.mediaCategory)
                }

                is MediaDetailsUiEvent.OnToastMessageShown ->
                    mMediaDetailsState.update {
                        it.copy(errorMessage = "")
                    }
            }
        }

        private fun getMediaCredits(
            mediaId: Long,
            mediaCategory: MediaCategory,
        ) {
            viewModelScope.launch {
                val result = getMediaCreditsUseCase(mediaId, mediaCategory)
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

        private fun getMediaDetails(
            mediaId: Long,
            mediaCategory: MediaCategory,
        ) {
            viewModelScope.launch {
                val result = getMediaDetailsUseCase(mediaId, mediaCategory)
                when (result.hasError) {
                    false -> {
                        mMediaDetailsState.update {
                            it.copy(
                                movieDetailsData = result.mediaDetailsData,
                                isLoading = false,
                            )
                        }
                    }

                    true -> {
                        mMediaDetailsState.update {
                            it.copy(
                                errorMessage = result.errorMessage,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
