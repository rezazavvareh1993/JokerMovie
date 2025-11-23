package com.rezazavareh7.movies.ui.model

import androidx.paging.compose.LazyPagingItems
import com.rezazavareh7.movies.domain.model.MediaData

data class MediaCategoryPagingList(
    val pagingList: LazyPagingItems<MediaData>,
    val title: String,
)
