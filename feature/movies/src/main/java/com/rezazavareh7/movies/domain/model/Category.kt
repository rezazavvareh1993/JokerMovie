package com.rezazavareh7.movies.domain.model

enum class Category {
    MOVIE,
    SERIES,
    ;

    fun getCategory(type: String): Category =
        when (type) {
            MOVIE.toString() -> MOVIE
            else -> SERIES
        }
}
