package com.rezazavareh7.movies.ui.movie

sealed class MoviesUiEvent {
    data object OnGetMoviesCalled : MoviesUiEvent()

    data object OnCancelSearch : MoviesUiEvent()

    data object OnToastMessageShown : MoviesUiEvent()

    data class OnSearchMovieChanged(
        val newMovieName: String,
    ) : MoviesUiEvent()

    data class OnSearchedMovie(
        val query: String,
    ) : MoviesUiEvent()
}
