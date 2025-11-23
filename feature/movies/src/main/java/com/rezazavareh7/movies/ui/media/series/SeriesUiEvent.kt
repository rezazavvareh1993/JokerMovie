package com.rezazavareh7.movies.ui.media.series

import com.rezazavareh7.ui.util.UiText

sealed interface SeriesUiEvent {
    data object OnGetSeriesCalled : SeriesUiEvent

    data object OnToastMessageShown : SeriesUiEvent

    data class OnSearchBarExpandStateChanged(
        val isExpanded: Boolean,
    ) : SeriesUiEvent

    data class OnSearchQueryChanged(
        val newSeriesName: String,
    ) : SeriesUiEvent

    data class OnSearched(
        val query: String,
    ) : SeriesUiEvent

    data class OnShowToast(
        val uiText: UiText?,
    ) : SeriesUiEvent
}
