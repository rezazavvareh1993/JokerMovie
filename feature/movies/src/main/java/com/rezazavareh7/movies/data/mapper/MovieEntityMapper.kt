package com.rezazavareh7.movies.data.mapper

import com.rezazavareh.database.MovieEntity
import com.rezazavareh7.movies.data.model.MoviesResponseResult
import com.rezazavareh7.movies.domain.model.MovieData

fun MoviesResponseResult.toMovieEntity(): MovieEntity =
    MovieEntity(
        id = id.toLong(),
        title = this@toMovieEntity.title,
        overview = overview,
        releaseDate = release_date,
        voteAverage = vote_average.toFloat(),
        voteCount = vote_count.toLong(),
        posterPath = poster_path ?: "",
        genres = "",
    )

fun MovieEntity.toMovieData(): MovieData =
    MovieData(
        id = id.toLong(),
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount.toLong(),
        posterPath = posterPath,
        genres = emptyList(),
    )
