package com.rezazavareh7.movies.domain.repository

import androidx.paging.PagingData
import com.rezazavareh7.common.domain.DataError
import com.rezazavareh7.common.domain.Result
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.model.MediaDetailData
import com.rezazavareh7.movies.domain.model.MediaImage
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun searchSeries(query: String): Flow<PagingData<MediaData>>

    fun getTopRatedSeries(): Flow<PagingData<MediaData>>

    fun getOnTheAirSeries(): Flow<PagingData<MediaData>>

    fun getPopularSeries(): Flow<PagingData<MediaData>>

    fun getSimilarSeries(seriesId: Long): Flow<PagingData<MediaData>>

    fun getAiringTodaySeries(): Flow<PagingData<MediaData>>

    suspend fun getSeriesDetail(seriesId: Long): Result<MediaDetailData, DataError.Remote>

    suspend fun getImages(seriesId: Long): BasicNetworkState<List<MediaImage>>

    suspend fun getSeriesCredits(seriesId: Long): Result<List<Credit>, DataError>
}
