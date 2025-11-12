package com.rezazavareh7.movies.ui.media.movie

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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rezazavareh7.designsystem.component.searchbar.SearchBarComponent
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.movies.ui.media.component.MediaListComponent
import com.rezazavareh7.movies.ui.media.component.SearchedContentComponent
import com.rezazavareh7.movies.ui.util.exceptionHandling
import com.rezazavareh7.ui.components.lottie.MediaLoadingAnimation
import com.rezazavareh7.ui.components.showToast
import com.rezazavareh7.movies.R as MediaResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesPage(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: MoviesViewModel = hiltViewModel<MoviesViewModel>(),
    movieUiEvent: (MoviesUiEvent) -> Unit = viewModel::onEvent,
    moviesUiState: MoviesUiState = viewModel.moviesState.collectAsStateWithLifecycle().value,
    favoriteIds: List<Long>,
    mediaUiEvent: (MediaUiEvent) -> Unit,
    navigateToMediaDetailsScreen: (MediaData, String) -> Unit,
) {
    val topRatedMovies = moviesUiState.topRatedMovies.collectAsLazyPagingItems()
    val upcomingMovies = moviesUiState.upcomingMovies.collectAsLazyPagingItems()
    val popularMovies = moviesUiState.popularMovies.collectAsLazyPagingItems()
    val nowPlayingMovies = moviesUiState.nowPlayingMovies.collectAsLazyPagingItems()
    val searchedMovies = moviesUiState.searchResult.collectAsLazyPagingItems()
    val context = LocalContext.current
    if (moviesUiState.errorMessage.isNotEmpty()) {
        showToast(context, moviesUiState.errorMessage)
        movieUiEvent(MoviesUiEvent.OnToastMessageShown)
    }
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = if (moviesUiState.isSearchBarExpanded) 0.dp else 4.dp),
    ) {
        SearchBarComponent(
            modifier =
                if (moviesUiState.isSearchBarExpanded) {
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 16.dp)
                } else {
                    Modifier.padding(8.dp)
                },
            query = moviesUiState.queryInput,
            maxQueryLength = 30,
            containerColor = if (moviesUiState.isSearchBarExpanded) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.surfaceContainer,
            onQueryChange = { query ->
                movieUiEvent(MoviesUiEvent.OnSearchQueryChanged(newMovieName = query))
            },
            onSearch = {
                if (moviesUiState.queryInput.isEmpty()) {
                    movieUiEvent(MoviesUiEvent.OnSearchBarExpandStateChanged(false))
                } else {
                    movieUiEvent(MoviesUiEvent.OnSearched(moviesUiState.queryInput))
                }
            },
            placeHolder = stringResource(MediaResource.string.search_movie),
            onExpandedChange = { isExpanded ->
                movieUiEvent(MoviesUiEvent.OnSearchBarExpandStateChanged(isExpanded))
            },
            isExpanded = moviesUiState.isSearchBarExpanded,
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
                    mediaList = searchedMovies,
                    shouldShowHistoryQueries = moviesUiState.shouldShowHistoryQueries && moviesUiState.searchQueriesHistory.isNotEmpty(),
                    historyQueryList = moviesUiState.searchQueriesHistory,
                    hasSearchResult = moviesUiState.hasSearched,
                    favoriteIds = favoriteIds,
                    mediaUiEvent = mediaUiEvent,
                    onItemClicked = navigateToMediaDetailsScreen,
                    clickOnQueryItem = { query ->
                        movieUiEvent(MoviesUiEvent.OnSearched(query))
                    },
                )
            },
        )
        when {
            topRatedMovies.loadState.refresh is LoadState.Loading ||
                nowPlayingMovies.loadState.refresh is LoadState.Loading ||
                upcomingMovies.loadState.refresh is LoadState.Loading ||
                popularMovies.loadState.refresh is LoadState.Loading -> {
                MediaLoadingAnimation()
            }

            topRatedMovies.loadState.refresh is LoadState.Error &&
                nowPlayingMovies.loadState.refresh is LoadState.Error &&
                upcomingMovies.loadState.refresh is LoadState.Error &&
                popularMovies.loadState.refresh is LoadState.Error -> {
                showToast(
                    context,
                    exceptionHandling((topRatedMovies.loadState.refresh as LoadState.Error).error),
                )
            }

            else ->
                if (!moviesUiState.isSearchBarExpanded) {
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
                                groupName = stringResource(MediaResource.string.upcoming),
                                mediaList = upcomingMovies,
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
                                mediaList = topRatedMovies,
                                favoriteIds = favoriteIds,
                                mediaUiEvent = mediaUiEvent,
                                onItemClicked = navigateToMediaDetailsScreen,
                            )
                        }

                        item {
                            MediaListComponent(
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope,
                                groupName = stringResource(MediaResource.string.now_playing),
                                mediaList = nowPlayingMovies,
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
                                mediaList = popularMovies,
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
