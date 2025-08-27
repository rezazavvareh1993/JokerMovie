package com.rezazavareh7.movies.data.mapper

import com.rezazavareh.database.FavoriteEntity
import com.rezazavareh7.movies.data.model.MoviesResponseResult
import com.rezazavareh7.movies.domain.model.Category
import com.rezazavareh7.movies.domain.model.MovieData

fun MoviesResponseResult.toMovieEntity(): FavoriteEntity =
    FavoriteEntity(
        id = id.toLong(),
        title = this@toMovieEntity.title,
        overview = overview,
        releaseDate = release_date,
        voteAverage = vote_average.toFloat(),
        voteCount = vote_count.toLong(),
        posterPath = poster_path ?: "",
        genres = "",
    )

fun FavoriteEntity.toMovieData(): MovieData =
    MovieData(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        posterPath = posterPath,
        genres = emptyList(),
        category = Category.MOVIE,
    )
