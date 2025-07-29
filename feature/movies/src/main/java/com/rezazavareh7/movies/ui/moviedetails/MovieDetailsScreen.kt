package com.rezazavareh7.movies.ui.moviedetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.icon.ImageComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleSmallTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.ui.components.ShowToast
import com.rezazavareh7.ui.glide.ShowGlideImageByUrl

@Composable
fun MovieDetailsScreen(
    movieId: Long,
    movieDetailsUiEvent: (MovieDetailsUiEvent) -> Unit,
    movieDetailsUiState: MovieDetailsUiState,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()
    if (movieDetailsUiState.errorMessage.isNotEmpty()) {
        ShowToast(context, movieDetailsUiState.errorMessage)
        movieDetailsUiEvent(MovieDetailsUiEvent.OnToastMessageShown)
    }

    LaunchedEffect(movieId) {
        movieDetailsUiEvent(MovieDetailsUiEvent.OnGetMovieDetailsCalled(movieId))
    }

    Scaffold(
        topBar = {
            ToolbarComponent(
                hasBackButton = true,
                onBackClicked = onBackClicked,
                startContent = {
                    IconComponent(
                        drawableId = LocalJokerIconPalette.current.icMainLogo,
                        modifier = Modifier.padding(vertical = 2.dp),
                    )
                    TitleLargeTextComponent(
                        text = "Details",
                    )
                },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            ImageComponent(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painterId = LocalJokerIconPalette.current.imgJokerBackground,
            )
            LazyColumn {
                item {
                    Column(
                        modifier =
                            Modifier
                                .padding(padding)
                                .padding(horizontal = 8.dp),
                    ) {
                        if (movieDetailsUiState.movieDetailsData != null) {
                            TitleLargeTextComponent(
                                text = movieDetailsUiState.movieDetailsData.name,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(8.dp),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    modifier =
                                        Modifier
                                            .weight(1f)
                                            .height(350.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = Shape.highRoundCorner,
                                            ),
                                ) {
                                    ShowGlideImageByUrl(
                                        modifier =
                                            Modifier
                                                .fillMaxSize()
                                                .clip(Shape.highRoundCorner),
                                        imageUrlPath = movieDetailsUiState.movieDetailsData.banner,
                                        contentScale = ContentScale.FillBounds,
                                        context = LocalContext.current,
                                        placeHolder = LocalJokerIconPalette.current.icMovie,
                                    )
                                }
                                Column(
                                    modifier =
                                        Modifier
                                            .weight(1f)
                                            .padding(start = 8.dp),
                                ) {
                                    Spacer(Modifier.height(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        TitleSmallTextComponent(text = "Release Date: ")
                                        BodyMediumTextComponent(movieDetailsUiState.movieDetailsData.releaseDate)
                                    }
                                    Spacer(Modifier.height(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        TitleSmallTextComponent(text = "Rate: ")
                                        BodyMediumTextComponent(movieDetailsUiState.movieDetailsData.rate.toString())
                                    }
                                    Spacer(Modifier.height(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        TitleSmallTextComponent(text = "Vote Count: ")
                                        BodyMediumTextComponent(movieDetailsUiState.movieDetailsData.voteCount.toString())
                                    }
                                    Spacer(Modifier.height(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        TitleSmallTextComponent(text = "Genres: ")
                                        var genres = ""
                                        movieDetailsUiState.movieDetailsData.genres.forEachIndexed { index, item ->
                                            genres +=
                                                if (index == movieDetailsUiState.movieDetailsData.genres.lastIndex) {
                                                    item
                                                } else {
                                                    "$item,"
                                                }
                                        }
                                        BodyMediumTextComponent(genres)
                                    }
                                }
                            }

                            TitleMediumTextComponent(text = "Overview: ")
                            Spacer(Modifier.height(8.dp))
                            BodyMediumTextComponent(text = movieDetailsUiState.movieDetailsData.overview)
                        }
                    }
                }
            }
        }
    }

    BackHandler {
        onBackClicked()
    }
}
