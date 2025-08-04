package com.rezazavareh.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class, RemoteKeyEntity::class], version = 2)
abstract class MovieDataBase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val remoteKeyDao: RemoteKeyDao
}
