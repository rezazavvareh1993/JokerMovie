package com.rezazavareh7.movies.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesResponse(
    val page: Int,
    val results: List<SeriesResponseResult>,
    val total_pages: Int,
    val total_results: Int,
)

@JsonClass(generateAdapter = true)
data class SeriesResponseResult(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>,
    val id: Int,
    val originCountry: List<String> = emptyList(),
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String,
    val popularity: Double,
    val poster_path: String? = null,
    val first_air_date: String? = null,
    val name: String,
    val vote_average: Double,
    val vote_count: Int,
)
