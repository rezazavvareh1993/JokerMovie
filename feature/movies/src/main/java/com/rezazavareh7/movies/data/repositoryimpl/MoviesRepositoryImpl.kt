package com.rezazavareh7.movies.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rezazavareh7.common.domain.DataError
import com.rezazavareh7.common.domain.Result
import com.rezazavareh7.common.domain.map
import com.rezazavareh7.movies.data.apiservice.MovieApiService
import com.rezazavareh7.movies.data.mapper.MediaImagesMapper
import com.rezazavareh7.movies.data.mapper.MovieCreditsMapper
import com.rezazavareh7.movies.data.mapper.MovieDetailsMapper
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.model.MovieCreditsResponse
import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.data.paging.GenericPagingSource
import com.rezazavareh7.movies.data.paging.SearchPagingSource
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.model.MediaDetailData
import com.rezazavareh7.movies.domain.model.MediaImage
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import com.rezazavareh7.network.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl
    @Inject
    constructor(
        private val movieApiServices: MovieApiService,
        private val movieDetailMapper: MovieDetailsMapper,
        private val movieCreditsMapper: MovieCreditsMapper,
        private val searchPagerFactory: SearchPagingSource.Factory,
        private val mediaImagesMapper: MediaImagesMapper,
        private val moviesMapper: MoviesMapper,
    ) : MoviesRepository {
        override fun searchMovies(query: String): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    searchPagerFactory.create(MediaCategory.MOVIE, query)
                },
            ).flow

        override suspend fun getMovieDetail(movieId: Long): Result<MediaDetailData, DataError> =
            safeApiCall<MovieDetailsResponse> {
                movieApiServices.getMovieDetails(movieId)
            }.map { response ->
                movieDetailMapper(response)
            }

        override fun getTopRatedMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    GenericPagingSource(
                        apiCall = { page -> movieApiServices.getTopRatedMovies(page) },
                        mapper = { response -> moviesMapper.invoke(response) },
                    )
                },
            ).flow

        override fun getUpcomingMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    GenericPagingSource(
                        apiCall = { page -> movieApiServices.getUpcomingMovies(page) },
                        mapper = { response -> moviesMapper.invoke(response) },
                    )
                },
            ).flow

        override fun getSimilarMovies(movieId: Long): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    GenericPagingSource(
                        apiCall = { page -> movieApiServices.getSimilarMovies(movieId, page) },
                        mapper = { response -> moviesMapper.invoke(response) },
                    )
                },
            ).flow

        override fun getPopularMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    GenericPagingSource(
                        apiCall = { page -> movieApiServices.getPopularMovies(page) },
                        mapper = { response -> moviesMapper.invoke(response) },
                    )
                },
            ).flow

        override fun getNowPlayingMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    GenericPagingSource(
                        apiCall = { page -> movieApiServices.getNowPlayingMovies(page) },
                        mapper = { response -> moviesMapper.invoke(response) },
                    )
                },
            ).flow

        override suspend fun getImages(movieId: Long): BasicNetworkState<List<MediaImage>> =
            mediaImagesMapper(movieApiServices.getImages(movieId))

        override suspend fun getMovieCredits(movieId: Long): Result<List<Credit>, DataError> =
            safeApiCall<MovieCreditsResponse> {
                movieApiServices.getCredits(movieId)
            }.map { response ->
                movieCreditsMapper(response)
            }
    }
