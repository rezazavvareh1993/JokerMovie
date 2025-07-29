package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.MoviesRepository
import com.rezazavareh7.movies.domain.model.MovieDetailsResult
import com.rezazavareh7.movies.domain.networkstate.GetMovieDetailNetworkState
import javax.inject.Inject

class GetMovieDetailsUseCase
    @Inject
    constructor(
        private val moviesRepository: MoviesRepository,
    ) {
        suspend operator fun invoke(movieId: Long): MovieDetailsResult =
            when (val result = moviesRepository.getMovieDetail(movieId)) {
                is GetMovieDetailNetworkState.Success -> MovieDetailsResult(movieDetailsData = result.data)
                is GetMovieDetailNetworkState.Error ->
                    MovieDetailsResult(
                        hasError = true,
                        errorMessage = result.message,
                    )
            }
    }
