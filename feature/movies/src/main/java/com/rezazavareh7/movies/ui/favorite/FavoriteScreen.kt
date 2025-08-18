package com.rezazavareh7.movies.ui.favorite

import FavoriteListItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.component.toolbar.ToolbarComponent

@Composable
fun FavoriteScreen(
    category: String,
    favoriteUiEvent: (FavoriteUiEvent) -> Unit,
    favoriteUiState: FavoriteUiState,
    onBackClicked: () -> Unit,
    navigateToDetails: (Long) -> Unit,
) {
    LaunchedEffect(category) {
        if (category.isNotEmpty()) {
            favoriteUiEvent(FavoriteUiEvent.GetFavorites(category))
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarComponent(
                hasBackButton = true,
                startContent = {
                    TitleMediumTextComponent(text = "مورد علاقه")
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
                items(favoriteUiState.favorites) { item ->
                    FavoriteListItem(
                        item = item,
                        navigateToDetails = navigateToDetails,
                        onRemoveFavoriteClicked = { favoriteData ->
                            favoriteUiEvent(FavoriteUiEvent.OnRemoveFavorite(favoriteData))
                        },
                    )
                }
            }
        }
    }
}
