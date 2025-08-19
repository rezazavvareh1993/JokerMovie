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
import com.rezazavareh7.movies.ui.favorite.FavoriteScreen
import com.rezazavareh7.movies.ui.favorite.FavoriteViewModel
import com.rezazavareh7.movies.ui.movie.MoviesScreen
import com.rezazavareh7.movies.ui.movie.MoviesViewModel
import com.rezazavareh7.movies.ui.moviedetails.MovieDetailsScreen
import com.rezazavareh7.movies.ui.moviedetails.MovieDetailsViewModel
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
            val viewModel = hiltViewModel<MoviesViewModel>()
            val moviesUiEvent = viewModel::onEvent
            val moviesState by viewModel.moviesState.collectAsStateWithLifecycle()
            MoviesScreen(
                movieUiEvent = moviesUiEvent,
                moviesUiState = moviesState,
                navigateToMovieDetailsScreen = { movieId ->
                    navController.navigate(MoviesScreens.MovieDetails(movieId).route)
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

        composable<MoviesScreensGraph.MovieDetails> { backStackEntry ->
            val movieDetailsInfo: MoviesScreensGraph.MovieDetails = backStackEntry.toRoute()
            val viewModel = hiltViewModel<MovieDetailsViewModel>()
            val movieDetailsUiEvent = viewModel::onEvent
            val movieDetailsUiState by viewModel.movieDetailsState.collectAsStateWithLifecycle()
            MovieDetailsScreen(
                movieId = movieDetailsInfo.movieId,
                movieDetailsUiEvent = movieDetailsUiEvent,
                movieDetailsUiState = movieDetailsUiState,
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
                navigateToDetails = { id ->
                    navController.navigate(MoviesScreens.MovieDetails(id).route)
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
