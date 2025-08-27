package com.rezazavareh7.movies.ui.moviedetails

sealed class MovieDetailsUiEvent {
    data class OnGetMovieDetailsCalled(
        val movieId: Long,
    ) : MovieDetailsUiEvent()

    data object OnToastMessageShown : MovieDetailsUiEvent()
}
