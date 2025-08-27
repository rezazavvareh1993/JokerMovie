package com.rezazavareh.database

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAVORITE_TABLE_NAME = "favorite_table"

@Entity(tableName = FAVORITE_TABLE_NAME)
data class FavoriteEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Long,
    val overview: String,
    val genres: String,
    val category: String = "movie",
)
