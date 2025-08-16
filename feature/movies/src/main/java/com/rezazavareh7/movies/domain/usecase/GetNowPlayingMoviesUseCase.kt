package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.MoviesResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNowPlayingMoviesUseCase
    @Inject
    constructor(
        private val moviesRepository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(): MoviesResult =
            withContext(dispatcher) {
                val result = moviesRepository.getNowPlayingMovies()
                MoviesResult(nowPlayingMovies = result)
            }
    }
