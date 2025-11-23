package com.rezazavareh7.movies.ui.images

import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaImage

sealed class MediaImagesUiEvent {
    data object OnToastMessageShown : MediaImagesUiEvent()

    data class OnGetMediaImages(
        val mediaId: Long,
        val mediaCategory: MediaCategory,
    ) : MediaImagesUiEvent()

    data class LastPhotoDisplayed(
        val currentDisplayPhotoInfo: DisplayPhotoItemInfo?,
    ) : MediaImagesUiEvent()

    data object OnResetLastDisplayedImage : MediaImagesUiEvent()

    data object OnFullScreenExitClicked : MediaImagesUiEvent()

    data object OnConfirmSavePhoto : MediaImagesUiEvent()

    data object OnHideSavePhotoBottomSheet : MediaImagesUiEvent()

    data object OnSavePhotoSuccessful : MediaImagesUiEvent()

    data object OnShareComplete : MediaImagesUiEvent()

    data class OnItemClicked(
        val item: MediaImage,
        val index: Int,
    ) : MediaImagesUiEvent()

    data class OnSaveImageClicked(
        val imageUrl: String,
    ) : MediaImagesUiEvent()

    data class OnSharePhotoClicked(
        val imageUrl: String,
    ) : MediaImagesUiEvent()
}
