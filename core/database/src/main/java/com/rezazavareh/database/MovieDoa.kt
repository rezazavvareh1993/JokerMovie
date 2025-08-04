package com.rezazavareh.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Upsert

@Dao
@RewriteQueriesToDropUnusedColumns
interface MovieDao {
    @Upsert
    suspend fun insertAll(fileEntity: List<MovieEntity>)

    @Query("DELETE FROM $MOVIE_TABLE_NAME")
    suspend fun clearAll()

    @Query("SELECT * FROM $MOVIE_TABLE_NAME")
    fun getAllMovies(): PagingSource<Int, MovieEntity>
}
