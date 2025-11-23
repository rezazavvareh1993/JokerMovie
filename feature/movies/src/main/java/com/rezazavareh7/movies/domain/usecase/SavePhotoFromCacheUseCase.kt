package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.repository.MediaSaverRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavePhotoFromCacheUseCase
    @Inject
    constructor(
        private val mediaSaverRepository: MediaSaverRepository,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(imageUrl: String): Result<Unit> =
            withContext(dispatcher) {
                mediaSaverRepository.savePhotoToGallery(imageUrl)
            }
    }
