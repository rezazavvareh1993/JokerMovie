package com.rezazavareh7.movies.ui.navgraph

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.rezazavareh7.movies.ui.images.MediaImagesScreen
import com.rezazavareh7.movies.ui.images.MediaImagesViewModel
import com.rezazavareh7.movies.ui.media.MediaScreen
import com.rezazavareh7.movies.ui.media.MediaViewModel
import com.rezazavareh7.movies.ui.moviedetails.MediaDetailsScreen
import com.rezazavareh7.movies.ui.moviedetails.MediaDetailsViewModel
import com.rezazavareh7.movies.ui.setting.SettingScreen
import com.rezazavareh7.movies.ui.setting.SettingViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.moviesNavGraph(
    sharedTransitionScope: SharedTransitionScope,
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
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this,
                mediaUiEvent = mediaUiEvent,
                mediaUiState = mediaUiState,
                navigateToMediaDetailsScreen = { id, category, groupName ->
                    navController.navigate(
                        MoviesScreens.MediaDetails(
                            id,
                            category,
                            groupName,
                        ).route,
                    )
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

        composable<MoviesScreensGraph.MediaDetails>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 400 },
                    animationSpec =
                        tween(
                            durationMillis = 600,
                        ),
                ) + fadeIn(animationSpec = tween(600))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 400 },
                    animationSpec =
                        tween(
                            durationMillis = 600,
                        ),
                ) + fadeOut(animationSpec = tween(600))
            },
        ) { backStackEntry ->
            val mediaDetailsInfo: MoviesScreensGraph.MediaDetails = backStackEntry.toRoute()
            val viewModel = hiltViewModel<MediaDetailsViewModel>()
            val mediaDetailsUiEvent = viewModel::onEvent
            val mediaDetailsUiState by viewModel.mediaDetailsState.collectAsStateWithLifecycle()
            MediaDetailsScreen(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this,
                mediaId = mediaDetailsInfo.mediaId,
                groupName = mediaDetailsInfo.groupName,
                mediaCategory = MediaCategory.valueOf(mediaDetailsInfo.mediaCategory),
                mediaDetailsUiEvent = mediaDetailsUiEvent,
                mediaDetailsUiState = mediaDetailsUiState,
                onBackClicked = {
                    navController.popBackStack()
                },
                navigateToMediaImages = { mediaId, mediaCategory ->
                    navController.navigate(
                        MoviesScreens.MediaImages(
                            mediaId,
                            mediaCategory = mediaCategory.name,
                        ).route,
                    )
                },
            )
        }

        composable<MoviesScreensGraph.MediaImages> { backStackEntry ->
            val mediaImagesInfo: MoviesScreensGraph.MediaImages = backStackEntry.toRoute()
            val viewModel = hiltViewModel<MediaImagesViewModel>()
            val mediaImagesUiEvent = viewModel::onEvent
            val mediaImagesUiState by viewModel.mediaImagesUiState.collectAsStateWithLifecycle()
            MediaImagesScreen(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this,
                mediaId = mediaImagesInfo.mediaId,
                mediaCategory = MediaCategory.valueOf(mediaImagesInfo.mediaCategory),
                mediaImagesUiEvent = mediaImagesUiEvent,
                mediaImagesUiState = mediaImagesUiState,
                onBackClicked = {
                    navController.popBackStack()
                },
            )
            LaunchedEffect(mediaImagesUiState.shouldDisplayFullScreenPhotos) {
                if (mediaImagesUiState.shouldDisplayFullScreenPhotos && systemBarManager.isLightBar.value) {
                    systemBarManager.setDarkBar()
                } else {
                    systemBarManager.setLightBar()
                }
            }
        }

        composable<MoviesScreensGraph.Favorite> { backStackEntry ->
            val favoriteInfo: MoviesScreensGraph.Favorite = backStackEntry.toRoute()
            val viewModel = hiltViewModel<FavoriteViewModel>()
            val favoriteUiEvent = viewModel::onEvent
            val favoriteUiState by viewModel.favoriteState.collectAsStateWithLifecycle()
            FavoriteScreen(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this,
                category = favoriteInfo.category,
                favoriteUiEvent = favoriteUiEvent,
                favoriteUiState = favoriteUiState,
                navigateToMediaDetailsScreen = { id, category, groupName ->
                    navController.navigate(MoviesScreens.MediaDetails(id, category, groupName).route)
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
