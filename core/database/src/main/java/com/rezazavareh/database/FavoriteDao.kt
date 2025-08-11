package com.rezazavareh.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Upsert

@Dao
@RewriteQueriesToDropUnusedColumns
interface FavoriteDao {
    @Upsert
    suspend fun insertAll(fileEntity: List<FavoriteEntity>)

    @Query("DELETE FROM $FAVORITE_TABLE_NAME")
    suspend fun clearAll()
}
