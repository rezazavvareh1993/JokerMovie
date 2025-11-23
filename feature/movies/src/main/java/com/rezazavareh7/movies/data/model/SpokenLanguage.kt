package com.rezazavareh7.movies.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String,
)
