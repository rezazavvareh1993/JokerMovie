package com.rezazavareh7.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rezazavareh.database.MovieDataBase
import com.rezazavareh.database.MovieEntity
import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import com.rezazavareh7.movies.data.mapper.toMovieEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDb: MovieDataBase,
    private val moviesApiService: MoviesApiService,
) : RemoteMediator<Int, MovieEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        return try {
            val loadKey =
                when (loadType) {
                    LoadType.PREPEND ->
                        return MediatorResult.Success(
                            endOfPaginationReached = true,
                        )

                    LoadType.REFRESH -> 1
                    LoadType.APPEND -> {
                        val lastItem = state.lastItemOrNull()
                        if (lastItem == null) {
                            1
                        } else {
                            (lastItem.id / 100) + 1
                        }
                    }
                }

            val movies =
                moviesApiService.getTopRatedMovies(
                    page = loadKey.toInt(),
                )

            movieDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDb.movieDao.clearAll()
                }
                val movieEntities = movies.results.map { it.toMovieEntity() }
                movieDb.movieDao.upsetAll(movieEntities)
            }

            MediatorResult.Success(endOfPaginationReached = movies.results.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
