package com.rezazavareh7.movies.data.repositoryimpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rezazavareh.database.FavoriteDao
import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import com.rezazavareh7.movies.data.mapper.MovieDetailsMapper
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.mapper.SearchMovieMapper
import com.rezazavareh7.movies.data.paging.MoviePagingSource
import com.rezazavareh7.movies.data.paging.SearchMoviePagingSource
import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl
    @Inject
    constructor(
        private val moviesApiServices: MoviesApiService,
        private val moviesMapper: MoviesMapper,
        private val movieDetailMapper: MovieDetailsMapper,
        private val searchMovieMapper: SearchMovieMapper,
        private val favoriteDao: FavoriteDao,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
        private val moviePagingSource: MoviePagingSource,
        private val searchMoviePagerFactory: SearchMoviePagingSource.Factory,
    ) : MoviesRepository {
        override fun searchMovie(query: String): Flow<PagingData<MovieData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { searchMoviePagerFactory.create(query) },
            ).flow

        override suspend fun getMovieDetail(movieId: Long): GetMovieDetailNetworkState =
            withContext(dispatcher) {
                val result = moviesApiServices.getMovieDetails(movieId)
                movieDetailMapper(result)
            }

        @OptIn(ExperimentalPagingApi::class)
        override fun getPagedMovies(): Flow<PagingData<MovieData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { moviePagingSource },
            ).flow
    }
