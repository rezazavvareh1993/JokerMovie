package com.rezazavareh7.movies.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rezazavareh.database.FavoriteDao
import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import com.rezazavareh7.movies.data.mapper.FavoritesMapper
import com.rezazavareh7.movies.data.mapper.MovieDetailsMapper
import com.rezazavareh7.movies.data.paging.NowPlayingMoviePagingSource
import com.rezazavareh7.movies.data.paging.PopularMoviePagingSource
import com.rezazavareh7.movies.data.paging.SearchMoviePagingSource
import com.rezazavareh7.movies.data.paging.TopRatedMoviePagingSource
import com.rezazavareh7.movies.data.paging.UpcomingMoviePagingSource
import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.Category
import com.rezazavareh7.movies.domain.model.FavoriteData
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl
    @Inject
    constructor(
        private val moviesApiServices: MoviesApiService,
        private val favoriteDao: FavoriteDao,
        private val favoritesMapper: FavoritesMapper,
        private val movieDetailMapper: MovieDetailsMapper,
        private val topRatedMoviePagingSource: TopRatedMoviePagingSource,
        private val upcomingMoviePagingSource: UpcomingMoviePagingSource,
        private val popularMoviePagingSource: PopularMoviePagingSource,
        private val nowPlayingMoviePagingSource: NowPlayingMoviePagingSource,
        private val searchMoviePagerFactory: SearchMoviePagingSource.Factory,
    ) : MoviesRepository {
        override fun searchMovies(query: String): Flow<PagingData<MovieData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { searchMoviePagerFactory.create(query) },
            ).flow

        override suspend fun getMovieDetail(movieId: Long): GetMovieDetailNetworkState {
            val result = moviesApiServices.getMovieDetails(movieId)
            return movieDetailMapper(result)
        }

        override fun getTopRatedMovies(): Flow<PagingData<MovieData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { topRatedMoviePagingSource },
            ).flow

        override fun getUpcomingMovies(): Flow<PagingData<MovieData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { upcomingMoviePagingSource },
            ).flow

        override fun getPopularMovies(): Flow<PagingData<MovieData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { popularMoviePagingSource },
            ).flow

        override fun getNowPlayingMovies(): Flow<PagingData<MovieData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { nowPlayingMoviePagingSource },
            ).flow

        override fun fetchFavorites(category: Category): Flow<List<FavoriteData>> =
            flow {
                val entities =
                    favoriteDao.getFavorites(category.toString()).collect {
                        if (it.isNotEmpty()) {
                            val dataList = favoritesMapper.mapToData(*it.toTypedArray())
                            emit(dataList)
                        }
                    }
            }

        override suspend fun insertFavoriteItem(movieData: MovieData) {
            favoriteDao.saveFavoriteItem(favoritesMapper.mapToEntity(movieData).first())
        }

        override suspend fun deleteMovieFromFavoriteMovies(movieData: MovieData) =
            favoriteDao.delete(favoritesMapper.mapToEntity(movieData).first())

        override suspend fun findItemById(
            category: Category,
            id: Long,
        ): FavoriteData? {
            val foundEntity = favoriteDao.findItemById(category = category.toString(), id = id)
            return foundEntity?.let {
                favoritesMapper.mapToData(foundEntity).first()
            }
        }
    }
