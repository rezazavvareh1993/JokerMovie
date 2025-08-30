package com.rezazavareh7.movies.ui.media.series

sealed class SeriesUiEvent {
    data object OnGetSeriesCalled : SeriesUiEvent()

    data object OnCancelSearch : SeriesUiEvent()

    data object OnToastMessageShown : SeriesUiEvent()

    data class OnSearchQueryChanged(
        val newMovieName: String,
    ) : SeriesUiEvent()

    data class OnSearched(
        val query: String,
    ) : SeriesUiEvent()
}
