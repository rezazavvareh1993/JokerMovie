package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.common.domain.handleResult
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaDetailsResult
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMediaDetailsUseCase
    @Inject
    constructor(
        private val moviesRepository: MoviesRepository,
        private val seriesRepository: SeriesRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(
            mediaId: Long,
            mediaCategory: MediaCategory,
        ): MediaDetailsResult =
            withContext(dispatcher) {
                val result =
                    if (mediaCategory == MediaCategory.MOVIE) {
                        moviesRepository.getMovieDetail(mediaId)
                    } else {
                        seriesRepository.getSeriesDetail(mediaId)
                    }

                return@withContext result.handleResult(
                    onSuccessAction = { data ->
                        MediaDetailsResult(mediaDetailsData = data)
                    },
                    onErrorAction = { error ->
                        MediaDetailsResult(hasError = true, dataError = error)
                    },
                )
            }
    }
