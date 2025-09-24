package com.rezazavareh7.movies.ui.media.series

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rezazavareh7.designsystem.component.searchbar.SearchBarComponent
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.movies.ui.media.component.MediaListComponent
import com.rezazavareh7.movies.ui.media.component.SearchedContentComponent
import com.rezazavareh7.ui.components.lottie.LottieAnimationComponent
import com.rezazavareh7.ui.components.showToast
import com.rezazavareh7.designsystem.R as DesignSystemResource
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
    navigateToMediaDetailsScreen: (Long, String, String) -> Unit,
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
                )
            },
        )
        if (topRatedSeries.loadState.refresh is LoadState.Loading ||
            airingTodaySeries.loadState.refresh is LoadState.Loading ||
            onTheAirSeries.loadState.refresh is LoadState.Loading ||
            popularSeries.loadState.refresh is LoadState.Loading
        ) {
            LottieAnimationComponent(
                lottieResource = DesignSystemResource.raw.lottie_video_loading,
                modifier = Modifier.fillMaxSize(),
            )
        } else if (!seriesUiState.isSearchBarExpanded) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) {
                item {
                    MediaListComponent(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        groupName = stringResource(MediaResource.string.airing_today),
                        mediaList = airingTodaySeries,
                        favoriteIds = favoriteIds,
                        mediaUiEvent = mediaUiEvent,
                        onItemClicked = navigateToMediaDetailsScreen,
                    )
                }
                item {
                    MediaListComponent(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        groupName = stringResource(MediaResource.string.top_rated),
                        mediaList = topRatedSeries,
                        favoriteIds = favoriteIds,
                        mediaUiEvent = mediaUiEvent,
                        onItemClicked = navigateToMediaDetailsScreen,
                    )
                }
                item {
                    MediaListComponent(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        groupName = stringResource(MediaResource.string.on_the_air),
                        mediaList = onTheAirSeries,
                        favoriteIds = favoriteIds,
                        mediaUiEvent = mediaUiEvent,
                        onItemClicked = navigateToMediaDetailsScreen,
                    )
                }
                item {
                    MediaListComponent(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        groupName = stringResource(MediaResource.string.popular),
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
