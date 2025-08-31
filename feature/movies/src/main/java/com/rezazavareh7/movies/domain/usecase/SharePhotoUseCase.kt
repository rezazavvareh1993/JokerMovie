package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh7.movies.domain.model.MediaImageResult
import com.rezazavareh7.movies.domain.networkstate.SharePhotoState
import com.rezazavareh7.movies.domain.repository.MediaSaverRepository
import javax.inject.Inject

class SharePhotoUseCase
    @Inject
    constructor(private val mediaSaverRepository: MediaSaverRepository) {
        suspend operator fun invoke(photoUrl: String): MediaImageResult =
            when (val response = mediaSaverRepository.sharePhoto(photoUrl)) {
                is SharePhotoState.Error ->
                    MediaImageResult(
                        errorMessage = response.message,
                        hasError = true,
                    )

                is SharePhotoState.Success ->
                    MediaImageResult(
                        hasError = false,
                        sharePhotoAbsolutePath = response.absolutePath,
                    )
            }
    }
