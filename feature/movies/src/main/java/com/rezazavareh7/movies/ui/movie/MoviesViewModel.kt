package com.rezazavareh7.movies.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.rezazavareh.database.MovieEntity
import com.rezazavareh7.movies.data.mapper.toMovieData
import com.rezazavareh7.movies.domain.usecase.GetPagedMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
    @Inject
    constructor(
        pager: Pager<Int, MovieEntity>,
//        private val getMoviesUseCase: GetMoviesUseCase,
        private val searchMoviesUseCase: SearchMoviesUseCase,
        private val getPagedMoviesUseCase: GetPagedMoviesUseCase,
    ) : ViewModel() {
        private var mMoviesState = MutableStateFlow(MoviesUiState(isLoading = true))

        val moviePagingFlow =
            pager
                .flow
                .map { pagingData ->
                    pagingData.map { movieEntity -> movieEntity.toMovieData() }
                }.cachedIn(viewModelScope)

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
                val result = getPagedMoviesUseCase()
                mMoviesState.update {
                    it.copy(
                        isLoading = false,
                        moviesPagedData = result.moviesPagedData,
                        hasSearchResult = false,
                    )
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
