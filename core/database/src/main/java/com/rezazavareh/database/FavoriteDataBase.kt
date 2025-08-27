package com.rezazavareh.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 4)
abstract class FavoriteDataBase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}
