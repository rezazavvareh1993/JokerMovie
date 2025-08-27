package com.rezazavareh7.movies.domain.model

enum class MediaCategory {
    MOVIE,
    SERIES,
    ;

    fun getCategory(type: String): MediaCategory =
        when (type) {
            MOVIE.toString() -> MOVIE
            else -> SERIES
        }
}
