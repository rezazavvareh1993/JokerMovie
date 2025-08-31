package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.repository.MediaSaverRepository
import javax.inject.Inject

class SavePhotoFromCacheUseCase
    @Inject
    constructor(
        private val mediaSaverRepository: MediaSaverRepository,
    ) {
        suspend operator fun invoke(imageUrl: String): Result<Unit> {
            return mediaSaverRepository.savePhotoToGallery(imageUrl)
        }
    }
