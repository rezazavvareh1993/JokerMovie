package com.rezazavareh7.movies.ui.model

import com.rezazavareh7.movies.domain.model.MediaCategory

data class MediaTabItem(
    val name: String,
    val type: MediaCategory,
    val icon: Int,
)
