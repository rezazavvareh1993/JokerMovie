package com.rezazavareh7.movies.ui.media.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.ui.components.showToast

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaListComponent(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    groupName: String,
    mediaList: LazyPagingItems<MediaData>,
    favoriteIds: List<Long>,
    onItemClicked: (Long, String, String) -> Unit,
    mediaUiEvent: (MediaUiEvent) -> Unit,
) {
    if (mediaList.itemCount > 0) {
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
            items(mediaList.itemCount) { index ->
                val item = mediaList[index]
                item?.let {
                    MovieListItemComponent(
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
                        onItemClicked = { id, category ->
                            onItemClicked(id, category, groupName)
                        },
                    )
                }
            }

            mediaList.apply {
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
                                (mediaList.loadState.refresh as LoadState.Error).error.message.toString(),
                            )
                        }
                }
            }
        }
    }
}
