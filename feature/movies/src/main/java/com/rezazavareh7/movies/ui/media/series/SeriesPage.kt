package com.rezazavareh7.movies.ui.media.series

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.rezazavareh7.designsystem.component.searchbar.SearchBarComponent
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.movies.ui.media.component.HandlingPagingLoadState
import com.rezazavareh7.movies.ui.media.component.MediaListComponent
import com.rezazavareh7.movies.ui.media.component.SearchedContentComponent
import com.rezazavareh7.movies.ui.model.MediaCategoryPagingList
import com.rezazavareh7.ui.components.lottie.MediaLoadingAnimation
import com.rezazavareh7.ui.components.showToast
import com.rezazavareh7.movies.R as MediaResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SeriesPage(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: SeriesViewModel = hiltViewModel<SeriesViewModel>(),
    seriesUiEvent: (SeriesUiEvent) -> Unit = viewModel::onEvent,
    seriesUiState: SeriesUiState = viewModel.seriesState.collectAsStateWithLifecycle().value,
    favoriteIds: List<Long>,
    mediaUiEvent: (MediaUiEvent) -> Unit,
    navigateToMediaDetailsScreen: (MediaData, String) -> Unit,
) {
    val context = LocalContext.current
    if (seriesUiState.errorMessage != null) {
        showToast(context, seriesUiState.errorMessage.asString())
        seriesUiEvent(SeriesUiEvent.OnToastMessageShown)
    }

    val searchedSeries = seriesUiState.searchResult.collectAsLazyPagingItems()
    val listPagingCategories =
        arrayOf(
            MediaCategoryPagingList(
                pagingList = seriesUiState.airingTodaySeries.collectAsLazyPagingItems(),
                title = stringResource(MediaResource.string.airing_today),
            ),
            MediaCategoryPagingList(
                pagingList = seriesUiState.topRatedSeries.collectAsLazyPagingItems(),
                title = stringResource(MediaResource.string.top_rated),
            ),
            MediaCategoryPagingList(
                pagingList = seriesUiState.onTheAirSeries.collectAsLazyPagingItems(),
                title = stringResource(MediaResource.string.on_the_air),
            ),
            MediaCategoryPagingList(
                pagingList = seriesUiState.popularSeries.collectAsLazyPagingItems(),
                title = stringResource(MediaResource.string.popular),
            ),
        )
    HandlingPagingLoadState(
        categoryLists = listPagingCategories,
        onRefreshLoading = {
            MediaLoadingAnimation()
        },
        onRefreshError = { errorUiText ->
            seriesUiEvent(SeriesUiEvent.OnShowToast(errorUiText))
        },
    )
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = if (seriesUiState.isSearchBarExpanded) 0.dp else 4.dp),
    ) {
        SearchBarComponent(
            modifier =
                if (seriesUiState.isSearchBarExpanded) {
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 16.dp)
                } else {
                    Modifier.padding(8.dp)
                },
            query = seriesUiState.queryInput,
            maxQueryLength = 30,
            containerColor = if (seriesUiState.isSearchBarExpanded) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.surfaceContainer,
            onQueryChange = { query ->
                seriesUiEvent(SeriesUiEvent.OnSearchQueryChanged(newSeriesName = query))
            },
            onSearch = {
                if (seriesUiState.queryInput.isEmpty()) {
                    seriesUiEvent(SeriesUiEvent.OnSearchBarExpandStateChanged(false))
                } else {
                    seriesUiEvent(SeriesUiEvent.OnSearched(seriesUiState.queryInput))
                }
            },
            onExpandedChange = { isExpanded ->
                seriesUiEvent(SeriesUiEvent.OnSearchBarExpandStateChanged(isExpanded))
            },
            isExpanded = seriesUiState.isSearchBarExpanded,
            placeHolder = stringResource(MediaResource.string.search_series),
            content = {
                SearchedContentComponent(
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 8.dp, bottom = 20.dp),
                    groupName = stringResource(MediaResource.string.searched),
                    mediaList = searchedSeries,
                    shouldShowHistoryQueries = seriesUiState.shouldShowHistoryQueries && seriesUiState.searchQueriesHistory.isNotEmpty(),
                    historyQueryList = seriesUiState.searchQueriesHistory,
                    hasSearchResult = seriesUiState.hasSearched,
                    favoriteIds = favoriteIds,
                    mediaUiEvent = mediaUiEvent,
                    onItemClicked = navigateToMediaDetailsScreen,
                    clickOnQueryItem = { query ->
                        seriesUiEvent(SeriesUiEvent.OnSearched(query))
                    },
                    onShowToast = { errorMessage ->
                        seriesUiEvent(SeriesUiEvent.OnShowToast(errorMessage))
                    },
                )
            },
        )
        if (!seriesUiState.isSearchBarExpanded) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) {
                listPagingCategories.forEach { mediaCategoryPagingList ->
                    item {
                        MediaListComponent(
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            groupName = mediaCategoryPagingList.title,
                            mediaPagingList = mediaCategoryPagingList.pagingList,
                            favoriteIds = favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            onItemClicked = navigateToMediaDetailsScreen,
                            onShowError = { errorMessage ->
                                seriesUiEvent(SeriesUiEvent.OnShowToast(errorMessage))
                            },
                        )
                    }
                }
            }
        }
    }
}
