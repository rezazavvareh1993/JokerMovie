package com.rezazavareh7.movies.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rezazavareh.database.MovieDao
import com.rezazavareh.database.MovieDataBase
import com.rezazavareh.database.MovieEntity
import com.rezazavareh7.movies.data.MovieRemoteMediator
import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviePagerModule {
    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMoviePager(
        movieDataBase: MovieDataBase,
        movieApi: MoviesApiService,
        movieDao: MovieDao,
    ): Pager<Int, MovieEntity> =
        Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator =
                MovieRemoteMediator(
                    movieDb = movieDataBase,
                    moviesApiService = movieApi,
                ),
            pagingSourceFactory = {
                movieDao.getAllMovies()
            },
        )
}
