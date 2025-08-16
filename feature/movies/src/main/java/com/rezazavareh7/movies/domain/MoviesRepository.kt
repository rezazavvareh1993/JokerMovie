package com.rezazavareh7.movies.domain

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.Category
import com.rezazavareh7.movies.domain.model.FavoriteData
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(query: String): Flow<PagingData<MovieData>>

    suspend fun getMovieDetail(movieId: Long): GetMovieDetailNetworkState

    fun getTopRatedMovies(): Flow<PagingData<MovieData>>

    fun getUpcomingMovies(): Flow<PagingData<MovieData>>

    fun getPopularMovies(): Flow<PagingData<MovieData>>

    fun getNowPlayingMovies(): Flow<PagingData<MovieData>>

    fun fetchFavorites(category: Category): Flow<List<FavoriteData>>

    suspend fun insertFavoriteItem(movieData: MovieData)

    suspend fun deleteMovieFromFavoriteMovies(movieData: MovieData)

    suspend fun findItemById(
        category: Category,
        id: Long,
    ): FavoriteData?
}
