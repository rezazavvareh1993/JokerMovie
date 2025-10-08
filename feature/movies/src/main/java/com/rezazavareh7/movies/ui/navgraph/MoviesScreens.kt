package com.rezazavareh7.movies.ui.navgraph

import com.rezazavareh7.movies.domain.model.MediaData
import kotlinx.serialization.Serializable

object MoviesScreensGraph {
    @Serializable
    data object Movies

    @Serializable
    data class Favorite(val category: String)

    @Serializable
    object Setting

    @Serializable
    data class MediaDetails(val mediaData: MediaData, val groupName: String)

    @Serializable
    data class MediaImages(val mediaId: Long, val mediaCategory: String)
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
    object Setting : MoviesScreens<MoviesScreensGraph.Setting>(
        route = MoviesScreensGraph.Setting,
    )

    @Serializable
    data class Favorite(val category: String) : MoviesScreens<MoviesScreensGraph.Favorite>(
        route = MoviesScreensGraph.Favorite(category = category),
    )

    @Serializable
    data class MediaDetails(
        val mediaData: MediaData,
        val groupName: String,
    ) : MoviesScreens<MoviesScreensGraph.MediaDetails>(
            route =
                MoviesScreensGraph.MediaDetails(mediaData = mediaData, groupName = groupName),
        )

    @Serializable
    data class MediaImages(
        val mediaId: Long,
        val mediaCategory: String,
    ) : MoviesScreens<MoviesScreensGraph.MediaImages>(
            route =
                MoviesScreensGraph.MediaImages(mediaId = mediaId, mediaCategory = mediaCategory),
        )
}
