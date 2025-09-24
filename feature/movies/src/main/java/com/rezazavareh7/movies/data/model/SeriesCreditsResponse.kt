package com.rezazavareh7.movies.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesCreditsResponse(
    val cast: List<SeriesCastResponse>,
    val crew: List<SeriesCrewResponse>,
    val id: Int,
)

@JsonClass(generateAdapter = true)
data class SeriesCastResponse(
    val adult: Boolean,
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

@JsonClass(generateAdapter = true)
data class SeriesCrewResponse(
    val adult: Boolean,
    val credit_id: String,
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
