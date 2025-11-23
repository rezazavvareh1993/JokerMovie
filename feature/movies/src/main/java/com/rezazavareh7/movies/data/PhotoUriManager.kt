package com.rezazavareh7.movies.data

import android.content.Context
import com.rezazavareh7.movies.data.repositoryimpl.PHOTO_EXTENSION
import com.rezazavareh7.movies.data.repositoryimpl.TEMP_PHOTO_PREFIX
import com.rezazavareh7.movies.domain.networkstate.SharePhotoState
import com.rezazavareh7.ui.components.glide.GlideUtils
import timber.log.Timber
import java.io.File

suspend fun generateShareablePhoto(
    photoUrl: String,
    context: Context,
): SharePhotoState =
    try {
        createTempFile(photoUrl, context)
    } catch (error: Throwable) {
        Timber.e(error, "Error in share process: ${error.message}")
        SharePhotoState.Error(error.message.toString())
    }

private suspend fun createTempFile(
    photoUrl: String,
    context: Context,
): SharePhotoState {
    val cachedImageFile = GlideUtils.getCachedImageFile(context, photoUrl)
    if (!cachedImageFile.exists()) {
        return SharePhotoState.Error("Cached image file does not exist.")
    }

    val tempFile = createTemporaryFile(context, cachedImageFile)
    return SharePhotoState.Success(tempFile.absolutePath)
}

private fun createTemporaryFile(
    context: Context,
    cachedImageFile: File,
): File {
    val tempFileName = "$TEMP_PHOTO_PREFIX${System.currentTimeMillis()}$PHOTO_EXTENSION"
    val tempFile = File(context.filesDir, tempFileName)
    cachedImageFile.copyTo(tempFile, overwrite = true)
    return tempFile
}
