package com.rezazavareh7.movies.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaImagesViewModel
    @Inject
    constructor(
        private val getImagesUseCase: GetImagesUseCase,
    ) : ViewModel() {
        private var mMediaImagesUiState = MutableStateFlow(MediaImagesUiState(isLoading = true))
        val mediaImagesUiState: StateFlow<MediaImagesUiState> = mMediaImagesUiState.asStateFlow()

        fun onEvent(event: MediaImagesUiEvent) {
            when (event) {
                is MediaImagesUiEvent.OnToastMessageShown -> mMediaImagesUiState.update { it.copy(errorMessage = "") }
                is MediaImagesUiEvent.OnGetMediaImages -> getImages(event.mediaId, event.mediaCategory)
            }
        }

        private fun getImages(
            mediaId: Long,
            mediaCategory: MediaCategory,
        ) {
            viewModelScope.launch {
                val result = getImagesUseCase.invoke(mediaId = mediaId, mediaCategory = mediaCategory)
                when (result.hasError) {
                    true ->
                        mMediaImagesUiState.update {
                            it.copy(
                                errorMessage = result.errorMessage,
                                isLoading = false,
                            )
                        }

                    false ->
                        mMediaImagesUiState.update {
                            it.copy(
                                images = result.images,
                                isLoading = false,
                            )
                        }
                }
            }
        }
    }
