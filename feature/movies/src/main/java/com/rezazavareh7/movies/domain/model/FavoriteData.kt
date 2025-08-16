package com.rezazavareh7.movies.domain.model

data class FavoriteData(
    val title: String,
    val posterPath: String,
    val id: Long,
    val voteAverage: Float,
    val releaseDate: String,
    val genres: List<String>,
    val overview: String,
    val voteCount: Long,
)
