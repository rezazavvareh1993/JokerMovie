package com.rezazavareh7.movies.ui.moviedetails

import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.ui.util.UiText

sealed interface MediaDetailsUiEvent {
    data object OnToastMessageShown : MediaDetailsUiEvent

    data class OnLikeMedia(
        val mediaData: MediaData,
    ) : MediaDetailsUiEvent

    data class OnDislikeMedia(
        val mediaData: MediaData,
    ) : MediaDetailsUiEvent

    data object OnShowMore : MediaDetailsUiEvent

    data object OnShowLess : MediaDetailsUiEvent

    data class MediaDataSelected(
        val mediaData: MediaData,
    ) : MediaDetailsUiEvent

    data class OnShowToast(
        val uiText: UiText?,
    ) : MediaDetailsUiEvent
}
