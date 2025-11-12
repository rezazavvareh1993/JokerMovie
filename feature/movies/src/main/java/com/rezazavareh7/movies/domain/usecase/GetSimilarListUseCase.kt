package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.movies.domain.model.MediaDetailsResult
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSimilarListUseCase
    @Inject
    constructor(
        private val movieRepository: MoviesRepository,
        private val seriesRepository: SeriesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(mediaData: MediaData): MediaDetailsResult =
            withContext(dispatcher) {
                val result =
                    when (mediaData.mediaCategory) {
                        MediaCategory.SERIES -> seriesRepository.getSimilarSeries(mediaData.id)
                        MediaCategory.MOVIE -> movieRepository.getSimilarMovies(mediaData.id)
                    }
                MediaDetailsResult(mediaSimilar = result)
            }
    }
