package com.rezazavareh.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
@RewriteQueriesToDropUnusedColumns
interface MovieDao {
    @Upsert
    suspend fun upsetAll(fileEntity: List<MovieEntity>): List<Long>

    @Query("SELECT * FROM $MOVIE_TABLE_NAME")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM $MOVIE_TABLE_NAME")
    suspend fun clearAll()

    @Query("SELECT * FROM $MOVIE_TABLE_NAME")
    fun getAllFiles(): Flow<List<MovieEntity>>
}
