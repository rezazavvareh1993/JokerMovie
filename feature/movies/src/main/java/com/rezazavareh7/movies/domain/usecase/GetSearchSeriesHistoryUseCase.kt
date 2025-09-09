package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.SEARCH_SERIES_HISTORY
import com.rezazavareh7.common.util.fromJsonList
import com.rezazavareh7.movies.domain.model.MediaResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                val seriesQueriesHistory: Flow<List<String>> =
                    flow {
                        regularDataStoreManager.getString(SEARCH_SERIES_HISTORY)
                            .collect { querySeriesHistoryJson ->
                                if (querySeriesHistoryJson.isNotEmpty()) {
                                    val querySeriesHistoryList =
                                        querySeriesHistoryJson.fromJsonList<String>()
                                    emit(querySeriesHistoryList ?: emptyList())
                                } else {
                                    emit(emptyList())
                                }
                            }
                    }
                MediaResult(seriesQueriesHistory = seriesQueriesHistory)
            }
    }
