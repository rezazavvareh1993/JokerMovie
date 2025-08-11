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
import com.rezazavareh7.movies.ui.movie.MoviesScreen
import com.rezazavareh7.movies.ui.movie.MoviesViewModel
import com.rezazavareh7.movies.ui.moviedetails.MovieDetailsScreen
import com.rezazavareh7.movies.ui.moviedetails.MovieDetailsViewModel

fun NavGraphBuilder.moviesNavGraph(
    navController: NavHostController,
    systemBarManager: SystemBarManager,
    isUserLoggedIn: Boolean,
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
                navigateToFavoriteScreen = {
                    navController.navigate(MoviesScreens.Favorite.route)
                },
            )
            if (!systemBarManager.isBottomBarVisible.value) {
                systemBarManager.showBottomBar()
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
    }
}
