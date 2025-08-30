package com.rezazavareh7.movies.domain.model

data class MediaDetailData(
    val name: String,
    val id: Long,
    val backdrop: String,
    val poster: String,
    val releaseDate: String,
    val rate: Float,
    val voteCount: Long,
    val overview: String,
    val genres: List<String>,
    val category: MediaCategory,
)
