package com.rezazavareh.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val MOVIE_DATABASE = "movie_database.db"

@Module
@InstallIn(SingletonComponent::class)
object MovieRoomDataBaseModule {
    @Provides
    @Singleton
    fun provideMovieRoomDataBase(
        @ApplicationContext context: Context,
    ): MovieDataBase =
        Room
            .databaseBuilder(
                context.applicationContext,
                MovieDataBase::class.java,
                MOVIE_DATABASE,
            ).build()

    @Provides
    fun provideDao(database: MovieDataBase): MovieDao = database.movieDao
}
