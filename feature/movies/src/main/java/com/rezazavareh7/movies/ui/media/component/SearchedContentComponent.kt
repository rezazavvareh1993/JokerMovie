package com.rezazavareh7.movies.ui.media.component

import SearchedListItemComponent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rezazavareh7.designsystem.R
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.ui.components.lottie.LottieAnimationComponent
import com.rezazavareh7.ui.components.showToast

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchedContentComponent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    hasSearchResult: Boolean,
    historyQueryList: List<String>,
    shouldShowHistoryQueries: Boolean,
    groupName: String,
    mediaList: LazyPagingItems<MediaData>,
    favoriteIds: List<Long>,
    onItemClicked: (Long, String, String) -> Unit,
    mediaUiEvent: (MediaUiEvent) -> Unit,
    clickOnQueryItem: (String) -> Unit,
) {
    AnimatedVisibility(shouldShowHistoryQueries) {
        SearchHistoryListComponent(
            modifier =
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            historyList = historyQueryList,
            clickOnItem = clickOnQueryItem,
        )
    }
    if (hasSearchResult) {
        if (mediaList.itemCount > 0) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(mediaList.itemCount) { index ->
                    val item = mediaList[index]
                    item?.let {
                        SearchedListItemComponent(
                            groupName = groupName,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            item = item,
                            isLiked = favoriteIds.contains(item.id),
                            onFavoriteClicked = { isLiked, mediaItem ->
                                if (isLiked) {
                                    mediaUiEvent(MediaUiEvent.OnLikeMovie(mediaItem))
                                } else {
                                    mediaUiEvent(MediaUiEvent.OnDislikeMovie(mediaItem))
                                }
                            },
                            onItemClicked = onItemClicked,
                        )
                    }
                }

                mediaList.apply {
                    when (loadState.append) {
                        is LoadState.Loading ->
                            item {
                                CircularProgressIndicator()
                            }

                        is LoadState.Error ->
                            item {
                                showToast(
                                    LocalContext.current,
                                    (mediaList.loadState.append as LoadState.Error).error.message.toString(),
                                )
                            }

                        else -> {}
                    }
                }
            }
        } else {
            LottieAnimationComponent(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                lottieResource = R.raw.lottie_no_data,
            )
        }
    }
}
