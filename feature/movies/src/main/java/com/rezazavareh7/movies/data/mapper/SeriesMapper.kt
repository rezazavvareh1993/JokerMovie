package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.SeriesResponse
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import javax.inject.Inject

class SeriesMapper
    @Inject
    constructor() {
        operator fun invoke(response: SeriesResponse): List<MediaData> =
            response.results.map {
                with(it) {
                    MediaData(
                        mediaCategory = MediaCategory.SERIES,
                        title = name,
                        id = id.toLong(),
                        posterPath = poster_path ?: "",
                        releaseDate = first_air_date ?: "",
                        voteAverage = vote_average.toFloat(),
                        overview = overview,
                        voteCount = vote_count.toLong(),
                        genres = emptyList(),
                    )
                }
            }
    }
