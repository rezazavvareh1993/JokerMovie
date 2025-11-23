package com.rezazavareh7.movies.data.mapper.extension

import com.rezazavareh7.movies.data.model.Genre

fun List<Genre>.mapGenres(): String {
    var genres = ""
    if (this.isNotEmpty()) {
        this.map { it.name }.forEachIndexed { index, item ->
            genres +=
                if (index == this.lastIndex) {
                    " $item"
                } else {
                    " $item,"
                }
        }
    }
    return genres
}
