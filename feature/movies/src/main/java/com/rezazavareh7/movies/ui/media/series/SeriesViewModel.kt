package com.rezazavareh7.movies.ui.media.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.usecase.GetAiringTodaySeriesUseCase
import com.rezazavareh7.movies.domain.usecase.GetFavoritesUseCase
import com.rezazavareh7.movies.domain.usecase.GetOnTheAirSeriesUseCase
import com.rezazavareh7.movies.domain.usecase.GetPopularSeriesUseCase
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
        private val getFavoritesUseCase: GetFavoritesUseCase,
    ) : ViewModel() {
        private var mSeriesUiState = MutableStateFlow(SeriesUiState(isLoading = true))
        val seriesState =
            mSeriesUiState
                .onStart {
                    getFavorites()
                    getSeries()
                }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    SeriesUiState(isLoading = true),
                )

        fun onEvent(event: SeriesUiEvent) {
            when (event) {
                is SeriesUiEvent.OnGetSeriesCalled -> getSeries()
                is SeriesUiEvent.OnToastMessageShown ->
                    mSeriesUiState.update { it.copy(errorMessage = "") }

                is SeriesUiEvent.OnSearchQueryChanged ->
                    mSeriesUiState.update { it.copy(seriesNameInput = event.newMovieName) }

                is SeriesUiEvent.OnSearched -> searchMovies(event.query)

                is SeriesUiEvent.OnCancelSearch -> cancelSearch()
            }
        }

        private fun cancelSearch() {
            viewModelScope.launch {
                mSeriesUiState.update {
                    it.copy(seriesNameInput = "", hasSearchResult = false)
                }
                getSeries()
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
                        topRatedSeries = topRatedResult.await().topRatedMovies,
                        popularSeries = popularResult.await().popularMovies,
                        onTheAirSeries = onTheAirResult.await().upcomingMovies,
                        airingTodaySeries = airingTodayResult.await().nowPlayingMovies,
                        hasSearchResult = false,
                    )
                }
            }
        }

        private fun getFavorites() {
            viewModelScope.launch {
                val favoriteList = getFavoritesUseCase.invoke(category = MediaCategory.MOVIE.toString())
                favoriteList.collect { favorites ->
                    mSeriesUiState.update { it.copy(favoriteIds = favorites.map { item -> item.id }) }
                }
            }
        }

        private fun searchMovies(query: String) {
            viewModelScope.launch {
                val result = searchSeriesUseCase(query)
                mSeriesUiState.update {
                    it.copy(
                        isLoading = false,
                        searchResult = result.searchResult,
                        hasSearchResult = true,
                    )
                }
            }
        }
    }
