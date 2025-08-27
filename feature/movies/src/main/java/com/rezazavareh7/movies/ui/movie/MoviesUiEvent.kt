package com.rezazavareh7.movies.ui.movie

import com.rezazavareh7.movies.domain.model.MovieData

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

    data class OnLikeMovie(
        val movieData: MovieData,
    ) : MoviesUiEvent()

    data class OnDislikeMovie(
        val movieData: MovieData,
    ) : MoviesUiEvent()
}
