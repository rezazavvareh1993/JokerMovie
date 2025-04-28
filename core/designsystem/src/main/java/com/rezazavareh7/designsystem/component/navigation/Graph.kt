package com.rezazavareh7.designsystem.component.navigation

import com.rezazavareh7.designsystem.R
import kotlinx.serialization.Serializable

@Serializable
sealed class GraphRoutes {
    @Serializable
    object HomeScreens

    @Serializable
    object AuthenticationScreens
}

@Serializable
sealed class BottomScreens<T>(val name: Int, val icon: Int, val route: T) {
    @Serializable
    data object Home : BottomScreens<GraphRoutes.HomeScreens>(
        name = R.string.home,
        icon = R.drawable.ic_home,
        route = GraphRoutes.HomeScreens,
    )
}
