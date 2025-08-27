package com.rezazavareh7.movies.domain.model

data class MovieDetailsResult(
    val hasError: Boolean = false,
    val movieDetailsData: MediaDetailData? = null,
    val errorMessage: String = "",
)
