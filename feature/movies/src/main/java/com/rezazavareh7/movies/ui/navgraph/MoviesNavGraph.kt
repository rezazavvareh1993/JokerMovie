package com.rezazavareh7.movies.ui.navgraph

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.rezazavareh7.designsystem.component.navigation.GraphRoutes
import com.rezazavareh7.designsystem.component.navigation.SystemBarManager
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.ui.favorite.FavoriteScreen
import com.rezazavareh7.movies.ui.favorite.FavoriteViewModel
import com.rezazavareh7.movies.ui.media.MediaScreen
import com.rezazavareh7.movies.ui.media.MediaViewModel
import com.rezazavareh7.movies.ui.moviedetails.MediaDetailsScreen
import com.rezazavareh7.movies.ui.moviedetails.MediaDetailsViewModel
import com.rezazavareh7.movies.ui.setting.SettingScreen
import com.rezazavareh7.movies.ui.setting.SettingViewModel

fun NavGraphBuilder.moviesNavGraph(
    navController: NavHostController,
    systemBarManager: SystemBarManager,
) {
    navigation<GraphRoutes.Home>(
        startDestination = MoviesScreens.Movies.route,
    ) {
        composable<MoviesScreensGraph.Movies> {
            val viewModel = hiltViewModel<MediaViewModel>()
            val mediaUiEvent = viewModel::onEvent
            val mediaUiState by viewModel.mediaState.collectAsStateWithLifecycle()
            MediaScreen(
                mediaUiEvent = mediaUiEvent,
                mediaUiState = mediaUiState,
                navigateToMediaDetailsScreen = { id, category ->
                    navController.navigate(MoviesScreens.MediaDetails(id, category).route)
                },
                navigateToFavoriteScreen = { category ->
                    navController.navigate(MoviesScreens.Favorite(category = category).route)
                },
                navigateToSetting = {
                    navController.navigate(MoviesScreens.Setting.route)
                },
            )
            if (systemBarManager.isBottomBarVisible.value) {
                systemBarManager.hideBottomBar()
            }
        }

        composable<MoviesScreensGraph.MediaDetails> { backStackEntry ->
            val mediaDetailsInfo: MoviesScreensGraph.MediaDetails = backStackEntry.toRoute()
            val viewModel = hiltViewModel<MediaDetailsViewModel>()
            val mediaDetailsUiEvent = viewModel::onEvent
            val mediaDetailsUiState by viewModel.mediaDetailsState.collectAsStateWithLifecycle()
            MediaDetailsScreen(
                mediaId = mediaDetailsInfo.mediaId,
                mediaCategory = MediaCategory.valueOf(mediaDetailsInfo.mediaCategory),
                mediaDetailsUiEvent = mediaDetailsUiEvent,
                mediaDetailsUiState = mediaDetailsUiState,
                onBackClicked = {
                    navController.popBackStack()
                },
            )
        }

        composable<MoviesScreensGraph.Favorite> { backStackEntry ->
            val favoriteInfo: MoviesScreensGraph.Favorite = backStackEntry.toRoute()
            val viewModel = hiltViewModel<FavoriteViewModel>()
            val favoriteUiEvent = viewModel::onEvent
            val favoriteUiState by viewModel.favoriteState.collectAsStateWithLifecycle()
            FavoriteScreen(
                category = favoriteInfo.category,
                favoriteUiEvent = favoriteUiEvent,
                favoriteUiState = favoriteUiState,
                navigateToMediaDetailsScreen = { id, category ->
                    navController.navigate(MoviesScreens.MediaDetails(id, category).route)
                },
                onBackClicked = {
                    navController.popBackStack()
                },
            )
        }

        composable<MoviesScreensGraph.Setting> {
            val viewModel = hiltViewModel<SettingViewModel>()
            val settingUiEvent = viewModel::onEvent
            val settingUiState by viewModel.settingUiState.collectAsStateWithLifecycle()
            SettingScreen(
                settingUiEvent = settingUiEvent,
                settingUiState = settingUiState,
                onBackClicked = {
                    navController.popBackStack()
                },
            )
        }
    }
}
