package com.rezazavareh7.movies.ui.media

import com.rezazavareh7.movies.domain.model.MediaCategory

data class MediaUiState(
    val currentTabRowIndex: Int = 0,
    val currentTabRowType: MediaCategory = MediaCategory.MOVIE,
    val favoriteIds: List<Long> = emptyList(),
)
