package com.rezazavareh7.movies.ui.moviedetails

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rezazavareh7.common.util.extensions.formattedStringOneDecimal
import com.rezazavareh7.designsystem.component.divider.HorizontalDividerComponent
import com.rezazavareh7.designsystem.component.icon.CircleIconBoxComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleSmallTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.ui.moviedetails.component.CreditListItemComponent
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl
import com.rezazavareh7.ui.components.showToast

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaDetailsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    groupName: String,
    mediaId: Long,
    mediaCategory: MediaCategory,
    mediaDetailsUiEvent: (MediaDetailsUiEvent) -> Unit,
    mediaDetailsUiState: MovieDetailsUiState,
    onBackClicked: () -> Unit,
    navigateToMediaImages: (Long, MediaCategory) -> Unit,
) {
    val context = LocalContext.current
    if (mediaDetailsUiState.errorMessage.isNotEmpty()) {
        showToast(context, mediaDetailsUiState.errorMessage)
        mediaDetailsUiEvent(MediaDetailsUiEvent.OnToastMessageShown)
    }

    LaunchedEffect(mediaId) {
        mediaDetailsUiEvent(MediaDetailsUiEvent.OnGetMediaDetailsCalled(mediaId, mediaCategory))
    }

    Scaffold(
        topBar = {
            ToolbarComponent(
                hasBackButton = true,
                onBackClicked = onBackClicked,
                startContent = {
                    TitleLargeTextComponent(
                        text = stringResource(R.string.details),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        with(sharedTransitionScope) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
            ) {
                mediaDetailsUiState.movieDetailsData?.let { data ->
                    with(data) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .clickable { navigateToMediaImages(mediaId, mediaCategory) }
                                    .clip(Shape.highRoundCornerTop),
                        ) {
                            ShowGlideImageByUrl(
                                modifier = Modifier.matchParentSize(),
                                imageUrlPath = backdrop,
                                context = LocalContext.current,
                            )
                            CircleIconBoxComponent(
                                modifier =
                                    Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(16.dp),
                                icon = LocalJokerIconPalette.current.icGallery,
                                borderColor = Color.Unspecified,
                                backgroundColor = Color.Black.copy(alpha = 0.8f),
                                iconTint = Color.White,
                                iconSize = 20.dp,
                                boxSize = 42.dp,
                                isClickable = true,
                                onClick = { navigateToMediaImages(mediaId, mediaCategory) },
                            )
                        }

                        Box(modifier = Modifier.fillMaxSize()) {
                            ShowGlideImageByUrl(
                                modifier =
                                    Modifier
                                        .matchParentSize()
                                        .sharedElement(
                                            sharedContentState = rememberSharedContentState(key = "poster$groupName$id"),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            renderInOverlayDuringTransition = false,
                                        ),
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
                                                        MaterialTheme.colorScheme.surface.copy(
                                                            alpha = 0.9f,
                                                        ),
                                                    1f to
                                                        MaterialTheme.colorScheme.surface.copy(
                                                            alpha = 0.2f,
                                                        ),
                                                    1f to Color.Transparent,
                                                ),
                                        ),
                            )

                            Column(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp),
                            ) {
                                TitleLargeTextComponent(
                                    text = mediaDetailsUiState.movieDetailsData.name,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(top = 24.dp)
                                            .sharedElement(
                                                rememberSharedContentState(key = "title$groupName$mediaId"),
                                                animatedVisibilityScope = animatedVisibilityScope,
                                                renderInOverlayDuringTransition = false,
                                            ),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                                Spacer(Modifier.height(8.dp))
                                TitleValueComponent(
                                    title = stringResource(R.string.release_date),
                                    value = mediaDetailsUiState.movieDetailsData.releaseDate,
                                )
                                TitleValueComponent(
                                    title = stringResource(R.string.rate),
                                    value = mediaDetailsUiState.movieDetailsData.rate.formattedStringOneDecimal(),
                                )
                                TitleValueComponent(
                                    title = stringResource(R.string.vote_count),
                                    value = mediaDetailsUiState.movieDetailsData.voteCount.toString(),
                                )
                                TitleValueComponent(
                                    title = stringResource(R.string.genres),
                                    value = mediaDetailsUiState.movieDetailsData.genres,
                                )

                                HorizontalDividerComponent(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                )
                                TitleMediumTextComponent(text = stringResource(R.string.overview))
                                Spacer(Modifier.height(8.dp))
                                BodyMediumTextComponent(text = mediaDetailsUiState.movieDetailsData.overview)
                                if (mediaDetailsUiState.mediaCredits.isNotEmpty()) {
                                    LazyRow(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .padding(vertical = 24.dp),
                                    ) {
                                        items(mediaDetailsUiState.mediaCredits) { item ->
                                            CreditListItemComponent(
                                                credit = item,
                                                onItemClicked = {},
                                            )
                                        }
                                    }
                                }
                            }
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

@Composable
private fun TitleValueComponent(
    title: String,
    value: String,
) {
    Spacer(Modifier.height(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        TitleSmallTextComponent(text = title)
        Spacer(modifier = Modifier.width(4.dp))
        BodyMediumTextComponent(text = value)
    }
}
