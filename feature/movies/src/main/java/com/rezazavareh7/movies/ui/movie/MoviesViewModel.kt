package com.rezazavareh7.movies.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh7.movies.domain.model.Category
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.usecase.GetFavoritesUseCase
import com.rezazavareh7.movies.domain.usecase.GetTopRatedMoviesUseCase
import com.rezazavareh7.movies.domain.usecase.RemoveFavoriteItemUseCase
import com.rezazavareh7.movies.domain.usecase.SaveFavoriteItemUseCase
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
        private val searchMoviesUseCase: SearchMoviesUseCase,
        private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
        private val saveFavoriteItemUseCase: SaveFavoriteItemUseCase,
        private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase,
        private val getFavoritesUseCase: GetFavoritesUseCase,
    ) : ViewModel() {
        private var mMoviesState = MutableStateFlow(MoviesUiState(isLoading = true))
        val moviesState =
            mMoviesState
                .onStart {
                    getFavorites()
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

                is MoviesUiEvent.OnLikeMovie -> saveFavoriteMovie(event.movieData)

                is MoviesUiEvent.OnDislikeMovie -> removeFavoriteMovie(event.movieData)
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
                val result = getTopRatedMoviesUseCase()
                mMoviesState.update {
                    it.copy(
                        isLoading = false,
                        moviesPagedData = result.topRatedMovies,
                        hasSearchResult = false,
                    )
                }
            }
        }

        private fun getFavorites() {
            viewModelScope.launch {
                val result = getFavoritesUseCase.invoke(category = Category.MOVIE)
                result.favoriteList.collect { favorites ->
                    mMoviesState.update { it.copy(favorites = favorites) }
                }
            }
        }

        private fun saveFavoriteMovie(movieData: MovieData) {
            viewModelScope.launch {
                viewModelScope.launch {
                    saveFavoriteItemUseCase(movieData)
                }
            }
        }

        private fun removeFavoriteMovie(movieData: MovieData) {
            viewModelScope.launch {
                viewModelScope.launch {
                    removeFavoriteItemUseCase(movieData)
                }
            }
        }

        private fun searchMovies(query: String) {
            viewModelScope.launch {
                val result = searchMoviesUseCase(query)
                mMoviesState.update {
                    it.copy(
                        isLoading = false,
                        moviesPagedData = result.topRatedMovies,
                        hasSearchResult = true,
                    )
                }
            }
        }
    }
