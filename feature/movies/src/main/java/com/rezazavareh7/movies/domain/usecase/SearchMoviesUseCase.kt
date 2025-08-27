package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaResult
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchMoviesUseCase
    @Inject
    constructor(
        private val repository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(query: String): MediaResult =
            withContext(dispatcher) {
                val result = repository.searchMovies(query)
                MediaResult(searchResult = result)
            }
    }
