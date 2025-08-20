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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleSmallTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.ui.components.showToast
import com.rezazavareh7.ui.glide.ShowGlideImageByUrl

@Composable
fun MovieDetailsScreen(
    movieId: Long,
    movieDetailsUiEvent: (MovieDetailsUiEvent) -> Unit,
    movieDetailsUiState: MovieDetailsUiState,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    if (movieDetailsUiState.errorMessage.isNotEmpty()) {
        showToast(context, movieDetailsUiState.errorMessage)
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
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 8.dp),
        ) {
            movieDetailsUiState.movieDetailsData?.let { data ->
                with(data) {

                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .clip(Shape.highRoundCorner)
                                    .padding(vertical = 16.dp),
                        ) {
                            ShowGlideImageByUrl(
                                modifier =
                                    Modifier
                                        .matchParentSize()
                                        .clip(Shape.highRoundCorner),
                                imageUrlPath = backdrop,
                                context = LocalContext.current,
                            )

                    }

                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize(),
                        ) {
                            ShowGlideImageByUrl(
                                modifier =
                                    Modifier
                                        .matchParentSize(),
                                imageUrlPath = poster,
                                context = LocalContext.current,
                            )
                            Box(
                                modifier =
                                    Modifier
                                        .matchParentSize()
                                        .background(
                                            brush =
                                                Brush.verticalGradient(
                                                    0.5f to
                                                            Color.Black.copy(
                                                                alpha = 0.7f,
                                                            ),
                                                    0.85f to
                                                            Color.Black.copy(
                                                                alpha = 0.2f,
                                                            ),
                                                    1f to Color.Transparent,
                                                ),
                                        ),
                            )

                            Column(modifier = Modifier) {
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
                                    Column(
                                        modifier =
                                            Modifier
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
