package com.rezazavareh7.movies.ui.navgraph

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.rezazavareh7.designsystem.component.navigation.GraphRoutes
import com.rezazavareh7.designsystem.component.navigation.SystemBarVisibilityManager
import com.rezazavareh7.movies.ui.movie.MoviesScreen
import com.rezazavareh7.movies.ui.movie.MoviesViewModel

fun NavGraphBuilder.moviesNavGraph(
    navController: NavHostController,
    systemBarVisibilityManager: SystemBarVisibilityManager,
    isUserLoggedIn: Boolean,
) {
    navigation<GraphRoutes.HomeScreens>(
        startDestination = MoviesScreens.Movies.route,
    ) {
        composable<MoviesScreensGraph.Movies> {
            val viewModel = hiltViewModel<MoviesViewModel>()
            val signUpUiEvent = viewModel::onEvent
            val signUpState by viewModel.moviesState.collectAsStateWithLifecycle()
            MoviesScreen(
                movieUiEvent = signUpUiEvent,
                moviesUiState = signUpState,
                navigateToMovieDetailsScreen = {
                    //                    navController.navigate(MoviesScreens.MovieDetails.route)
                },
            )
            systemBarVisibilityManager.hideBottomBar()
        }
    }
}
