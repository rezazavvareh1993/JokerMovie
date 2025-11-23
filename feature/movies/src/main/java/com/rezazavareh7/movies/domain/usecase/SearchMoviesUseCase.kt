package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.SEARCH_MOVIE_HISTORY
import com.rezazavareh7.movies.domain.model.MediaResult
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import com.rezazavareh7.network.util.fromJsonList
import com.rezazavareh7.network.util.toJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchMoviesUseCase
    @Inject
    constructor(
        private val repository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher,
        private val regularDataStoreManager: RegularDataStoreManager,
    ) {
        suspend operator fun invoke(query: String): MediaResult =
            withContext(dispatcher) {
                val newHistoryList = mutableListOf<String>()
                newHistoryList.add(query)
                val queryMovieHistoryJson =
                    regularDataStoreManager.getString(SEARCH_MOVIE_HISTORY).first()
                if (queryMovieHistoryJson.isNotEmpty()) {
                    val queryMovieHistoryList = queryMovieHistoryJson.fromJsonList<String>()

                    if (!queryMovieHistoryList.isNullOrEmpty()) {
                        queryMovieHistoryList.forEachIndexed forEachIndex@{ index, oldQuery ->
                            if (newHistoryList.size < 5 && query != oldQuery) {
                                newHistoryList.add(oldQuery)
                            } else {
                                return@forEachIndex
                            }
                        }
                    }
                }
                regularDataStoreManager.saveData(SEARCH_MOVIE_HISTORY, newHistoryList.toJson() ?: "")
                val result = repository.searchMovies(query)
                MediaResult(searchResult = result)
            }
    }
