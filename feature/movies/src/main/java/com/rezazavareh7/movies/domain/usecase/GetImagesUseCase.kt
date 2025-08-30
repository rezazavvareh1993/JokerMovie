package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaDetailsResult
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.repository.MoviesRepository
import com.rezazavareh7.movies.domain.repository.SeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetImagesUseCase
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
                        moviesRepository.getImages(mediaId)
                    } else {
                        seriesRepository.getImages(mediaId)
                    }

                when (result) {
                    is BasicNetworkState.Error ->
                        MediaDetailsResult(hasError = true, errorMessage = result.message)

                    is BasicNetworkState.Success -> MediaDetailsResult(images = result.data)
                }
            }
    }
