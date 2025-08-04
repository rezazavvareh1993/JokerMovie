package com.rezazavareh7.movies.ui.movie

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.rezazavareh7.designsystem.component.textfield.outlinetextfield.OutlineTextFieldComponent
import com.rezazavareh7.designsystem.component.textfield.outlinetextfield.OutlineTextFieldComponentParams
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.ui.movie.component.MovieListItem
import com.rezazavareh7.ui.components.ShowToast

@Composable
fun MoviesScreen(
    movieUiEvent: (MoviesUiEvent) -> Unit,
    moviesUiState: MoviesUiState,
    navigateToMovieDetailsScreen: (Long) -> Unit,
    movies: LazyPagingItems<MovieData>,
) {
    val pagedMovies = moviesUiState.moviesPagedData.collectAsLazyPagingItems()
    val context = LocalContext.current
    val lazyRowState = rememberLazyListState()
    if (moviesUiState.errorMessage.isNotEmpty()) {
        ShowToast(context, moviesUiState.errorMessage)
        movieUiEvent(MoviesUiEvent.OnToastMessageShown)
    }

    LaunchedEffect(movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast
                .makeText(
                    context,
                    "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_LONG,
                ).show()
        }
    }
    Scaffold(
        topBar = {
            ToolbarComponent(startContent = {
                IconComponent(
                    drawableId = LocalJokerIconPalette.current.icMainLogo,
                    modifier = Modifier.padding(),
                )
                TitleCustomTextComponent(text = "Joker Movies")
            })
        },
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            ImageComponent(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painterId = LocalJokerIconPalette.current.imgJokerBackground,
            )
            Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)) {
                OutlineTextFieldComponent(
                    outlineTextFieldComponentParams =
                        OutlineTextFieldComponentParams(
                            modifier = Modifier.padding(16.dp),
                            hasPlaceHolder = true,
                            placeHolder = stringResource(id = R.string.search_movie),
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
                if (movies.loadState.refresh is LoadState.Loading) {
                    CircularProgressIndicator()
                } else {
                    LazyRow(
                        state = lazyRowState,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(pagedMovies.itemCount) { index ->
                            val item = pagedMovies[index]
                            item?.let {
                                MovieListItem(item) { movieId ->
                                    navigateToMovieDetailsScreen(movieId)
                                }
                            }
                        }

                        pagedMovies.apply {
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
                                        Text("Error")
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}
