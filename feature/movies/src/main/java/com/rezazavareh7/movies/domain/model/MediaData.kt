package com.rezazavareh7.movies.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MediaData(
    val mediaCategory: MediaCategory,
    val title: String,
    val posterPath: String,
    val id: Long,
    val voteAverage: Float,
    val releaseDate: String,
    val genres: List<String>,
    val overview: String,
    val voteCount: Long,
)
