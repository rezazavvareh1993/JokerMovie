package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaDetailsResult
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMediaCreditsUseCase
    @Inject
    constructor(
        private val movieRepository: MoviesRepository,
        private val seriesRepository: SeriesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(
            id: Long,
            mediaType: MediaCategory,
        ): MediaDetailsResult =
            with(dispatcher) {
                val networkState =
                    when (mediaType) {
                        MediaCategory.MOVIE -> movieRepository.getMovieCredits(id)
                        MediaCategory.SERIES -> seriesRepository.getSeriesCredits(id)
                    }

                when (networkState) {
                    is BasicNetworkState.Success ->
                        MediaDetailsResult(mediaCredits = networkState.data, hasError = false)

                    is BasicNetworkState.Error ->
                        MediaDetailsResult(errorMessage = networkState.message, hasError = true)
                }
            }
    }
