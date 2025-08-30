package com.rezazavareh7.movies.domain.repository

import androidx.paging.PagingData
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.model.MediaDetailData
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(query: String): Flow<PagingData<MediaData>>

    suspend fun getMovieDetail(movieId: Long): BasicNetworkState<MediaDetailData>

    fun getTopRatedMovies(): Flow<PagingData<MediaData>>

    fun getUpcomingMovies(): Flow<PagingData<MediaData>>

    fun getPopularMovies(): Flow<PagingData<MediaData>>

    fun getNowPlayingMovies(): Flow<PagingData<MediaData>>
}
