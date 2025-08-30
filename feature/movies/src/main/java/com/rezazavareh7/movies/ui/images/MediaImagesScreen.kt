package com.rezazavareh7.movies.ui.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.rezazavareh7.designsystem.component.progressbar.CircularProgressBarComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.ui.components.showToast
import com.rezazavareh7.ui.glide.ShowGlideImageByUrl
import com.rezazavareh7.ui.util.getScreenDpSize

@Composable
fun MediaImagesScreen(
    mediaImagesUiState: MediaImagesUiState,
    mediaImagesUiEvent: (MediaImagesUiEvent) -> Unit,
    mediaId: Long,
    mediaCategory: MediaCategory,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    if (mediaImagesUiState.errorMessage.isNotEmpty()) {
        showToast(context, mediaImagesUiState.errorMessage)
        mediaImagesUiEvent(MediaImagesUiEvent.OnToastMessageShown)
    }
    LaunchedEffect(mediaId) {
        mediaImagesUiEvent(MediaImagesUiEvent.OnGetMediaImages(mediaId, mediaCategory))
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarComponent(
                hasBackButton = true,
                onBackClicked = onBackClicked,
                backButtonColor = Color.Transparent,
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(padding),
        ) {
            if (mediaImagesUiState.isLoading) {
                CircularProgressBarComponent(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally),
                )
            } else {
                LazyVerticalStaggeredGrid(
                    contentPadding = PaddingValues(1.dp),
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                    columns = StaggeredGridCells.Fixed(2),
                ) {
                    items(mediaImagesUiState.images) { item ->
                        ShowGlideImageByUrl(
                            modifier =
                                Modifier
                                    .width(maxOf(100.dp, getScreenDpSize().width / 2))
                                    .heightIn(
                                        min = getScreenDpSize().width / 4,
                                        max = item.height.dp / 6,
                                    )
                                    .padding(1.dp),
                            context = context,
                            imageUrlPath = item.filePath,
                            thumbnailPath = item.filePath,
                        )
                    }
                }
            }
        }
    }
}
