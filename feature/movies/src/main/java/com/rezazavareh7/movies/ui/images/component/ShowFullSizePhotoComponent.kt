package com.rezazavareh7.movies.ui.images.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.rezazavareh7.movies.domain.model.MediaImage
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl
import com.rezazavareh7.ui.components.showToast

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShowFullSizePhotoComponent(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    allPhotos: List<MediaImage>,
    clickedPhotoIndex: Int,
    onBack: (MediaImage) -> Unit,
    shouldShowFullScreen: Boolean = false,
    hasBottomBar: Boolean = false,
    currentItem: (MediaImage, Int) -> Unit,
    topBarContent: @Composable (MediaImage) -> Unit,
) {
    AnimatedVisibility(shouldShowFullScreen) {
        val pagerState =
            rememberPagerState(
                pageCount = { allPhotos.size },
                initialPage = getIndex(clickedPhotoIndex, allPhotos),
            )
        val message by remember { mutableStateOf("") }

        if (message.isNotEmpty()) {
            showToast(LocalContext.current, message)
        }

        var isShowTopBarAndBottomBar by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (isShowTopBarAndBottomBar) {
                    val index = getIndex(pagerState.currentPage, allPhotos)
                    topBarContent(allPhotos[index])
                }
            },
        ) { padding ->
            with(sharedTransitionScope) {
                if (allPhotos.isNotEmpty()) {
                    val index = getIndex(pagerState.currentPage, allPhotos)
                    currentItem(allPhotos[index], index)

                    VerticalSwipeDetectorComponent(
                        onSwipeUp = {},
                        onSwipeDown = {
                            onBack(allPhotos[pagerState.currentPage])
                        },
                    ) {
                        HorizontalPager(
                            modifier =
                                Modifier
                                    .consumeWindowInsets(padding)
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.onSurface),
                            state = pagerState,
                        ) { page ->
                            val image = allPhotos[page]
                            ShowGlideImageByUrl(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .sharedBounds(
                                            exit =
                                                slideOutHorizontally(
                                                    targetOffsetX = { 400 },
                                                    animationSpec =
                                                        tween(
                                                            durationMillis = 600,
                                                            easing = FastOutSlowInEasing,
                                                        ),
                                                ) + fadeOut(animationSpec = tween(600)),
                                            enter =
                                                slideInHorizontally(
                                                    initialOffsetX = { 400 },
                                                    animationSpec =
                                                        tween(
                                                            durationMillis = 600,
                                                            easing = FastOutSlowInEasing,
                                                        ),
                                                ) + fadeIn(animationSpec = tween(600)),
                                            sharedContentState = rememberSharedContentState(key = index),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            renderInOverlayDuringTransition = false,
                                        ),
                                isClickable = true,
                                isZoomable = true,
                                clickOnItem = {
                                    isShowTopBarAndBottomBar = !isShowTopBarAndBottomBar
                                },
                                context = LocalContext.current,
                                imageUrlPath = image.filePath,
                                thumbnailPath = image.filePath,
                                contentScale = ContentScale.Fit,
                            )
                        }
                    }

                    BackHandler { onBack(allPhotos[index]) }
                }
            }
        }
    }
}

private fun getIndex(
    index: Int,
    allPhotos: List<MediaImage>,
): Int =
    if (index in allPhotos.indices) {
        index
    } else {
        allPhotos.lastIndex
    }
