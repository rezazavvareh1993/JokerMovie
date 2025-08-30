package com.rezazavareh7.movies.ui.images

import com.rezazavareh7.movies.domain.model.MediaImage

data class MediaImagesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val images: List<MediaImage> = emptyList(),
)
