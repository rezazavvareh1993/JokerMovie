package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.SEARCH_MOVIE_HISTORY
import com.rezazavareh7.common.util.fromJsonList
import com.rezazavareh7.movies.domain.model.MediaResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSearchMovieHistoryUseCase
    @Inject
    constructor(
        private val dispatcher: CoroutineDispatcher,
        private val regularDataStoreManager: RegularDataStoreManager,
    ) {
        suspend operator fun invoke(): MediaResult =
            withContext(dispatcher) {
                val movieQueriesHistory: Flow<List<String>> =
                    flow {
                        regularDataStoreManager
                            .getString(SEARCH_MOVIE_HISTORY)
                            .collect { queryMovieHistoryJson ->
                                if (queryMovieHistoryJson.isNotEmpty()) {
                                    val queryMovieHistoryList = queryMovieHistoryJson.fromJsonList<String>()
                                    emit(queryMovieHistoryList ?: emptyList())
                                } else {
                                    emit(emptyList())
                                }
                            }
                    }
                MediaResult(movieQueriesHistory = movieQueriesHistory)
            }
    }
