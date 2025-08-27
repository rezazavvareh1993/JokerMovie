package com.rezazavareh7.jokermovie.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rezazavareh7.designsystem.component.navigation.GraphRoutes
import com.rezazavareh7.designsystem.component.navigation.SystemBarManager
import com.rezazavareh7.movies.ui.navgraph.moviesNavGraph

@Composable
fun RootNavGraph(
    navController: NavHostController,
    systemBarManager: SystemBarManager,
) {
    NavHost(
        navController = navController,
        startDestination = GraphRoutes.Home,
    ) {
        moviesNavGraph(
            navController = navController,
            systemBarManager = systemBarManager,
        )
    }
}
