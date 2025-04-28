package com.rezazavareh7.movies.ui.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.ImageComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.textfield.textfield.CustomTextFieldComponent
import com.rezazavareh7.designsystem.component.textfield.textfield.TextFieldComponentParams
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.ui.movie.component.MovieListItem
import com.rezazavareh7.ui.components.ShowToast

@Composable
fun MoviesScreen(
    movieUiEvent: (MoviesUiEvent) -> Unit,
    moviesUiState: MoviesUiState,
    navigateToMovieDetailsScreen: () -> Unit,
) {
    val context = LocalContext.current
    val lazyRowState = rememberLazyListState()
    if (moviesUiState.errorMessage.isNotEmpty()) {
        ShowToast(context, moviesUiState.errorMessage)
        movieUiEvent(MoviesUiEvent.OnToastMessageShown)
    }
    Scaffold(
        topBar = {
            ToolbarComponent(startContent = {
                TitleLargeTextComponent(
                    text = "Joker Movies",
                )
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
                CustomTextFieldComponent(
                    textFieldComponentParams =
                        TextFieldComponentParams(
                            modifier = Modifier.padding(16.dp),
                            hasPlaceHolder = true,
                            placeHolder = stringResource(id = R.string.search_movie),
                            onValueChange = { value ->
                                if (moviesUiState.movieNameInput.isEmpty() && moviesUiState.hasSearchResult) {
                                    movieUiEvent(MoviesUiEvent.OnGetMoviesCalled)
                                } else {
                                    movieUiEvent(MoviesUiEvent.OnSearchMovieChanged(value))
                                }
                            },
                            value = moviesUiState.movieNameInput,
                            limitationCharacter = 40,
                            hasLeadingIcon = true,
                            leadingIcon = LocalJokerIconPalette.current.icJokerSearch,
                            isLeadingIconClickable = true,
                            clickOnLeadingIcon = {
                                if (moviesUiState.movieNameInput.length >= 2) {
                                    movieUiEvent(MoviesUiEvent.OnSearchedMovie(moviesUiState.movieNameInput))
                                }
                            },
                        ),
                )
                LazyRow(
                    state = lazyRowState,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(moviesUiState.moviesData) { item ->
                        MovieListItem(item)
                    }
                }
            }
        }
    }
}
