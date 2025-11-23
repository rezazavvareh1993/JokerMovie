package com.rezazavareh7.movies.ui.moviedetails

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.rezazavareh7.common.util.extensions.formattedStringOneDecimal
import com.rezazavareh7.designsystem.component.divider.HorizontalDividerComponent
import com.rezazavareh7.designsystem.component.icon.CircleIconBoxComponent
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.progressbar.CircularProgressBarComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleSmallTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.designsystem.util.getScreenDpSize
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.ui.media.component.HandlingPagingLoadState
import com.rezazavareh7.movies.ui.media.component.MediaListItemComponent
import com.rezazavareh7.movies.ui.moviedetails.component.CreditListItemComponent
import com.rezazavareh7.movies.ui.moviedetails.component.ImageValueComponent
import com.rezazavareh7.movies.ui.moviedetails.component.TitleValueComponent
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl
import com.rezazavareh7.ui.components.lottie.MediaLoadingAnimation
import com.rezazavareh7.ui.components.showToast

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaDetailsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    groupName: String,
    mediaData: MediaData,
    mediaDetailsUiEvent: (MediaDetailsUiEvent) -> Unit,
    mediaDetailsUiState: MediaDetailsUiState,
    mediaSimilarList: LazyPagingItems<MediaData>,
    onBackClicked: () -> Unit,
    navigateToMediaImages: (Long, MediaCategory) -> Unit,
    navigateToMediaDetails: (MediaData, String) -> Unit,
) {
    val context = LocalContext.current
    if (mediaDetailsUiState.errorMessage != null) {
        showToast(context, mediaDetailsUiState.errorMessage.asString())
        mediaDetailsUiEvent(MediaDetailsUiEvent.OnToastMessageShown)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        if (mediaDetailsUiState.isLoading) {
            MediaLoadingAnimation()
        } else {
            with(sharedTransitionScope) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                ) {
                    mediaDetailsUiState.movieDetailsData?.let { data ->
                        with(data) {
                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(getScreenDpSize().height * 0.25f)
                                        .clickable {
                                            navigateToMediaImages(
                                                mediaData.id,
                                                mediaData.mediaCategory,
                                            )
                                        }.clip(Shape.highRoundCornerBottom),
                            ) {
                                ShowGlideImageByUrl(
                                    modifier = Modifier.matchParentSize(),
                                    imageUrlPath = backdrop,
                                    context = LocalContext.current,
                                )
                                IconComponent(
                                    drawableId = LocalJokerIconPalette.current.icBack,
                                    tint = MaterialTheme.colorScheme.primary,
                                    isClickable = true,
                                    onClick = onBackClicked,
                                    modifier =
                                        Modifier
                                            .align(Alignment.TopStart)
                                            .padding(16.dp),
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
                                    onClick = {
                                        navigateToMediaImages(
                                            mediaData.id,
                                            mediaData.mediaCategory,
                                        )
                                    },
                                )

                                CircleIconBoxComponent(
                                    modifier =
                                        Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(16.dp),
                                    icon = if (mediaDetailsUiState.isFavorite) LocalJokerIconPalette.current.icLike else LocalJokerIconPalette.current.icDislike,
                                    borderColor = Color.Unspecified,
                                    backgroundColor = Color.Black.copy(alpha = 0.8f),
                                    iconTint = if (mediaDetailsUiState.isFavorite) MaterialTheme.colorScheme.error else Color.White,
                                    iconSize = 20.dp,
                                    boxSize = 42.dp,
                                    isClickable = true,
                                    onClick = {
                                        if (mediaDetailsUiState.isFavorite) {
                                            mediaDetailsUiEvent(
                                                MediaDetailsUiEvent.OnDislikeMedia(mediaData),
                                            )
                                        } else {
                                            mediaDetailsUiEvent(
                                                MediaDetailsUiEvent.OnLikeMedia(mediaData),
                                            )
                                        }
                                    },
                                )
                            }
                            Box(modifier = Modifier.fillMaxSize()) {
                                Box(modifier = Modifier.matchParentSize()) {
                                    ShowGlideImageByUrl(
                                        modifier =
                                            Modifier
                                                .matchParentSize()
                                                .sharedElement(
                                                    sharedContentState =
                                                        rememberSharedContentState(
                                                            key = "poster$groupName$id",
                                                        ),
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
                                }

                                Column(
                                    modifier =
                                        Modifier
                                            .matchParentSize()
                                            .padding(horizontal = 8.dp),
                                ) {
                                    TitleLargeTextComponent(
                                        text = mediaDetailsUiState.movieDetailsData.name,
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .padding(top = 8.dp)
                                                .sharedElement(
                                                    rememberSharedContentState(key = "title$groupName$mediaData"),
                                                    animatedVisibilityScope = animatedVisibilityScope,
                                                    renderInOverlayDuringTransition = false,
                                                ),
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                    Column(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                                .verticalScroll(rememberScrollState()),
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            TitleValueComponent(
                                                title = stringResource(R.string.release_date),
                                                modifier = Modifier.weight(1f),
                                                value = mediaDetailsUiState.movieDetailsData.releaseDate,
                                            )

                                            ImageValueComponent(
                                                image = LocalJokerIconPalette.current.icIMDB,
                                                value = mediaDetailsUiState.movieDetailsData.rate.formattedStringOneDecimal(),
                                            )
                                        }

                                        TitleValueComponent(
                                            title = stringResource(R.string.vote_count),
                                            value = mediaDetailsUiState.movieDetailsData.voteCount.toString(),
                                        )
                                        TitleValueComponent(
                                            title = stringResource(R.string.genres),
                                            value = mediaDetailsUiState.movieDetailsData.genres,
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        TitleSmallTextComponent(text = stringResource(R.string.overview))
                                        Spacer(Modifier.height(4.dp))
                                        BodyMediumTextComponent(
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth(),
                                            text = mediaDetailsUiState.movieDetailsData.overview,
                                            maxLines =
                                                if (mediaDetailsUiState.showOverview) {
                                                    Int.MAX_VALUE
                                                } else {
                                                    1
                                                },
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            HorizontalDividerComponent(modifier = Modifier.weight(1f))
                                            TitleSmallTextComponent(
                                                color = MaterialTheme.colorScheme.primary,
                                                text =
                                                    if (mediaDetailsUiState.showOverview) {
                                                        stringResource(R.string.show_less)
                                                    } else {
                                                        stringResource(R.string.show_more)
                                                    },
                                                isClickable = true,
                                                onClickable = {
                                                    if (mediaDetailsUiState.showOverview) {
                                                        mediaDetailsUiEvent(MediaDetailsUiEvent.OnShowLess)
                                                    } else {
                                                        mediaDetailsUiEvent(MediaDetailsUiEvent.OnShowMore)
                                                    }
                                                },
                                            )
                                            HorizontalDividerComponent(modifier = Modifier.weight(1f))
                                        }
                                        if (mediaDetailsUiState.mediaCredits.isNotEmpty()) {
                                            Spacer(Modifier.height(8.dp))
                                            TitleMediumTextComponent(text = stringResource(R.string.credits))
                                            LazyRow(
                                                modifier =
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .wrapContentHeight()
                                                        .padding(bottom = 16.dp, top = 8.dp),
                                            ) {
                                                items(mediaDetailsUiState.mediaCredits) { item ->
                                                    CreditListItemComponent(
                                                        credit = item,
                                                        onItemClicked = {},
                                                    )
                                                }
                                            }
                                        }

                                        if (mediaSimilarList.itemCount > 0) {
                                            TitleMediumTextComponent(text = stringResource(R.string.similar))
                                            LazyRow(
                                                state = rememberLazyListState(),
                                                modifier =
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .height(300.dp)
                                                        .padding(top = 8.dp, bottom = 20.dp),
                                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                            ) {
                                                items(mediaSimilarList.itemCount) { index ->
                                                    val item = mediaSimilarList[index]
                                                    item?.let {
                                                        MediaListItemComponent(
                                                            groupName = groupName,
                                                            sharedTransitionScope = sharedTransitionScope,
                                                            animatedVisibilityScope = animatedVisibilityScope,
                                                            mediaData = item,
                                                            isLiked =
                                                                mediaDetailsUiState.favoriteIds.contains(
                                                                    it.id,
                                                                ),
                                                            onFavoriteClicked = { isLiked, mediaItem ->
                                                                if (isLiked) {
                                                                    mediaDetailsUiEvent(
                                                                        MediaDetailsUiEvent.OnLikeMedia(
                                                                            mediaItem,
                                                                        ),
                                                                    )
                                                                } else {
                                                                    mediaDetailsUiEvent(
                                                                        MediaDetailsUiEvent.OnDislikeMedia(
                                                                            mediaItem,
                                                                        ),
                                                                    )
                                                                }
                                                            },
                                                            onItemClicked = { mediaData, groupName ->
                                                                navigateToMediaDetails(
                                                                    mediaData,
                                                                    groupName,
                                                                )
                                                            },
                                                        )
                                                    }
                                                }

                                                item {
                                                    HandlingPagingLoadState(
                                                        mediaSimilarList,
                                                        onAppendLoading = {
                                                            CircularProgressBarComponent()
                                                        },
                                                        onAppendError = { errorUiText ->
                                                            mediaDetailsUiEvent(MediaDetailsUiEvent.OnShowToast(errorUiText))
                                                        },
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
        }
    }

    BackHandler {
        onBackClicked()
    }
}
