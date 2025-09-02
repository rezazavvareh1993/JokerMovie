package com.rezazavareh7.movies.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.usecase.GetImagesUseCase
import com.rezazavareh7.movies.domain.usecase.SavePhotoFromCacheUseCase
import com.rezazavareh7.movies.domain.usecase.SharePhotoUseCase
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
        private val sharePhotoUseCase: SharePhotoUseCase,
        private val savePhotoFromCacheUseCase: SavePhotoFromCacheUseCase,
    ) : ViewModel() {
        private var mMediaImagesUiState = MutableStateFlow(MediaImagesUiState(isLoading = true))
        val mediaImagesUiState: StateFlow<MediaImagesUiState> = mMediaImagesUiState.asStateFlow()

        fun onEvent(event: MediaImagesUiEvent) {
            when (event) {
                is MediaImagesUiEvent.OnToastMessageShown ->
                    mMediaImagesUiState.update { it.copy(errorMessage = "") }

                is MediaImagesUiEvent.OnGetMediaImages -> getImages(event.mediaId, event.mediaCategory)
                is MediaImagesUiEvent.OnFullScreenExitClicked ->
                    mMediaImagesUiState.update {
                        it.copy(
                            shouldDisplayFullScreenPhotos = false,
                            lastDisplayedImageIndex =
                                mMediaImagesUiState.value.currentDisplayPhotoItemInfo?.index
                                    ?: -1,
                        )
                    }

                is MediaImagesUiEvent.OnItemClicked ->
                    mMediaImagesUiState.update {
                        it.copy(
                            indexOfFirstFullScreenPhoto = event.index,
                            shouldDisplayFullScreenPhotos = true,
                        )
                    }

                is MediaImagesUiEvent.OnResetLastDisplayedImage ->
                    mMediaImagesUiState.update {
                        it.copy(
                            lastDisplayedImageIndex = -1,
                            currentDisplayPhotoItemInfo = null,
                        )
                    }

                is MediaImagesUiEvent.OnSaveImageClicked ->
                    mMediaImagesUiState.update {
                        it.copy(
                            shouldShowSavePhotoBottomSheet = true,
                            photoUrlToSave = event.imageUrl,
                        )
                    }

                is MediaImagesUiEvent.OnHideSavePhotoBottomSheet ->
                    mMediaImagesUiState.update {
                        it.copy(
                            shouldShowSavePhotoBottomSheet = false,
                            photoUrlToSave = "",
                        )
                    }

                is MediaImagesUiEvent.OnConfirmSavePhoto ->
                    if (mMediaImagesUiState.value.photoUrlToSave.isNotEmpty()) {
                        savePhotoFromCache(mMediaImagesUiState.value.photoUrlToSave)
                    }

                is MediaImagesUiEvent.OnSavePhotoSuccessful ->
                    mMediaImagesUiState.update { it.copy(savePhotoState = SavePhotoState.Idle) }

                is MediaImagesUiEvent.LastPhotoDisplayed ->
                    mMediaImagesUiState.update {
                        it.copy(currentDisplayPhotoItemInfo = event.currentDisplayPhotoInfo)
                    }

                is MediaImagesUiEvent.OnSharePhotoClicked -> sharePhoto(event.imageUrl)

                is MediaImagesUiEvent.OnShareComplete ->
                    mMediaImagesUiState.update { it.copy(sharePhotoAbsolutePath = "") }
            }
        }

        private fun sharePhoto(photoUrl: String) =
            viewModelScope.launch {
                val result = sharePhotoUseCase(photoUrl)
                when (result.hasError) {
                    true ->
                        mMediaImagesUiState.update {
                            it.copy(
                                errorMessage = result.errorMessage,
                                sharePhotoAbsolutePath = "",
                            )
                        }

                    false -> mMediaImagesUiState.update { it.copy(sharePhotoAbsolutePath = result.sharePhotoAbsolutePath) }
                }
            }

        private fun savePhotoFromCache(imageUrl: String) {
            viewModelScope.launch {
                val result = savePhotoFromCacheUseCase(imageUrl)
                if (result.isFailure) {
                    mMediaImagesUiState.update {
                        it.copy(
                            savePhotoState = SavePhotoState.Error,
                            shouldShowSavePhotoBottomSheet = false,
                            photoUrlToSave = "",
                            errorMessage = "Cant save it",
                        )
                    }
                } else {
                    mMediaImagesUiState.update {
                        it.copy(
                            savePhotoState = SavePhotoState.Saved,
                            shouldShowSavePhotoBottomSheet = false,
                            photoUrlToSave = "",
                        )
                    }
                }
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
                                isLoading = false,
                                errorMessage = result.errorMessage,
                            )
                        }

                    false ->
                        mMediaImagesUiState.update {
                            it.copy(
                                isLoading = false,
                                images = result.images,
                            )
                        }
                }
            }
        }
    }
