package com.rezazavareh7.movies.ui.media.movie

import com.rezazavareh7.ui.util.UiText

sealed interface MoviesUiEvent {
    data object OnGetMoviesCalled : MoviesUiEvent

    data object OnToastMessageShown : MoviesUiEvent

    data class OnSearchBarExpandStateChanged(
        val isExpanded: Boolean,
    ) : MoviesUiEvent

    data class OnSearchQueryChanged(
        val newMovieName: String,
    ) : MoviesUiEvent

    data class OnSearched(
        val query: String,
    ) : MoviesUiEvent

    data class OnShowToast(
        val uiText: UiText?,
    ) : MoviesUiEvent
}
