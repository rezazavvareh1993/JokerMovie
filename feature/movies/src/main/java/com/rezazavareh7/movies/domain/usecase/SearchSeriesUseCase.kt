package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.SEARCH_SERIES_HISTORY
import com.rezazavareh7.common.util.fromJsonList
import com.rezazavareh7.common.util.toJson
import com.rezazavareh7.movies.domain.model.MediaResult
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchSeriesUseCase
    @Inject
    constructor(
        private val repository: SeriesRepository,
        private val dispatcher: CoroutineDispatcher,
        private val regularDataStoreManager: RegularDataStoreManager,
    ) {
        suspend operator fun invoke(query: String): MediaResult =
            withContext(dispatcher) {
                val newHistoryList = mutableListOf<String>()
                newHistoryList.add(query)
                val querySeriesHistoryJson =
                    regularDataStoreManager.getString(SEARCH_SERIES_HISTORY).first()
                val querySeriesHistoryList = querySeriesHistoryJson.fromJsonList<String>()

                if (!querySeriesHistoryList.isNullOrEmpty()) {
                    querySeriesHistoryList.forEachIndexed forEachIndex@{ index, oldQuery ->
                        if (index < 4) {
                            newHistoryList.add(query)
                        } else {
                            return@forEachIndex
                        }
                    }
                }
                regularDataStoreManager.saveData(SEARCH_SERIES_HISTORY, newHistoryList.toJson() ?: "")
                val result = repository.searchSeries(query)
                MediaResult(searchResult = result)
            }
    }
