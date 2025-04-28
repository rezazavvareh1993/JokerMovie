package com.rezazavareh7.movies.domain.model

data class MoviesResult(
    val hasError: Boolean = false,
    val moviesData: List<MovieData> = emptyList(),
    val errorMessage: String = "",
)
