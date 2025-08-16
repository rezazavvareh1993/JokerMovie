package com.rezazavareh.database

import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    suspend fun delete(entity: FavoriteEntity)

    @Update
    suspend fun update(entity: FavoriteEntity)

    @Query("DELETE FROM $FAVORITE_TABLE_NAME")
    suspend fun clearAll()

    @Query("SELECT * FROM $FAVORITE_TABLE_NAME WHERE :category = category")
    fun getFavorites(category: String): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM $FAVORITE_TABLE_NAME  WHERE :id = id and :category = category")
    fun findItemById(
        category: String,
        id: Long,
    ): FavoriteEntity?
}
