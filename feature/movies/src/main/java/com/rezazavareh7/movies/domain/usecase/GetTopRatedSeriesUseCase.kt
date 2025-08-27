package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaResult
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTopRatedSeriesUseCase
    @Inject
    constructor(
        private val seriesRepository: SeriesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(): MediaResult =
            withContext(dispatcher) {
                val result = seriesRepository.getTopRatedSeries()
                MediaResult(topRatedSeries = result)
            }
    }
