package com.rezazavareh7.movies.domain.model

data class MediaImageResult(
    val hasError: Boolean = false,
    val errorMessage: String = "",
    val images: List<MediaImage> = emptyList(),
    val sharePhotoAbsolutePath: String = "",
)
