package com.rezazavareh7.movies.ui.media.series

sealed class SeriesUiEvent {
    data object OnGetSeriesCalled : SeriesUiEvent()

    data object OnToastMessageShown : SeriesUiEvent()

    data class OnSearchBarExpandStateChanged(
        val isExpanded: Boolean,
    ) : SeriesUiEvent()

    data class OnSearchQueryChanged(
        val newSeriesName: String,
    ) : SeriesUiEvent()

    data class OnSearched(
        val query: String,
    ) : SeriesUiEvent()
}
