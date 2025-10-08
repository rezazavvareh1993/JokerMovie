package com.rezazavareh7.movies.ui.moviedetails

import com.rezazavareh7.movies.domain.model.MediaData

sealed class MediaDetailsUiEvent {
    data class OnGetMediaDetailsCalled(
        val mediaData: MediaData,
    ) : MediaDetailsUiEvent()

    data object OnToastMessageShown : MediaDetailsUiEvent()

    data class OnLikeMedia(
        val mediaData: MediaData,
    ) : MediaDetailsUiEvent()

    data class OnDislikeMedia(
        val mediaData: MediaData,
    ) : MediaDetailsUiEvent()
}
