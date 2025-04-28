package com.rezazavareh7.jokermovie.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rezazavareh7.designsystem.component.navigation.GraphRoutes
import com.rezazavareh7.designsystem.component.navigation.SystemBarVisibilityManager
import com.rezazavareh7.movies.ui.navgraph.moviesNavGraph

@Composable
fun RootNavGraph(
    navController: NavHostController,
    systemBarVisibilityManager: SystemBarVisibilityManager,
    isUserLoggedIn: Boolean,
    isNavigateToSpecialRouteOfBottomNavigation: (Int) -> Unit,
    navigateToPasswordVerification: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = GraphRoutes.HomeScreens,
    ) {
        moviesNavGraph(
            navController = navController,
            systemBarVisibilityManager = systemBarVisibilityManager,
            isUserLoggedIn = isUserLoggedIn,
        )
    }
}
