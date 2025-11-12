package com.rezazavareh7.movies.ui.media.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rezazavareh7.movies.domain.usecase.GetAiringTodaySeriesUseCase
import com.rezazavareh7.movies.domain.usecase.GetOnTheAirSeriesUseCase
import com.rezazavareh7.movies.domain.usecase.GetPopularSeriesUseCase
import com.rezazavareh7.movies.domain.usecase.GetSearchSeriesHistoryUseCase
import com.rezazavareh7.movies.domain.usecase.GetTopRatedSeriesUseCase
import com.rezazavareh7.movies.domain.usecase.SearchSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel
    @Inject
    constructor(
        private val searchSeriesUseCase: SearchSeriesUseCase,
        private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
        private val getOnTheAirSeriesUseCase: GetOnTheAirSeriesUseCase,
        private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
        private val getPopularSeriesUseCase: GetPopularSeriesUseCase,
        private val getSearchSeriesHistoryUseCase: GetSearchSeriesHistoryUseCase,
    ) : ViewModel() {
        private var mSeriesUiState = MutableStateFlow(SeriesUiState(isLoading = true))
        val seriesState =
            mSeriesUiState
                .onStart {
                    getSeries()
                    getSearchSeriesHistory()
                }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    mSeriesUiState.value,
                )

        fun onEvent(event: SeriesUiEvent) {
            when (event) {
                is SeriesUiEvent.OnGetSeriesCalled -> getSeries()
                is SeriesUiEvent.OnToastMessageShown ->
                    mSeriesUiState.update { it.copy(errorMessage = "") }

                is SeriesUiEvent.OnSearchQueryChanged ->
                    mSeriesUiState.update { it.copy(queryInput = event.newSeriesName, shouldShowHistoryQueries = true) }

                is SeriesUiEvent.OnSearched -> searchMovies(event.query)

                is SeriesUiEvent.OnSearchBarExpandStateChanged -> handelSearchBarExpandState(event.isExpanded)
            }
        }

        private fun handelSearchBarExpandState(isExpanded: Boolean) {
            viewModelScope.launch {
                if (isExpanded) {
                    mSeriesUiState.update {
                        it.copy(isSearchBarExpanded = true, shouldShowHistoryQueries = true)
                    }
                    getSearchSeriesHistory()
                } else {
                    mSeriesUiState.update { it.copy(queryInput = "", isSearchBarExpanded = false, hasSearched = false) }
                    getSeries()
                }
            }
        }

        private fun getSearchSeriesHistory() {
            viewModelScope.launch {
                getSearchSeriesHistoryUseCase().seriesQueriesHistory.collect { historyQueries ->
                    mSeriesUiState.update { it.copy(searchQueriesHistory = historyQueries) }
                }
            }
        }

        private fun getSeries() {
            viewModelScope.launch {
                val topRatedResult = async { getTopRatedSeriesUseCase() }
                val popularResult = async { getPopularSeriesUseCase() }
                val airingTodayResult = async { getAiringTodaySeriesUseCase() }
                val onTheAirResult = async { getOnTheAirSeriesUseCase() }
                mSeriesUiState.update {
                    it.copy(
                        isLoading = false,
                        topRatedSeries = topRatedResult.await().topRatedSeries.cachedIn(viewModelScope),
                        popularSeries = popularResult.await().popularSeries.cachedIn(viewModelScope),
                        onTheAirSeries = onTheAirResult.await().onTheAirSeries.cachedIn(viewModelScope),
                        airingTodaySeries = airingTodayResult.await().airingTodaySeries.cachedIn(viewModelScope),
                    )
                }
            }
        }

        private fun searchMovies(query: String) {
            viewModelScope.launch {
                mSeriesUiState.update { it.copy(queryInput = query, isLoading = true) }
                val result = searchSeriesUseCase(query)
                mSeriesUiState.update {
                    it.copy(
                        isLoading = false,
                        searchResult = result.searchResult,
                        hasSearched = true,
                        shouldShowHistoryQueries = false,
                    )
                }
            }
        }
    }
