package com.rezazavareh7.movies.data.repositoryimpl

import androidx.paging.PagingData
import com.rezazavareh7.movies.data.apiservice.SeriesApiService
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesRepositoryImpl
    @Inject
    constructor(
        private val seriesApiService: SeriesApiService,
    ) : SeriesRepository {
        override fun searchSeries(query: String): Flow<PagingData<MediaData>> {
            TODO("Not yet implemented")
        }

        override suspend fun getSeriesDetail(seriesId: Long): BasicNetworkState<Unit> {
            TODO("Not yet implemented")
        }

        override fun getTopRatedSeries(): Flow<PagingData<MediaData>> {
            TODO("Not yet implemented")
        }

        override fun getOnTheAirSeries(): Flow<PagingData<MediaData>> {
            TODO("Not yet implemented")
        }

        override fun getPopularSeries(): Flow<PagingData<MediaData>> {
            TODO("Not yet implemented")
        }

        override fun getAiringTodaySeries(): Flow<PagingData<MediaData>> {
            TODO("Not yet implemented")
        }
    }
