package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.MovieDetailsResult
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieDetailsUseCase
    @Inject
    constructor(
        private val moviesRepository: MoviesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(movieId: Long): MovieDetailsResult =
            withContext(dispatcher) {
                when (val result = moviesRepository.getMovieDetail(movieId)) {
                    is GetMovieDetailNetworkState.Success -> MovieDetailsResult(movieDetailsData = result.data)
                    is GetMovieDetailNetworkState.Error ->
                        MovieDetailsResult(
                            hasError = true,
                            errorMessage = result.message,
                        )
                }
            }
    }
