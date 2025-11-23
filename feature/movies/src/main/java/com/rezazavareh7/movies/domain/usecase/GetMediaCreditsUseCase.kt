package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.common.domain.handleResult
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaDetailsResult
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
                val result =
                    when (mediaType) {
                        MediaCategory.MOVIE -> movieRepository.getMovieCredits(id)
                        MediaCategory.SERIES -> seriesRepository.getSeriesCredits(id)
                    }

                result.handleResult(
                    onSuccessAction = { data ->
                        MediaDetailsResult(mediaCredits = data)
                    },
                    onErrorAction = { error ->
                        MediaDetailsResult(dataError = error, hasError = true)
                    },
                )
            }
    }
