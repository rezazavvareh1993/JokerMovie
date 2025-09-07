package com.rezazavareh7.movies.ui.media

import com.rezazavareh7.movies.domain.model.MediaData

sealed class MediaUiEvent {
    data class OnCurrentTabRowChanged(val tabIndex: Int) : MediaUiEvent()

    data class OnLikeMovie(
        val mediaData: MediaData,
    ) : MediaUiEvent()

    data class OnDislikeMovie(
        val mediaData: MediaData,
    ) : MediaUiEvent()
}
