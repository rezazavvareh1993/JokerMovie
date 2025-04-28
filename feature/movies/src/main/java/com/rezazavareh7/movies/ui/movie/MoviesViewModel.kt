package com.rezazavareh7.movies.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.usecase.GetMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
        private val getMoviesUseCase: GetMoviesUseCase,
        private val searchMoviesUseCase: SearchMoviesUseCase,
    ) : ViewModel() {
        private var mMoviesState = MutableStateFlow(MoviesUiState(isLoading = true))
        val moviesState =
            mMoviesState.onStart {
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
            }
        }

        private fun getMovies() {
            viewModelScope.launch {
                val result = getMoviesUseCase()
                when (result.hasError) {
                    false -> {
                        mMoviesState.update {
                            it.copy(
                                isLoading = false,
                                moviesData = result.moviesData,
                                hasSearchResult = false,
                                errorMessage = result.moviesData.size.toString(),
                            )
                        }
                    }

                    true -> {
                        mMoviesState.update {
                            it.copy(
                                isLoading = false,
                                hasSearchResult = false,
                                errorMessage = result.errorMessage,
                            )
                        }
                    }
                }
            }
        }

        private fun searchMovies(query: String) {
            viewModelScope.launch {
                val result = searchMoviesUseCase(query)
                when (result.hasError) {
                    false -> {
                        mMoviesState.update {
                            it.copy(
                                isLoading = false,
                                moviesData = result.moviesData,
                                hasSearchResult = true,
                                errorMessage = result.moviesData.size.toString(),
                            )
                        }
                    }

                    true -> {
                        mMoviesState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                            )
                        }
                    }
                }
            }
        }
    }
