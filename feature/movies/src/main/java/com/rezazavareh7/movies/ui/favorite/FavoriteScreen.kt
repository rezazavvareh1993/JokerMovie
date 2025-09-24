package com.rezazavareh7.movies.ui.favorite

import SearchedListItem
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleSmallTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent
import com.rezazavareh7.movies.R
import com.rezazavareh7.ui.components.lottie.LottieAnimationComponent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    category: String,
    favoriteUiEvent: (FavoriteUiEvent) -> Unit,
    favoriteUiState: FavoriteUiState,
    onBackClicked: () -> Unit,
    navigateToMediaDetailsScreen: (Long, String, String) -> Unit,
) {
    LaunchedEffect(category) {
        if (category.isNotEmpty()) {
            favoriteUiEvent(FavoriteUiEvent.GetFavorites)
        }
    }
    val removingIds = remember { mutableStateMapOf<Long, Boolean>() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarComponent(
                hasBackButton = true,
                startContent = {
                    TitleLargeTextComponent(
                        text = stringResource(R.string.favorites),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                onBackClicked = onBackClicked,
            )
        },
    ) { innerPadding ->
        if (favoriteUiState.isLoading) {
            CircularProgressIndicator()
        } else if (favoriteUiState.favorites.isNotEmpty()) {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(favoriteUiState.favorites, key = { it.id }) { item ->
                    val isRemoving = removingIds[item.id] == true
                    SearchedListItem(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        groupName = stringResource(R.string.favorites),
                        favoriteList = favoriteUiState.favorites,
                        item = item,
                        navigateToMediaDetailsScreen = navigateToMediaDetailsScreen,
                        onRemoveFavoriteClicked = { favoriteData ->
                            removingIds[favoriteData.id] = true
                            favoriteUiEvent(FavoriteUiEvent.OnRemoveFavorite(favoriteData))
                            removingIds.remove(favoriteData.id)
                        },
                    )
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimationComponent(
                    modifier =
                        Modifier
                            .wrapContentSize(),
                    lottieResource = com.rezazavareh7.designsystem.R.raw.lottie_no_data,
                )
                TitleSmallTextComponent(
                    text = stringResource(R.string.no_data),
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}
