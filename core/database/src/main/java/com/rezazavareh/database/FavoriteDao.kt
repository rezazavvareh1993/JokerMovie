package com.rezazavareh.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
@RewriteQueriesToDropUnusedColumns
interface FavoriteDao {
    @Upsert
    suspend fun saveFavoriteItem(entity: FavoriteEntity)

    @Query("DELETE FROM $FAVORITE_TABLE_NAME WHERE id = :id")
    suspend fun deleteFavoriteItemById(id: Long)

    @Update
    suspend fun update(entity: FavoriteEntity)

    @Query("DELETE FROM $FAVORITE_TABLE_NAME")
    suspend fun clearAll()

    @Query("SELECT * FROM $FAVORITE_TABLE_NAME")
    fun getFavorites(): Flow<List<FavoriteEntity>>
}
