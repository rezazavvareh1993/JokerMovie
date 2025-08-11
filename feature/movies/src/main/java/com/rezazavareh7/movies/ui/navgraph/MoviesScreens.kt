package com.rezazavareh7.movies.ui.navgraph

import kotlinx.serialization.Serializable

object MoviesScreensGraph {
    @Serializable
    data object Movies

    @Serializable
    object Favorite

    @Serializable
    data class MovieDetails(
        val movieId: Long,
    )
}

@Serializable
sealed class MoviesScreens<T>(
    val route: T,
) {
    @Serializable
    data object Movies : MoviesScreens<MoviesScreensGraph.Movies>(
        route = MoviesScreensGraph.Movies,
    )

    @Serializable
    data object Favorite : MoviesScreens<MoviesScreensGraph.Favorite>(
        route = MoviesScreensGraph.Favorite,
    )

    @Serializable
    data class MovieDetails(
        val movieId: Long,
    ) : MoviesScreens<MoviesScreensGraph.MovieDetails>(
            route = MoviesScreensGraph.MovieDetails(movieId = movieId),
        )
}
