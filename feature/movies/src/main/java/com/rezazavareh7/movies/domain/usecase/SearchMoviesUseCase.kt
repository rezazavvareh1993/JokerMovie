package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.MoviesResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchMoviesUseCase
    @Inject
    constructor(
        private val repository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(query: String): MoviesResult =
            withContext(dispatcher) {
                val result = repository.searchMovie(query)
                MoviesResult(moviesPagedData = result)
            }
    }
