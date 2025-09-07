package com.rezazavareh7.movies.ui.media.movie

sealed class MoviesUiEvent {
    data object OnGetMoviesCalled : MoviesUiEvent()

    data object OnToastMessageShown : MoviesUiEvent()

    data class OnSearchBarExpandStateChanged(val isExpanded: Boolean) : MoviesUiEvent()
    data class OnMovieQueryChanged(val newMovieName: String) : MoviesUiEvent()
    data class OnSearchedMovie(val query: String) : MoviesUiEvent()
}
