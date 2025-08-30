package com.rezazavareh7.movies.ui.media.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rezazavareh7.movies.ui.media.MediaUiEvent
import com.rezazavareh7.movies.ui.media.MediaUiState
import com.rezazavareh7.movies.ui.media.movie.MoviesPage
import com.rezazavareh7.movies.ui.media.series.SeriesPage

@Composable
fun MediaPagerComponent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    uiState: MediaUiState,
    mediaUiEvent: (MediaUiEvent) -> Unit,
    navigateToMediaDetailsScreen: (Long, String) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 0,
        modifier =
            modifier
                .animateContentSize(),
        pageSpacing = 24.dp,
        verticalAlignment = Alignment.Top,
    ) { page ->
        if (page == 0) {
            MoviesPage(
                navigateToMediaDetailsScreen = navigateToMediaDetailsScreen,
                mediaUiEvent = mediaUiEvent,
            )
        } else {
            SeriesPage(
                navigateToMediaDetailsScreen = navigateToMediaDetailsScreen,
                mediaUiEvent = mediaUiEvent,
            )
        }
    }
}
