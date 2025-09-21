package com.rezazavareh7.movies.domain.model

data class MediaDetailsResult(
    val hasError: Boolean = false,
    val mediaDetailsData: MediaDetailData? = null,
    val errorMessage: String = "",
    val mediaCredits: List<Credit> = emptyList(),
)
