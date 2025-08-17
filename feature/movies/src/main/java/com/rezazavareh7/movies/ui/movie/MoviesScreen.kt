package com.rezazavareh7.movies.ui.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.icon.ImageComponent
import com.rezazavareh7.designsystem.component.text.title.TitleCustomTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.textfield.outlinetextfield.OutlineTextFieldComponent
import com.rezazavareh7.designsystem.component.textfield.outlinetextfield.OutlineTextFieldComponentParams
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.ui.movie.component.MovieListItem
import com.rezazavareh7.ui.components.showToast

@Composable
fun MoviesScreen(
    movieUiEvent: (MoviesUiEvent) -> Unit,
    moviesUiState: MoviesUiState,
    navigateToMovieDetailsScreen: (Long) -> Unit,
    navigateToFavoriteScreen: () -> Unit,
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

//    LaunchedEffect(topRatedMovies.loadState) {
//        if (topRatedMovies.loadState.refresh is LoadState.Error)
//            showToast(
//                context,
//                (topRatedMovies.loadState.refresh as LoadState.Error).error.message.toString()
//            )
//    }
//    LaunchedEffect(upcomingMovies.loadState) {
//        if (upcomingMovies.loadState.refresh is LoadState.Error)
//            showToast(
//                context,
//                (upcomingMovies.loadState.refresh as LoadState.Error).error.message.toString()
//            )
//    }
//    LaunchedEffect(popularMovies.loadState) {
//        if (popularMovies.loadState.refresh is LoadState.Error)
//            showToast(
//                context,
//                (popularMovies.loadState.refresh as LoadState.Error).error.message.toString()
//            )
//    }
//    LaunchedEffect(nowPlayingMovies.loadState) {
//        if (nowPlayingMovies.loadState.refresh is LoadState.Error)
//            showToast(
//                context,
//                (nowPlayingMovies.loadState.refresh as LoadState.Error).error.message.toString()
//            )
//    }

    Scaffold(
        topBar = {
            ToolbarComponent(startContent = {
                IconComponent(
                    drawableId = LocalJokerIconPalette.current.icMainLogo,
                    modifier = Modifier.padding(),
                )
                TitleCustomTextComponent(text = "Joker Movies")
            }, endContent = {
                TitleMediumTextComponent(
                    text = stringResource(com.rezazavareh7.designsystem.R.string.favorite),
                    modifier =
                        Modifier.clickable {
                            navigateToFavoriteScreen()
                        },
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
            ImageComponent(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painterId = LocalJokerIconPalette.current.imgJokerBackground,
            )
            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                OutlineTextFieldComponent(
                    outlineTextFieldComponentParams =
                        OutlineTextFieldComponentParams(
                            modifier = Modifier.padding(16.dp),
                            hasPlaceHolder = true,
                            placeHolder = stringResource(id = R.string.search_movie),
                            autoFocus = false,
                            onValueChange = { value ->
                                if (moviesUiState.movieNameInput.isEmpty() && moviesUiState.hasSearchResult) {
                                    movieUiEvent(MoviesUiEvent.OnCancelSearch)
                                } else {
                                    movieUiEvent(MoviesUiEvent.OnSearchMovieChanged(value))
                                }
                            },
                            value = moviesUiState.movieNameInput,
                            limitationCharacter = 40,
                            hasLeadingIcon = true,
                            leadingIcon = LocalJokerIconPalette.current.icSearch,
                            isLeadingIconClickable = true,
                            isTrailingIconClickable = true,
                            hasTrailingIcon = moviesUiState.movieNameInput.length >= 2,
                            trailingIcon = LocalJokerIconPalette.current.icCancel,
                            clickOnTrailingIcon = {
                                movieUiEvent(MoviesUiEvent.OnCancelSearch)
                            },
                            clickOnLeadingIcon = {
                                if (moviesUiState.movieNameInput.length >= 2) {
                                    movieUiEvent(MoviesUiEvent.OnSearchedMovie(moviesUiState.movieNameInput))
                                }
                            },
                        ),
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
                                title = "Upcoming",
                                movies = upcomingMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                movieUiEvent = movieUiEvent,
                                onMovieClicked = navigateToMovieDetailsScreen,
                            )
                        }
                        item {
                            MovieList(
                                title = "Top Rated",
                                movies = topRatedMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                movieUiEvent = movieUiEvent,
                                onMovieClicked = navigateToMovieDetailsScreen,
                            )
                        }

                        item {
                            MovieList(
                                title = "Now Playing",
                                movies = nowPlayingMovies,
                                favoriteIds = moviesUiState.favoriteIds,
                                movieUiEvent = movieUiEvent,
                                onMovieClicked = navigateToMovieDetailsScreen,
                            )
                        }

                        item {
                            MovieList(
                                title = "Popular",
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
    TitleMediumTextComponent(text = title)
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
