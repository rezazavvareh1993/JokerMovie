package com.rezazavareh7.movies.di

import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesApiServiceModule {
    @Singleton
    @Provides
    fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService = retrofit.create(MoviesApiService::class.java)
}
