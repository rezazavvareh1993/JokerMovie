package com.rezazavareh7.movies.ui.media.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.movies.ui.media.component.MediaListComponent
import com.rezazavareh7.ui.components.showToast

@Composable
fun MoviesPage(
    viewModel: MoviesViewModel = hiltViewModel<MoviesViewModel>(),
    movieUiEvent: (MoviesUiEvent) -> Unit = viewModel::onEvent,
    moviesUiState: MoviesUiState = viewModel.moviesState.collectAsStateWithLifecycle().value,
    mediaUiEvent: (MediaUiEvent) -> Unit,
    navigateToMediaDetailsScreen: (Long, String) -> Unit,
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
            modifier = Modifier.padding(vertical = 16.dp),
            query = moviesUiState.movieNameInput,
            maxQueryLength = 30,
            onQueryChange = { query ->
                movieUiEvent(MoviesUiEvent.OnSearchMovieChanged(newMovieName = query))
            },
            onSearch = {
                if (moviesUiState.movieNameInput.isEmpty() && moviesUiState.hasSearchResult) {
                    movieUiEvent(MoviesUiEvent.OnCancelSearch)
                } else {
                    movieUiEvent(MoviesUiEvent.OnSearchedMovie(moviesUiState.movieNameInput))
                }
            },
            placeHolder = stringResource(R.string.search_movie),
            content = {
            },
        )
        if (topRatedMovies.loadState.refresh is LoadState.Loading ||
            nowPlayingMovies.loadState.refresh is LoadState.Loading ||
            upcomingMovies.loadState.refresh is LoadState.Loading ||
            popularMovies.loadState.refresh is LoadState.Loading
        ) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                state = rememberLazyListState(),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) {
                if (moviesUiState.hasSearchResult && searchedMovies.itemCount > 0) {
                    item {
                        MediaListComponent(
                            title = stringResource(R.string.upcoming),
                            movies = searchedMovies,
                            favoriteIds = moviesUiState.favoriteIds,
                            mediaUiEvent = mediaUiEvent,
                            onItemClicked = navigateToMediaDetailsScreen,
                        )
                    }
                } else {
                    if (upcomingMovies.itemCount > 0) {
                        item {
                            MediaListComponent(
                                title = stringResource(R.string.upcoming),
                                movies = upcomingMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                mediaUiEvent = mediaUiEvent,
                                onItemClicked = navigateToMediaDetailsScreen,
                            )
                        }
                    }
                    if (topRatedMovies.itemCount > 0) {
                        item {
                            MediaListComponent(
                                title = stringResource(R.string.top_rated),
                                movies = topRatedMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                mediaUiEvent = mediaUiEvent,
                                onItemClicked = navigateToMediaDetailsScreen,
                            )
                        }
                    }

                    if (nowPlayingMovies.itemCount > 0) {
                        item {
                            MediaListComponent(
                                title = stringResource(R.string.now_playing),
                                movies = nowPlayingMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                mediaUiEvent = mediaUiEvent,
                                onItemClicked = navigateToMediaDetailsScreen,
                            )
                        }
                    }

                    if (popularMovies.itemCount > 0) {
                        item {
                            MediaListComponent(
                                title = stringResource(R.string.popular),
                                movies = popularMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                mediaUiEvent = mediaUiEvent,
                                onItemClicked = navigateToMediaDetailsScreen,
                            )
                        }
                    }
                }
            }
        }
    }
}
