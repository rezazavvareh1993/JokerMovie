package com.rezazavareh7.movies.ui.media.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.rezazavareh7.designsystem.R
import com.rezazavareh7.designsystem.component.progressbar.CircularProgressBarComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.ui.components.lottie.LottieAnimationComponent
import com.rezazavareh7.ui.util.UiText

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaListComponent(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    groupName: String,
    mediaPagingList: LazyPagingItems<MediaData>,
    favoriteIds: List<Long>,
    isInSearchMode: Boolean = false,
    onItemClicked: (MediaData, String) -> Unit,
    mediaUiEvent: (MediaUiEvent) -> Unit,
    onShowError: (UiText?) -> Unit,
) {
    if (mediaPagingList.itemCount > 0) {
        TitleMediumTextComponent(text = groupName)
        LazyRow(
            state = rememberLazyListState(),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(top = 8.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(mediaPagingList.itemCount) { index ->
                val item = mediaPagingList[index]
                item?.let {
                    MediaListItemComponent(
                        groupName = groupName,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        mediaData = item,
                        isLiked = favoriteIds.contains(item.id),
                        onFavoriteClicked = { isLiked, mediaItem ->
                            if (isLiked) {
                                mediaUiEvent(MediaUiEvent.OnLikeMovie(mediaItem))
                            } else {
                                mediaUiEvent(MediaUiEvent.OnDislikeMovie(mediaItem))
                            }
                        },
                        onItemClicked = { mediaData, groupName ->
                            onItemClicked(mediaData, groupName)
                        },
                    )
                }
            }

            item {
                HandlingPagingLoadState(
                    pagingListData = mediaPagingList,
                    onAppendLoading = { CircularProgressBarComponent(modifier = Modifier.fillMaxSize()) },
                    onAppendError = { errorUiText ->
                        onShowError(errorUiText)
                    },
                )
            }
        }
    } else if (isInSearchMode && mediaPagingList.itemCount == 0) {
        LottieAnimationComponent(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(400.dp),
            lottieResource = R.raw.lottie_no_search_data,
        ) { }
    }
}
