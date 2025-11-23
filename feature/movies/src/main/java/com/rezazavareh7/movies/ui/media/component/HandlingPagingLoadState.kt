package com.rezazavareh7.movies.ui.media.component

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.ui.model.MediaCategoryPagingList
import com.rezazavareh7.movies.ui.util.toDataError
import com.rezazavareh7.ui.util.UiText
import com.rezazavareh7.ui.util.toUiText

@Composable
fun HandlingPagingLoadState(
    vararg categoryLists: MediaCategoryPagingList,
    onRefreshLoading: @Composable () -> Unit,
    onRefreshError: @Composable (UiText?) -> Unit,
) {
    categoryLists.firstNotNullOfOrNull { categoryPagingList ->
        when (val refresh = categoryPagingList.pagingList.loadState.refresh) {
            is LoadState.Loading -> onRefreshLoading()
            is LoadState.Error -> onRefreshError(refresh.toDataError().toUiText())
            else -> Unit
        }
    }
}

@Composable
fun HandlingPagingLoadState(
    pagingListData: LazyPagingItems<MediaData>,
    onAppendLoading: @Composable () -> Unit,
    onAppendError: @Composable (UiText?) -> Unit,
) {
    when (val append = pagingListData.loadState.append) {
        is LoadState.Loading -> onAppendLoading()
        is LoadState.Error -> onAppendError(append.toDataError().toUiText())
        else -> Unit
    }
}
