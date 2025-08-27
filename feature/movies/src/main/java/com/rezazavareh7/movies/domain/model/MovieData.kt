package com.rezazavareh7.movies.domain.model

data class MovieData(
    val category: Category,
    val title: String,
    val posterPath: String,
    val id: Long,
    val voteAverage: Float,
    val releaseDate: String,
    val genres: List<String>,
    val overview: String,
    val voteCount: Long,
/*
    val isFavorite: Boolean = false,
*/
)
