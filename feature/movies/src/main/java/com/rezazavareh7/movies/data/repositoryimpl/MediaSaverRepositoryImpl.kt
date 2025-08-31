package com.rezazavareh7.movies.data.repositoryimpl

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.rezazavareh7.movies.data.generateShareablePhoto
import com.rezazavareh7.movies.domain.networkstate.SharePhotoState
import com.rezazavareh7.movies.domain.repository.MediaSaverRepository
import com.rezazavareh7.ui.glide.GlideUtils
import com.rezazavareh7.ui.util.Constants.IMAGE_BASE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val CONTENT_TYPES = "image/jpeg"
private const val DATE_FORMAT = "yyyyMMdd_HHmmss"
private const val PHOTO_PREFIX = "IMG_"
internal const val TEMP_PHOTO_PREFIX = "shared_image_"
internal const val PHOTO_EXTENSION = ".jpg"

class MediaSaverRepositoryImpl
    @Inject
    constructor(
        @param:ApplicationContext private val context: Context,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : MediaSaverRepository {
        override suspend fun savePhotoToGallery(imageUrl: String): Result<Unit> {
            return try {
                val photoFile = GlideUtils.getCachedImageFile(context, "$IMAGE_BASE_URL$imageUrl")
                val fileName = generateUniqueFileName()
                movePhotoToGallery(context, photoFile, fileName)
                Result.success(Unit)
            } catch (e: Exception) {
                Timber.e(e)
                Result.failure(e)
            }
        }

        override suspend fun sharePhoto(photoUrl: String): SharePhotoState =
            withContext(dispatcher) {
                deleteTemporarySharedFiles()
                generateShareablePhoto(photoUrl, context)
            }

        override suspend fun deleteTempFile(filePath: String): Result<Unit> {
            return withContext(dispatcher) {
                val tempFile = File(filePath)
                if (tempFile.exists()) {
                    tempFile.delete()
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to delete temporary file: $filePath"))
                }
            }
        }

        private suspend fun movePhotoToGallery(
            context: Context,
            imageFile: File,
            fileName: String,
        ) {
            withContext(dispatcher) {
                val resolver = context.contentResolver
                val contentValues = getContentValues(fileName)

                val uri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                        ?: throw IOException("Failed to create new MediaStore record.")

                resolver.openOutputStream(uri)?.use { outputStream ->
                    imageFile.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                } ?: throw IOException("Failed to open output stream.")

                notifyGalleryOfNewPhoto(contentValues, resolver, uri, context)
            }
        }

        private fun notifyGalleryOfNewPhoto(
            contentValues: ContentValues,
            resolver: ContentResolver,
            uri: Uri,
            context: Context,
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                resolver.update(uri, contentValues, null, null)
            } else {
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(File(contentValues.getAsString(MediaStore.Images.Media.DATA)).absolutePath),
                    arrayOf(CONTENT_TYPES),
                    null,
                )
            }
        }

        private fun getContentValues(fileName: String) =
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, CONTENT_TYPES)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                } else {
                    val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
                    val file = File(picturesDir, fileName)
                    put(MediaStore.Images.Media.DATA, file.absolutePath)
                }
            }

        private fun generateUniqueFileName(): String {
            val timestamp = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date())
            return "$PHOTO_PREFIX$timestamp$PHOTO_EXTENSION"
        }

        private suspend fun deleteTemporarySharedFiles() {
            withContext(dispatcher) {
                val tempDir = context.filesDir
                val tempFiles =
                    tempDir.listFiles { _, name ->
                        name.startsWith(TEMP_PHOTO_PREFIX)
                    }

                tempFiles?.forEach { file ->
                    if (file.exists()) {
                        file.delete()
                    }
                }
            }
        }
    }
