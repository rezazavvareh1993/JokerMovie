package com.rezazavareh7.movies.data.repositoryimpl

import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import com.rezazavareh7.movies.data.mapper.MovieDetailsMapper
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.mapper.SearchMovieMapper
import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import com.rezazavareh7.movies.domain.networkstate.GetMoviesNetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl
    @Inject
    constructor(
        private val moviesApiServices: MoviesApiService,
        private val moviesMapper: MoviesMapper,
        private val movieDetailMapper: MovieDetailsMapper,
        private val searchMovieMapper: SearchMovieMapper,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : MoviesRepository {
//        override suspend fun getMovies(): GetMoviesNetworkState =
//            withContext(dispatcher) {
//                val result = moviesApiServices.getTopRatedMovies()
//                moviesMapper(result)
//            }

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
    }
