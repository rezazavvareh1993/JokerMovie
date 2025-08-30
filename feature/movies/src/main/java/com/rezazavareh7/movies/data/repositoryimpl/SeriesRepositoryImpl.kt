package com.rezazavareh7.movies.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rezazavareh7.movies.data.apiservice.SeriesApiService
import com.rezazavareh7.movies.data.paging.AiringTodaySeriesPagingSource
import com.rezazavareh7.movies.data.paging.OnTheAirSeriesPagingSource
import com.rezazavareh7.movies.data.paging.PopularSeriesPagingSource
import com.rezazavareh7.movies.data.paging.SearchSeriesPagingSource
import com.rezazavareh7.movies.data.paging.TopRatedSeriesPagingSource
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesRepositoryImpl
    @Inject
    constructor(
        private val seriesApiService: SeriesApiService,
        private val popularSeriesPagingSource: PopularSeriesPagingSource,
        private val topRatedSeriesPagingSource: TopRatedSeriesPagingSource,
        private val onTheAirSeriesPagingSource: OnTheAirSeriesPagingSource,
        private val airingTodaySeriesPagingSource: AiringTodaySeriesPagingSource,
        private val searchSeriesFactory: SearchSeriesPagingSource.Factory,
    ) : SeriesRepository {
        override fun searchSeries(query: String): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { searchSeriesFactory.create(query) },
            ).flow

        override fun getTopRatedSeries(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { topRatedSeriesPagingSource },
            ).flow

        override fun getOnTheAirSeries(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { onTheAirSeriesPagingSource },
            ).flow

        override fun getPopularSeries(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { popularSeriesPagingSource },
            ).flow

        override fun getAiringTodaySeries(): Flow<PagingData<MediaData>> =
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = { airingTodaySeriesPagingSource },
            ).flow
    }
