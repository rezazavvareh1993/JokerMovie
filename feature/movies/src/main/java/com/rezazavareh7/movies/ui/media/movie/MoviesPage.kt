package com.rezazavareh7.movies.ui.media.movie

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.searchbar.SearchBarComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.movies.ui.media.component.MediaListComponent
import com.rezazavareh7.ui.components.lottie.LottieAnimationComponent
import com.rezazavareh7.ui.components.showToast
import com.rezazavareh7.designsystem.R as DesignSystemResource
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
    navigateToMediaDetailsScreen: (Long, String, String) -> Unit,
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
                .padding(horizontal = 4.dp),
    ) {
        SearchBarComponent(
            modifier = Modifier.padding(16.dp),
            query = moviesUiState.movieQueryInput,
            maxQueryLength = 30,
            onQueryChange = { query ->
                movieUiEvent(MoviesUiEvent.OnMovieQueryChanged(newMovieName = query))
            },
            onSearch = {
                if (moviesUiState.movieQueryInput.isEmpty()) {
                    movieUiEvent(MoviesUiEvent.OnSearchBarExpandStateChanged(false))
                } else {
                    movieUiEvent(MoviesUiEvent.OnSearchedMovie(moviesUiState.movieQueryInput))
                }
            },
            placeHolder = stringResource(MediaResource.string.search_movie),
            onExpandedChange = { isExpanded ->
                movieUiEvent(MoviesUiEvent.OnSearchBarExpandStateChanged(isExpanded))
            },
            isExpanded = moviesUiState.isSearchBarExpanded,
            content = {
                SearchHistoryListComponent(
                    modifier =
                        Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    historyList = moviesUiState.movieSearchHistory,
                    clickOnItem = { query ->
                        movieUiEvent(MoviesUiEvent.OnSearchedMovie(query))
                    },
                )
            },
        )
        if (topRatedMovies.loadState.refresh is LoadState.Loading ||
            nowPlayingMovies.loadState.refresh is LoadState.Loading ||
            upcomingMovies.loadState.refresh is LoadState.Loading ||
            popularMovies.loadState.refresh is LoadState.Loading
        ) {
            LottieAnimationComponent(
                lottieResource = DesignSystemResource.raw.lottie_video_loading,
                modifier = Modifier.fillMaxSize(),
            )
//            CircularProgressBarComponent(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                state = rememberLazyListState(),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) {
                if (moviesUiState.hasSearchResult) {
                    item {
                        MediaListComponent(
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            groupName = stringResource(MediaResource.string.upcoming),
                            mediaList = searchedMovies,
                            favoriteIds = favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            isInSearchMode = true,
                            onItemClicked = navigateToMediaDetailsScreen,
                        )
                    }
                } else {
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

@Composable
fun SearchHistoryListItemComponent(
    query: String,
    clickOnItem: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .clickable {
                    clickOnItem(query)
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconComponent(
            drawableId = LocalJokerIconPalette.current.icHistory,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            boxSize = 20.dp,
            iconSize = 20.dp,
        )
        Spacer(Modifier.width(8.dp))
        TitleMediumTextComponent(
            text = query,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
