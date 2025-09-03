package com.rezazavareh7.movies.domain.repository

import com.rezazavareh7.movies.domain.networkstate.SharePhotoState

interface MediaSaverRepository {
    suspend fun savePhotoToGallery(imageUrl: String): Result<Unit>

    suspend fun sharePhoto(photoUrl: String): SharePhotoState

    suspend fun deleteTempFile(filePath: String): Result<Unit>
}
