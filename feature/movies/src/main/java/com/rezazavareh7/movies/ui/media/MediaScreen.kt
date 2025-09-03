package com.rezazavareh7.movies.ui.media

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.ui.media.component.MediaPagerComponent
import com.rezazavareh7.movies.ui.media.component.MediaTabRowComponent
import kotlinx.coroutines.launch

@Composable
fun MediaScreen(
    mediaUiEvent: (MediaUiEvent) -> Unit,
    mediaUiState: MediaUiState,
    navigateToFavoriteScreen: (String) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMediaDetailsScreen: (Long, String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { MediaCategory.entries.size })

    LaunchedEffect(pagerState.isScrollInProgress) {
        if (pagerState.settledPage != mediaUiState.currentTabRowIndex && !pagerState.isScrollInProgress) {
            mediaUiEvent(
                MediaUiEvent.OnCurrentTabRowChanged(
                    pagerState.settledPage,
                    mediaUiState.currentTabRowIndex,
                ),
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarComponent(startContent = {
                IconComponent(drawableId = LocalJokerIconPalette.current.icMainLogo)
                TitleLargeTextComponent(text = stringResource(R.string.toolbar_title))
            }, endContent = {
                IconComponent(
                    drawableId = LocalJokerIconPalette.current.icFavorite,
                    isClickable = true,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        navigateToFavoriteScreen(MediaCategory.MOVIE.toString())
                    },
                )
                Spacer(modifier = Modifier.width(12.dp))
                IconComponent(
                    drawableId = LocalJokerIconPalette.current.icSetting,
                    isClickable = true,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = navigateToSetting,
                )
            })
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(24.dp))
            MediaTabRowComponent(
                modifier = Modifier.padding(horizontal = 16.dp),
                currentTabIndex = mediaUiState.currentTabRowIndex,
                onTabClick = { tab, index ->
                    if (mediaUiState.currentTabRowIndex != index) {
                        mediaUiEvent(
                            MediaUiEvent.OnCurrentTabRowChanged(
                                index,
                                mediaUiState.currentTabRowIndex,
                            ),
                        )
                        coroutineScope.launch { pagerState.scrollToPage(index) }
                    }
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
            MediaPagerComponent(
                modifier =
                    Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1f),
                favoriteIds = mediaUiState.favoriteIds,
                pagerState = pagerState,
                mediaUiEvent = mediaUiEvent,
                navigateToMediaDetailsScreen = navigateToMediaDetailsScreen,
            )
        }
    }
}
