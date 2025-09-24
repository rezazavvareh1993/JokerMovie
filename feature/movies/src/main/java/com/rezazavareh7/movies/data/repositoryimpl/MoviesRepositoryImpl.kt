package com.rezazavareh7.movies.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rezazavareh7.movies.data.apiservice.MovieApiService
import com.rezazavareh7.movies.data.mapper.MediaImagesMapper
import com.rezazavareh7.movies.data.mapper.MovieCreditsMapper
import com.rezazavareh7.movies.data.mapper.MovieDetailsMapper
import com.rezazavareh7.movies.data.paging.NowPlayingMoviePagingSource
import com.rezazavareh7.movies.data.paging.PopularMoviePagingSource
import com.rezazavareh7.movies.data.paging.SearchMoviePagingSource
import com.rezazavareh7.movies.data.paging.TopRatedMoviePagingSource
import com.rezazavareh7.movies.data.paging.UpcomingMoviePagingSource
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.model.MediaDetailData
import com.rezazavareh7.movies.domain.model.MediaImage
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl
    @Inject
    constructor(
        private val movieApiServices: MovieApiService,
        private val movieDetailMapper: MovieDetailsMapper,
        private val movieCreditsMapper: MovieCreditsMapper,
        private val topRatedMoviePagingSource: TopRatedMoviePagingSource,
        private val upcomingMoviePagingSource: UpcomingMoviePagingSource,
        private val popularMoviePagingSource: PopularMoviePagingSource,
        private val nowPlayingMoviePagingSource: NowPlayingMoviePagingSource,
        private val searchMoviePagerFactory: SearchMoviePagingSource.Factory,
        private val mediaImagesMapper: MediaImagesMapper,
    ) : MoviesRepository {
        override fun searchMovies(query: String): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { searchMoviePagerFactory.create(query) },
            ).flow

        override suspend fun getMovieDetail(movieId: Long): BasicNetworkState<MediaDetailData> {
            val result = movieApiServices.getMovieDetails(movieId)
            return movieDetailMapper(result)
        }

        override fun getTopRatedMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { topRatedMoviePagingSource },
            ).flow

        override fun getUpcomingMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { upcomingMoviePagingSource },
            ).flow

        override fun getPopularMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { popularMoviePagingSource },
            ).flow

        override fun getNowPlayingMovies(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { nowPlayingMoviePagingSource },
            ).flow

        override suspend fun getImages(moviesId: Long): BasicNetworkState<List<MediaImage>> =
            mediaImagesMapper(
                movieApiServices.getImages(moviesId),
            )

        override suspend fun getMovieCredits(moviesId: Long): BasicNetworkState<List<Credit>> =
            movieCreditsMapper(
                movieApiServices.getCredits(moviesId),
            )
    }
