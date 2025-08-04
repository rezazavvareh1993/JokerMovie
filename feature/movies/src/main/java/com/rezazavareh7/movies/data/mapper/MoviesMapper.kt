package com.rezazavareh7.movies.data.mapper

import com.rezazavareh.database.MovieEntity
import com.rezazavareh7.movies.data.model.MoviesResponseResult
import com.rezazavareh7.movies.domain.model.MovieData
import javax.inject.Inject

class MoviesMapper
    @Inject
    constructor() {
        fun MovieEntity.mapToDomain(): MovieData =
            MovieData(
                title = this.title,
                id = this.id.toLong(),
                posterPath = this.posterPath,
                releaseDate = this.releaseDate,
                voteAverage = this.voteAverage,
                overview = this.overview,
                voteCount = this.voteCount,
                genres = emptyList(),
            )

        fun MoviesResponseResult.mapToEntity(): MovieEntity =
            MovieEntity(
                title = title,
                id = id.toLong(),
                posterPath = poster_path ?: "",
                releaseDate = release_date,
                voteAverage = vote_average.toFloat(),
                overview = overview,
                voteCount = vote_count.toLong(),
                genres = "",
            )

        fun MovieData.mapToEntity(): MovieEntity =
            MovieEntity(
                title = title,
                id = id,
                posterPath = posterPath,
                releaseDate = releaseDate,
                voteAverage = voteAverage,
                overview = overview,
                voteCount = voteCount,
                genres = "",
            )
    }
