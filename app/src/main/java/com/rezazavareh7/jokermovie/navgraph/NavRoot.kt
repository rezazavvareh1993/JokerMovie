package com.rezazavareh7.jokermovie.navgraph

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rezazavareh7.designsystem.component.navigation.GraphRoutes
import com.rezazavareh7.designsystem.component.navigation.SystemBarManager
import com.rezazavareh7.movies.ui.navgraph.moviesNavGraph

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    systemBarManager: SystemBarManager,
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = GraphRoutes.Home,
        ) {
            moviesNavGraph(
                sharedTransitionScope = this@SharedTransitionLayout,
                navController = navController,
                systemBarManager = systemBarManager,
            )
        }
    }
}
