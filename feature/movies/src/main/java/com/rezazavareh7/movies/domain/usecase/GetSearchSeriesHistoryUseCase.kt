package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.SEARCH_SERIES_HISTORY
import com.rezazavareh7.common.util.fromJsonList
import com.rezazavareh7.movies.domain.model.MediaResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSearchSeriesHistoryUseCase
    @Inject
    constructor(
        private val dispatcher: CoroutineDispatcher,
        private val regularDataStoreManager: RegularDataStoreManager,
    ) {
        suspend operator fun invoke(): MediaResult =
            withContext(dispatcher) {
                val querySeriesHistoryJson =
                    regularDataStoreManager.getString(SEARCH_SERIES_HISTORY).first()
                val querySeriesHistoryList = querySeriesHistoryJson.fromJsonList<String>()

                MediaResult(seriesSearchHistory = querySeriesHistoryList ?: emptyList())
            }
    }
