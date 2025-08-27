package com.rezazavareh7.movies.ui.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.searchbar.SearchBarComponent
import com.rezazavareh7.designsystem.component.text.title.TitleCustomTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.Category
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.ui.movie.component.MovieListItem
import com.rezazavareh7.ui.components.showToast

@Composable
fun MoviesScreen(
    movieUiEvent: (MoviesUiEvent) -> Unit,
    moviesUiState: MoviesUiState,
    navigateToMovieDetailsScreen: (Long) -> Unit,
    navigateToFavoriteScreen: (String) -> Unit,
    navigateToSetting: () -> Unit,
) {
    val topRatedMovies = moviesUiState.topRatedMovies.collectAsLazyPagingItems()
    val upcomingMovies = moviesUiState.upcomingMovies.collectAsLazyPagingItems()
    val popularMovies = moviesUiState.popularMovies.collectAsLazyPagingItems()
    val nowPlayingMovies = moviesUiState.nowPlayingMovies.collectAsLazyPagingItems()
    val context = LocalContext.current
    if (moviesUiState.errorMessage.isNotEmpty()) {
        showToast(context, moviesUiState.errorMessage)
        movieUiEvent(MoviesUiEvent.OnToastMessageShown)
    }

    Scaffold(
        topBar = {
            ToolbarComponent(startContent = {
                IconComponent(drawableId = LocalJokerIconPalette.current.icMainLogo)
                TitleCustomTextComponent(text = stringResource(R.string.toolbar_title))
            }, endContent = {
                IconComponent(
                    drawableId = LocalJokerIconPalette.current.icLike,
                    isClickable = true,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        navigateToFavoriteScreen(Category.MOVIE.toString())
                    },
                )
                Spacer(modifier = Modifier.width(12.dp))
                IconComponent(
                    drawableId = LocalJokerIconPalette.current.icSetting,
                    isClickable = true,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = navigateToSetting,
                )
            })
        },
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding),
        ) {
            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                SearchBarComponent(
                    modifier = Modifier.padding(16.dp),
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
                                .weight(1f)
                                .fillMaxWidth(),
                    ) {
                        item {
                            MovieList(
                                title = stringResource(R.string.upcoming),
                                movies = upcomingMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                movieUiEvent = movieUiEvent,
                                onMovieClicked = navigateToMovieDetailsScreen,
                            )
                        }
                        item {
                            MovieList(
                                title = stringResource(R.string.top_rated),
                                movies = topRatedMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                movieUiEvent = movieUiEvent,
                                onMovieClicked = navigateToMovieDetailsScreen,
                            )
                        }

                        item {
                            MovieList(
                                title = stringResource(R.string.now_playing),
                                movies = nowPlayingMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                movieUiEvent = movieUiEvent,
                                onMovieClicked = navigateToMovieDetailsScreen,
                            )
                        }

                        item {
                            MovieList(
                                title = stringResource(R.string.popular),
                                movies = popularMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                movieUiEvent = movieUiEvent,
                                onMovieClicked = navigateToMovieDetailsScreen,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieList(
    title: String,
    movies: LazyPagingItems<MovieData>,
    favoriteIds: List<Long>,
    onMovieClicked: (Long) -> Unit,
    movieUiEvent: (MoviesUiEvent) -> Unit,
) {
    TitleMediumTextComponent(text = title, modifier = Modifier.padding(start = 16.dp))
    LazyRow(
        state = rememberLazyListState(),
        modifier =
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(movies.itemCount) { index ->
            val item = movies[index]
            item?.let {
                MovieListItem(
                    item,
                    isLiked = favoriteIds.contains(item.id),
                    onFavoriteClicked = { isLiked, movieItem ->
                        if (isLiked) {
                            movieUiEvent(MoviesUiEvent.OnLikeMovie(movieItem))
                        } else {
                            movieUiEvent(
                                MoviesUiEvent.OnDislikeMovie(
                                    movieItem,
                                ),
                            )
                        }
                    },
                    onMovieClicked = onMovieClicked,
                )
            }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading ->
                    item {
                        CircularProgressIndicator()
                    }

                loadState.append is LoadState.Loading ->
                    item {
                        CircularProgressIndicator()
                    }

                loadState.refresh is LoadState.Error ->
                    item {
                        showToast(
                            LocalContext.current,
                            (movies.loadState.refresh as LoadState.Error).error.message.toString(),
                        )
                    }
            }
        }
    }
}
