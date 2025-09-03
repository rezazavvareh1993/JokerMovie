package com.rezazavareh7.movies.ui.media.series

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rezazavareh7.designsystem.component.progressbar.CircularProgressBarComponent
import com.rezazavareh7.designsystem.component.searchbar.SearchBarComponent
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.movies.ui.media.component.MediaListComponent
import com.rezazavareh7.ui.components.showToast

@Composable
fun SeriesPage(
    viewModel: SeriesViewModel = hiltViewModel<SeriesViewModel>(),
    seriesUiEvent: (SeriesUiEvent) -> Unit = viewModel::onEvent,
    seriesUiState: SeriesUiState = viewModel.seriesState.collectAsStateWithLifecycle().value,
    favoriteIds: List<Long>,
    mediaUiEvent: (MediaUiEvent) -> Unit,
    navigateToMediaDetailsScreen: (Long, String) -> Unit,
) {
    val topRatedSeries = seriesUiState.topRatedSeries.collectAsLazyPagingItems()
    val onTheAirSeries = seriesUiState.onTheAirSeries.collectAsLazyPagingItems()
    val popularSeries = seriesUiState.popularSeries.collectAsLazyPagingItems()
    val airingTodaySeries = seriesUiState.airingTodaySeries.collectAsLazyPagingItems()
    val searchedSeries = seriesUiState.searchResult.collectAsLazyPagingItems()
    val context = LocalContext.current
    if (seriesUiState.errorMessage.isNotEmpty()) {
        showToast(context, seriesUiState.errorMessage)
        seriesUiEvent(SeriesUiEvent.OnToastMessageShown)
    }
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 4.dp),
    ) {
        SearchBarComponent(
            modifier = Modifier.padding(16.dp),
            query = seriesUiState.seriesNameInput,
            maxQueryLength = 30,
            onQueryChange = { query ->
                seriesUiEvent(SeriesUiEvent.OnSearchQueryChanged(newMovieName = query))
            },
            onSearch = {
                if (seriesUiState.seriesNameInput.isEmpty() && seriesUiState.hasSearchResult) {
                    seriesUiEvent(SeriesUiEvent.OnCancelSearch)
                } else {
                    seriesUiEvent(SeriesUiEvent.OnSearched(seriesUiState.seriesNameInput))
                }
            },
            placeHolder = stringResource(R.string.search_series),
            content = {
            },
        )
        if (topRatedSeries.loadState.refresh is LoadState.Loading ||
            airingTodaySeries.loadState.refresh is LoadState.Loading ||
            onTheAirSeries.loadState.refresh is LoadState.Loading ||
            popularSeries.loadState.refresh is LoadState.Loading
        ) {
            CircularProgressBarComponent(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                state = rememberLazyListState(),
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                if (seriesUiState.hasSearchResult) {
                    item {
                        MediaListComponent(
                            title = stringResource(R.string.searched),
                            mediaList = searchedSeries,
                            favoriteIds = favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            onItemClicked = navigateToMediaDetailsScreen,
                        )
                    }
                } else {
                    item {
                        MediaListComponent(
                            title = stringResource(R.string.airing_today),
                            mediaList = airingTodaySeries,
                            favoriteIds = favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            onItemClicked = navigateToMediaDetailsScreen,
                        )
                    }
                    item {
                        MediaListComponent(
                            title = stringResource(R.string.top_rated),
                            mediaList = topRatedSeries,
                            favoriteIds = favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            onItemClicked = navigateToMediaDetailsScreen,
                        )
                    }
                    item {
                        MediaListComponent(
                            title = stringResource(R.string.on_the_air),
                            mediaList = onTheAirSeries,
                            favoriteIds = favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            onItemClicked = navigateToMediaDetailsScreen,
                        )
                    }
                    item {
                        MediaListComponent(
                            title = stringResource(R.string.popular),
                            mediaList = popularSeries,
                            favoriteIds = favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            onItemClicked = navigateToMediaDetailsScreen,
                        )
                    }
                }
            }
        }
    }
}
