package com.rezazavareh7.movies.data.repositoryimpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.rezazavareh.database.MovieDao
import com.rezazavareh.database.MovieEntity
import com.rezazavareh7.movies.data.MovieRemoteMediator
import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import com.rezazavareh7.movies.data.mapper.MovieDetailsMapper
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.mapper.SearchMovieMapper
import com.rezazavareh7.movies.data.mapper.toMovieData
import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import com.rezazavareh7.movies.domain.networkstate.GetMoviesNetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl
    @Inject
    constructor(
        private val moviesApiServices: MoviesApiService,
        private val moviesMapper: MoviesMapper,
        private val movieDetailMapper: MovieDetailsMapper,
        private val searchMovieMapper: SearchMovieMapper,
        private val movieDao: MovieDao,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
        private val moviePager: Pager<Int, MovieEntity>,
    ) : MoviesRepository {

        override suspend fun searchMovie(query: String): GetMoviesNetworkState =
            withContext(dispatcher) {
                val result = moviesApiServices.searchMovie(query = query)
                searchMovieMapper(result)
            }

        override suspend fun getMovieDetail(movieId: Long): GetMovieDetailNetworkState =
            withContext(dispatcher) {
                val result = moviesApiServices.getMovieDetails(movieId)
                movieDetailMapper(result)
            }

        @OptIn(ExperimentalPagingApi::class)
        override fun getPagedMovies(): Flow<PagingData<MovieData>> =
            moviePager.flow.map { pagingData ->
                pagingData.map {
                    it.toMovieData()
                }
            }
    }
