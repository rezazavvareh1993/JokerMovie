package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.MoviesResponse
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import javax.inject.Inject

class MoviesMapper
    @Inject
    constructor() {
        operator fun invoke(response: MoviesResponse): List<MediaData> =
            response.results.map {
                with(it) {
                    MediaData(
                        mediaCategory = MediaCategory.MOVIE,
                        title = title,
                        id = id.toLong(),
                        posterPath = poster_path ?: "",
                        releaseDate = release_date,
                        voteAverage = vote_average.toFloat(),
                        overview = overview,
                        voteCount = vote_count.toLong(),
                        genres = emptyList(),
                    )
                }
            }
    }
