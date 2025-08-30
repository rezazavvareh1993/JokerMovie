package com.rezazavareh7.movies.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.usecase.GetMediaDetailsUseCase
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
    ) : ViewModel() {
        private var mMediaDetailsState = MutableStateFlow(MovieDetailsUiState(isLoading = true))
        val mediaDetailsState = mMediaDetailsState.asStateFlow()

        fun onEvent(event: MediaDetailsUiEvent) {
            when (event) {
                is MediaDetailsUiEvent.OnGetMediaDetailsCalled -> getMediaDetails(event.mediaId, event.mediaCategory)
                is MediaDetailsUiEvent.OnToastMessageShown ->
                    mMediaDetailsState.update { it.copy(errorMessage = "") }
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
                                isLoading = false,
                                movieDetailsData = result.mediaDetailsData,
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
    }
