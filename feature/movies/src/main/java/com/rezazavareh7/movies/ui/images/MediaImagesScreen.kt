package com.rezazavareh7.movies.ui.images

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.progressbar.CircularProgressBarComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.util.getScreenDpSize
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.ui.images.component.ConfirmationBottomSheetComponent
import com.rezazavareh7.movies.ui.images.component.ShowFullSizePhotoComponent
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl
import com.rezazavareh7.ui.components.showToast
import com.rezazavareh7.ui.util.shareProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaImagesScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    mediaImagesUiState: MediaImagesUiState,
    mediaImagesUiEvent: (MediaImagesUiEvent) -> Unit,
    mediaId: Long,
    mediaCategory: MediaCategory,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val staggeredGridScope = rememberLazyStaggeredGridState()

    if (mediaImagesUiState.errorMessage.isNotEmpty()) {
        showToast(context, mediaImagesUiState.errorMessage)
        mediaImagesUiEvent(MediaImagesUiEvent.OnToastMessageShown)
    }

    val shareLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { mediaImagesUiEvent(MediaImagesUiEvent.OnShareComplete) }

    LaunchedEffect(mediaId) {
        mediaImagesUiEvent(MediaImagesUiEvent.OnGetMediaImages(mediaId, mediaCategory))
    }

    LaunchedEffect(mediaImagesUiState.sharePhotoAbsolutePath) {
        if (mediaImagesUiState.sharePhotoAbsolutePath.isNotEmpty()) {
            shareProvider(context, mediaImagesUiState.sharePhotoAbsolutePath) { chooserIntent ->
                shareLauncher.launch(chooserIntent)
            }
        }
    }

    LaunchedEffect(mediaImagesUiState.savePhotoState) {
        if (mediaImagesUiState.savePhotoState is SavePhotoState.Saved) {
            mediaImagesUiEvent(MediaImagesUiEvent.OnSavePhotoSuccessful)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(visible = !mediaImagesUiState.shouldDisplayFullScreenPhotos) {
                ToolbarComponent(
                    hasBackButton = true,
                    onBackClicked = onBackClicked,
                    startContent = {
                        TitleLargeTextComponent(
                            text = stringResource(R.string.photos),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                )
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            if (mediaImagesUiState.isLoading) {
                CircularProgressBarComponent(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally),
                )
            } else {
                with(sharedTransitionScope) {
                    LazyVerticalStaggeredGrid(
                        contentPadding = PaddingValues(1.dp),
                        state = staggeredGridScope,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(vertical = 16.dp),
                        columns = StaggeredGridCells.Fixed(2),
                    ) {
                        if (mediaImagesUiState.lastDisplayedImageIndex != -1) {
                            scope.launch {
                                staggeredGridScope.scrollToItem(
                                    index = mediaImagesUiState.lastDisplayedImageIndex,
                                    scrollOffset = -500,
                                )
                                mediaImagesUiEvent(MediaImagesUiEvent.OnResetLastDisplayedImage)
                            }
                        }
                        itemsIndexed(mediaImagesUiState.images) { index, item ->
                            ShowGlideImageByUrl(
                                modifier =
                                    Modifier
                                        .width(maxOf(100.dp, getScreenDpSize().width / 2))
                                        .heightIn(
                                            min = getScreenDpSize().width / 4,
                                            max = item.height.dp / 6,
                                        )
                                        .padding(2.dp)
                                        .sharedBounds(
                                            sharedContentState = rememberSharedContentState(key = index),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            renderInOverlayDuringTransition = false,
                                        ),
                                clickOnItem = {
                                    mediaImagesUiEvent(
                                        MediaImagesUiEvent.OnItemClicked(
                                            item,
                                            index,
                                        ),
                                    )
                                },
                                context = context,
                                isClickable = true,
                                imageUrlPath = item.filePath,
                                thumbnailPath = item.filePath,
                            )
                        }
                    }
                }
            }
        }

        if (mediaImagesUiState.images.isNotEmpty()) {
            ShowFullSizePhotoComponent(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                shouldShowFullScreen = mediaImagesUiState.shouldDisplayFullScreenPhotos,
                allPhotos = mediaImagesUiState.images,
                clickedPhotoIndex = mediaImagesUiState.indexOfFirstFullScreenPhoto,
                currentItem = { mediaImage, indexOfItem ->
                    mediaImagesUiEvent(
                        MediaImagesUiEvent.LastPhotoDisplayed(
                            DisplayPhotoItemInfo(mediaImage, indexOfItem),
                        ),
                    )
                },
                onBack = { lastDisplayedPhoto ->
                    mediaImagesUiEvent(MediaImagesUiEvent.OnFullScreenExitClicked)
                },
                topBarContent = { lastDisplayedPhoto ->
                    ShowImageDisplayTopBar(
                        onBackClicked = {
                            mediaImagesUiEvent(MediaImagesUiEvent.OnFullScreenExitClicked)
                        },
                        onDownloadClicked = {
                            mediaImagesUiEvent(
                                MediaImagesUiEvent.OnSaveImageClicked(lastDisplayedPhoto.filePath),
                            )
                        },
                        onShareClicked = {
                            mediaImagesUiEvent(
                                MediaImagesUiEvent.OnSharePhotoClicked(lastDisplayedPhoto.filePath),
                            )
                        },
                    )
                },
            )

            if (mediaImagesUiState.shouldShowSavePhotoBottomSheet) {
                ConfirmationBottomSheetComponent(
                    title = stringResource(id = R.string.save_photo),
                    subTitle = stringResource(id = R.string.are_you_sure_to_save_photo),
                    confirmationButtonTitle = stringResource(id = R.string.yes_save_it),
                    onConfirm = {
                        mediaImagesUiEvent(MediaImagesUiEvent.OnConfirmSavePhoto)
                    },
                    onDismiss = {
                        mediaImagesUiEvent(MediaImagesUiEvent.OnHideSavePhotoBottomSheet)
                    },
                )
            }
        }
        BackHandler { onBackClicked() }
    }
}

@Composable
private fun ShowImageDisplayTopBar(
    onBackClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
    onShareClicked: () -> Unit,
) {
    ToolbarComponent(
        height = 52.dp,
        backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        hasBackButton = true,
        backButtonColor = MaterialTheme.colorScheme.surface,
        onBackClicked = onBackClicked,
        endContent = {
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                IconComponent(
                    boxSize = 32.dp,
                    drawableId = LocalJokerIconPalette.current.icDownload,
                    tint = MaterialTheme.colorScheme.surface,
                    isClickable = true,
                    onClick = onDownloadClicked,
                )

                IconComponent(
                    boxSize = 32.dp,
                    drawableId = LocalJokerIconPalette.current.icShare,
                    tint = MaterialTheme.colorScheme.surface,
                    isClickable = true,
                    onClick = onShareClicked,
                )
            }
        },
    )
}
