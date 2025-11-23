package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.mapper.extension.mapGenres
import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaDetailData
import javax.inject.Inject

class MovieDetailsMapper
    @Inject
    constructor() {
        operator fun invoke(response: MovieDetailsResponse): MediaDetailData =
            with(response) {
                MediaDetailData(
                    name = title,
                    id = id.toLong(),
                    backdrop = backdrop_path ?: "",
                    poster = poster_path ?: "",
                    overview = overview,
                    genres = genres.mapGenres(),
                    rate = vote_average.toFloat(),
                    voteCount = vote_count.toLong(),
                    releaseDate = release_date,
                    category = MediaCategory.MOVIE,
                )
            }
    }
