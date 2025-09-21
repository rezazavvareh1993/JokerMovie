package com.rezazavareh7.movies.ui.media.component

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.common.util.extensions.formattedStringOneDecimal
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.body.BodySmallTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerColorPalette
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaListItemComponent(
    groupName: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    mediaData: MediaData,
    isLiked: Boolean,
    onItemClicked: (Long, String) -> Unit,
    onFavoriteClicked: (Boolean, MediaData) -> Unit,
) {
    with(sharedTransitionScope) {
        Column(
            modifier =
                Modifier
                    .width(175.dp)
                    .fillMaxHeight()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = Shape.highRoundCorner,
                    )
                    .padding(8.dp)
                    .clickable { onItemClicked(mediaData.id, mediaData.mediaCategory.name) },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BodyMediumTextComponent(
                text = mediaData.title,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .wrapContentHeight()
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "title$groupName${mediaData.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            renderInOverlayDuringTransition = false,
                        ),
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            shape = Shape.highRoundCorner,
                        ),
            ) {
                ShowGlideImageByUrl(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .clip(Shape.highRoundCorner)
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "poster$groupName${mediaData.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                renderInOverlayDuringTransition = false,
                                clipInOverlayDuringTransition =
                                    sharedTransitionScope.OverlayClip(
                                        Shape.highRoundCorner,
                                    ),
                            ),
                    imageUrlPath = mediaData.posterPath,
                    contentScale = ContentScale.FillBounds,
                    context = LocalContext.current,
                    placeHolder = LocalJokerIconPalette.current.icMovie,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconComponent(
                        drawableId = LocalJokerIconPalette.current.icStar,
                        tint = LocalJokerColorPalette.current.yellow,
                        iconSize = 16.dp,
                        boxSize = 16.dp,
                    )
                    Spacer(Modifier.width(4.dp))
                    BodySmallTextComponent(
                        modifier = Modifier.padding(top = 4.dp),
                        text = mediaData.voteAverage.formattedStringOneDecimal(),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                IconComponent(
                    drawableId =
                        if (isLiked) {
                            LocalJokerIconPalette.current.icLike
                        } else {
                            LocalJokerIconPalette.current.icDislike
                        },
                    tint = if (isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(8.dp),
                    iconSize = 16.dp,
                    boxSize = 16.dp,
                    isClickable = true,
                    onClick = { onFavoriteClicked(!isLiked, mediaData) },
                )
            }
            Spacer(Modifier.height(4.dp))
            BodySmallTextComponent(
                text = mediaData.releaseDate,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
