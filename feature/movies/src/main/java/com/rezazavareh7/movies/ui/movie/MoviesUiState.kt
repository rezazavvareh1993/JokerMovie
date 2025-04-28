package com.rezazavareh7.movies.ui.movie

import com.rezazavareh7.movies.domain.model.MovieData

data class MoviesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val movieNameInput: String = "",
    val moviesData: List<MovieData> = emptyList(),
    val hasSearchResult: Boolean = false,
)
