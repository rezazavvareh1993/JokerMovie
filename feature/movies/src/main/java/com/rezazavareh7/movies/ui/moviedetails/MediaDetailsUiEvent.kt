package com.rezazavareh7.movies.ui.moviedetails

import com.rezazavareh7.movies.domain.model.MediaCategory

sealed class MediaDetailsUiEvent {
    data class OnGetMediaDetailsCalled(
        val mediaId: Long,
        val mediaCategory: MediaCategory,
    ) : MediaDetailsUiEvent()

    data object OnToastMessageShown : MediaDetailsUiEvent()
}
