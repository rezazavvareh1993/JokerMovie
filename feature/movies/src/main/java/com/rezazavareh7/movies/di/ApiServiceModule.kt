package com.rezazavareh7.movies.di

import com.rezazavareh7.movies.data.apiservice.MovieApiService
import com.rezazavareh7.movies.data.apiservice.SeriesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Singleton
    @Provides
    fun provideMoviesApiService(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)

    @Singleton
    @Provides
    fun provideSeriesApiService(retrofit: Retrofit): SeriesApiService = retrofit.create(SeriesApiService::class.java)
}
