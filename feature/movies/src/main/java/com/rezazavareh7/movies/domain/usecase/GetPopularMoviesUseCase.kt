package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaResult
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPopularMoviesUseCase
    @Inject
    constructor(
        private val moviesRepository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(): MediaResult =
            withContext(dispatcher) {
                val result = moviesRepository.getPopularMovies()
                MediaResult(popularMovies = result)
            }
    }
