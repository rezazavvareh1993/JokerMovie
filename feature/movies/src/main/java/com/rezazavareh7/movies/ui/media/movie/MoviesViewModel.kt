package com.rezazavareh7.movies.ui.media.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.usecase.GetNowPlayingMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.GetPopularMoviesUseCase
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
    ) : ViewModel() {
        private var mMoviesState = MutableStateFlow(MoviesUiState(isLoading = true))
        val moviesState =
            mMoviesState
                .onStart {
                    getMovies()
                }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    MoviesUiState(isLoading = true),
                )

        fun onEvent(event: MoviesUiEvent) {
            when (event) {
                is MoviesUiEvent.OnGetMoviesCalled -> getMovies()
                is MoviesUiEvent.OnToastMessageShown ->
                    mMoviesState.update { it.copy(errorMessage = "") }

                is MoviesUiEvent.OnSearchMovieChanged ->
                    mMoviesState.update { it.copy(movieNameInput = event.newMovieName) }

                is MoviesUiEvent.OnSearchedMovie -> searchMovies(event.query)

                is MoviesUiEvent.OnCancelSearch -> cancelSearch()
            }
        }

        private fun cancelSearch() {
            viewModelScope.launch {
                mMoviesState.update {
                    it.copy(movieNameInput = "", hasSearchResult = false)
                }
                getMovies()
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
                        topRatedMovies = topRatedResult.await().topRatedMovies,
                        popularMovies = popularResult.await().popularMovies,
                        upcomingMovies = upcomingResult.await().upcomingMovies,
                        nowPlayingMovies = nowPlayingResult.await().nowPlayingMovies,
                        hasSearchResult = false,
                    )
                }
            }
        }

        private fun searchMovies(query: String) {
            viewModelScope.launch {
                val result = searchMoviesUseCase(query)
                mMoviesState.update {
                    it.copy(
                        isLoading = false,
                        searchResult = result.searchResult,
                        hasSearchResult = true,
                    )
                }
            }
        }
    }
