package com.rezazavareh7.movies.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesDetailResponse(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val created_by: List<CreatedBy> = emptyList(),
    val episode_run_time: List<Int> = emptyList(),
    val first_air_date: String? = null,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean = false,
    val languages: List<String> = emptyList(),
    val last_air_date: String? = null,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int,
)

@JsonClass(generateAdapter = true)
data class Season(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String? = null,
    val season_number: Int,
    val vote_average: Double,
)

@JsonClass(generateAdapter = true)
data class Network(
    val id: Int,
    val logo_path: String? = null,
    val name: String,
    val origin_country: String,
)

@JsonClass(generateAdapter = true)
data class LastEpisodeToAir(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val runtime: Int,
    val season_number: Int,
    val show_id: Int,
    val still_path: String,
    val vote_average: Double,
    val vote_count: Int,
)

@JsonClass(generateAdapter = true)
data class CreatedBy(
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val profile_path: String? = null,
)
