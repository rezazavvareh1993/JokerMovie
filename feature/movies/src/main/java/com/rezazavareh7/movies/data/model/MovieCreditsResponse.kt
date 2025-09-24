package com.rezazavareh7.movies.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCreditsResponse(
    val cast: List<MovieCastResponse>,
    val crew: List<MovieCrewResponse>,
    val id: Int,
)

@JsonClass(generateAdapter = true)
data class MovieCrewResponse(
    val adult: Boolean,
    val credit_id: String? = null,
    val department: String,
    val gender: Int,
    val id: Long,
    val job: String,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String? = null,
)

@JsonClass(generateAdapter = true)
data class MovieCastResponse(
    val adult: Boolean,
    val cast_id: Long,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Long,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String? = null,
)
