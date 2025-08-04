package com.rezazavareh7.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rezazavareh.database.MovieDataBase
import com.rezazavareh.database.MovieEntity
import com.rezazavareh.database.RemoteKeyEntity
import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import com.rezazavareh7.movies.data.mapper.toMovieEntity
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

private const val REMOTE_KEY_ID = "REMOTE_KEY_ID"
@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator
    @Inject
    constructor(
        private val movieDb: MovieDataBase,
        private val moviesApiService: MoviesApiService,
    ) : RemoteMediator<Int, MovieEntity>() {
        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, MovieEntity>,
        ): MediatorResult {
            return try {
                val page =
                    when (loadType) {
                        LoadType.REFRESH -> 1
                        LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                        LoadType.APPEND -> {
                            val remoteKey = movieDb.remoteKeyDao.getById(REMOTE_KEY_ID)
                            if (remoteKey == null || remoteKey.nextOffset == 1) {
                                return MediatorResult.Success(endOfPaginationReached = true)
                            }
                            remoteKey.nextOffset
                        }
                    }

                val moviesResponse =
                    moviesApiService.getTopRatedMovies(
                        page = page.toInt(),
                    )

                val results = moviesResponse.results
                val nextOffset =
                    if (moviesResponse.page < moviesResponse.total_pages) {
                        moviesResponse.page + 1
                    } else {
                        1
                    }

                movieDb.remoteKeyDao.insert(
                    RemoteKeyEntity(
                        id = "REMOTE_KEY_ID",
                        nextOffset = nextOffset,
                    ),
                )

                movieDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDb.movieDao.clearAll()
                        movieDb.remoteKeyDao.deleteById(id = REMOTE_KEY_ID)
                    }
                    val movieEntities = results.map { it.toMovieEntity() }
                    movieDb.movieDao.insertAll(movieEntities)
                    movieDb.remoteKeyDao.insert(
                        RemoteKeyEntity(
                            id = REMOTE_KEY_ID,
                            nextOffset = nextOffset,
                        ),
                    )
                }

                MediatorResult.Success(endOfPaginationReached = results.isEmpty())
            } catch (e: IOException) {
                MediatorResult.Error(e)
            } catch (e: HttpException) {
                MediatorResult.Error(e)
            }
        }
    }
