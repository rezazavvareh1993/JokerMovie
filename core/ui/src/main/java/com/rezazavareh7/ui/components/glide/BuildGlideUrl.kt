package com.rezazavareh7.ui.components.glide

fun buildUrl(
    baseUrl: String,
    moviePosterPath: String,
): String {
    val stringBuilder = StringBuilder(baseUrl)
    stringBuilder.append(moviePosterPath)

    return stringBuilder.toString()
}
