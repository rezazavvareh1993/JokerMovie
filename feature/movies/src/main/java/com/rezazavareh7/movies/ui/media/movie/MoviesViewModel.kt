package com.rezazavareh7.movies.ui.media.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rezazavareh7.movies.domain.usecase.GetNowPlayingMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.GetPopularMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.GetSearchMovieHistoryUseCase
import com.rezazavareh7.movies.domain.usecase.GetTopRatedMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.GetUpcomingMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.SearchMoviesUseCase
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
class MoviesViewModel
    @Inject
    constructor(
        private val searchMoviesUseCase: SearchMoviesUseCase,
        private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
        private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
        private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
        private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
        private val getSearchMovieHistoryUseCase: GetSearchMovieHistoryUseCase,
    ) : ViewModel() {
        private var mMoviesState = MutableStateFlow(MoviesUiState(isLoading = true))
        val moviesState =
            mMoviesState
                .onStart {
                    getMovies()
                    getSearchMovieHistory()
                }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    mMoviesState.value,
                )

        fun onEvent(event: MoviesUiEvent) {
            when (event) {
                is MoviesUiEvent.OnGetMoviesCalled -> getMovies()
                is MoviesUiEvent.OnToastMessageShown -> mMoviesState.update { it.copy(errorMessage = "") }

                is MoviesUiEvent.OnSearchQueryChanged ->
                    mMoviesState.update { it.copy(queryInput = event.newMovieName, shouldShowHistoryQueries = true) }

                is MoviesUiEvent.OnSearched -> searchMovies(event.query)

                is MoviesUiEvent.OnSearchBarExpandStateChanged -> handelSearchBarExpandState(event.isExpanded)
            }
        }

        private fun getSearchMovieHistory() {
            viewModelScope.launch {
                getSearchMovieHistoryUseCase().movieQueriesHistory.collect { historyQueries ->
                    mMoviesState.update { it.copy(searchQueriesHistory = historyQueries) }
                }
            }
        }

        private fun handelSearchBarExpandState(isExpanded: Boolean) {
            viewModelScope.launch {
                if (isExpanded) {
                    mMoviesState.update { it.copy(isSearchBarExpanded = true, shouldShowHistoryQueries = true) }
                    getSearchMovieHistory()
                } else {
                    mMoviesState.update { it.copy(queryInput = "", isSearchBarExpanded = false, hasSearched = false) }
                    getMovies()
                }
            }
        }

        private fun getMovies() {
            viewModelScope.launch {
                val topRatedResult = async { getTopRatedMoviesUseCase() }
                val popularResult = async { getPopularMoviesUseCase() }
                val nowPlayingResult = async { getNowPlayingMoviesUseCase() }
                val upcomingResult = async { getUpcomingMoviesUseCase() }
                mMoviesState.update {
                    it.copy(
                        isLoading = false,
                        topRatedMovies = topRatedResult.await().topRatedMovies.cachedIn(viewModelScope),
                        popularMovies = popularResult.await().popularMovies.cachedIn(viewModelScope),
                        upcomingMovies = upcomingResult.await().upcomingMovies.cachedIn(viewModelScope),
                        nowPlayingMovies =
                            nowPlayingResult.await().nowPlayingMovies.cachedIn(viewModelScope),
                    )
                }
            }
        }

        private fun searchMovies(query: String) {
            viewModelScope.launch {
                mMoviesState.update { it.copy(isLoading = true, queryInput = query) }
                val result = searchMoviesUseCase(query)
                mMoviesState.update {
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
