package com.rezazavareh7.movies.ui.images

import com.rezazavareh7.movies.domain.model.MediaImage

data class MediaImagesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val images: List<MediaImage> = emptyList(),
    val indexOfFirstFullScreenPhoto: Int = -1,
    val shouldDisplayFullScreenPhotos: Boolean = false,
    val currentDisplayPhotoItemInfo: DisplayPhotoItemInfo? = null,
    val lastDisplayedImageIndex: Int = -1,
    val shouldShowSavePhotoBottomSheet: Boolean = false,
    val photoUrlToSave: String = "",
    val sharePhotoAbsolutePath: String = "",
    val savePhotoState: SavePhotoState = SavePhotoState.Idle,
)

sealed interface SavePhotoState {
    data object Saved : SavePhotoState

    data object Error : SavePhotoState

    data object Idle : SavePhotoState
}
