package com.rezazavareh7.movies.ui.moviedetails

import com.rezazavareh7.movies.domain.model.MovieDetailData

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val movieDetailsData: MovieDetailData? = null,
)
