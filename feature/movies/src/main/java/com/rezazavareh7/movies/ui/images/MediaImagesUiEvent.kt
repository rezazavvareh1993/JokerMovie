package com.rezazavareh7.movies.ui.images

import com.rezazavareh7.movies.domain.model.MediaCategory

sealed class MediaImagesUiEvent {
    data object OnToastMessageShown : MediaImagesUiEvent()

    data class OnGetMediaImages(val mediaId: Long, val mediaCategory: MediaCategory) : MediaImagesUiEvent()
}
