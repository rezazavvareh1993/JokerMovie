package com.rezazavareh.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val FAVORITE_DATABASE = "favorite_database.db"

@Module
@InstallIn(SingletonComponent::class)
object FavoriteRoomDataBaseModule {
    @Provides
    @Singleton
    fun provideFavoriteRoomDataBase(
        @ApplicationContext context: Context,
    ): FavoriteDataBase =
        Room
            .databaseBuilder(
                context.applicationContext,
                FavoriteDataBase::class.java,
                FAVORITE_DATABASE,
            ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(database: FavoriteDataBase): FavoriteDao = database.favoriteDao
}
