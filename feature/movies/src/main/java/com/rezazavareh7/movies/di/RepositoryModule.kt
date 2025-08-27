package com.rezazavareh7.movies.di

import com.rezazavareh7.movies.data.repositoryimpl.FavoriteRepositoryImpl
import com.rezazavareh7.movies.data.repositoryimpl.MoviesRepositoryImpl
import com.rezazavareh7.movies.data.repositoryimpl.SeriesRepositoryImpl
import com.rezazavareh7.movies.data.repositoryimpl.SettingRepositoryImpl
import com.rezazavareh7.movies.domain.repository.FavoriteRepository
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import com.rezazavareh7.movies.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Singleton
    @Binds
    abstract fun provideSeriesRepository(seriesRepositoryImpl: SeriesRepositoryImpl): SeriesRepository

    @Singleton
    @Binds
    abstract fun provideSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository

    @Singleton
    @Binds
    abstract fun provideFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}
