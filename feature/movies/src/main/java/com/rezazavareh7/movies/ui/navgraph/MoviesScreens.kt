package com.rezazavareh7.movies.ui.navgraph

import kotlinx.serialization.Serializable

object MoviesScreensGraph {
    @Serializable
    data object Movies

    @Serializable
    data object MovieDetails
}

@Serializable
sealed class MoviesScreens<T>(val route: T) {
    @Serializable
    data object Movies : MoviesScreens<MoviesScreensGraph.Movies>(
        route = MoviesScreensGraph.Movies,
    )

    @Serializable
    data object MovieDetails : MoviesScreens<MoviesScreensGraph.MovieDetails>(
        route = MoviesScreensGraph.MovieDetails,
    )
}
