package com.rezazavareh7.movies.ui.media.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchHistoryListComponent(
    modifier: Modifier = Modifier,
    historyList: List<String>,
    clickOnItem: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(historyList) { item ->
            SearchHistoryListItemComponent(
                query = item,
                clickOnItem = clickOnItem,
            )
        }
    }
}
