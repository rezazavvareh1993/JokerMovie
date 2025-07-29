package com.rezazavareh.database

import androidx.room.Entity
import androidx.room.PrimaryKey

const val MOVIE_TABLE_NAME = "movie_table"

@Entity(tableName = MOVIE_TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Long,
    val overview: String,
    val genres: String,
)
