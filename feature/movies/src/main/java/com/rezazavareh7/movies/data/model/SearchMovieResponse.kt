package com.rezazavareh7.movies.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchMovieResponse(
    val page: Int,
    val results: List<SearchMovieResult>,
    val total_pages: Int,
    val total_results: Int,
)

@JsonClass(generateAdapter = true)
data class SearchMovieResult(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String? = null,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
)
