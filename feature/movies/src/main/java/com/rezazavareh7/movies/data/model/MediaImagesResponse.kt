package com.rezazavareh7.movies.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaImagesResponse(
    val backdrops: List<Backdrop>,
    val id: Int,
    val logos: List<Logo>,
    val posters: List<Poster>,
)

@JsonClass(generateAdapter = true)
data class Poster(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String? = null,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int,
)

@JsonClass(generateAdapter = true)
data class Logo(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String? = null,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int,
)

@JsonClass(generateAdapter = true)
data class Backdrop(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String? = null,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int,
)
