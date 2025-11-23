package com.rezazavareh7.ui.util

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.rezazavareh7.ui.R
import timber.log.Timber
import java.io.File

private const val IMAGE_TYPE = "image/*"
private const val PROVIDER_AUTHORITIES = ".share.fileProvider"

fun shareProvider(
    context: Context,
    filePath: String,
    intentCreated: (Intent) -> Unit,
) {
    val uri = generateFileUri(context, File(filePath))
    uri?.let {
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                type = IMAGE_TYPE
                clipData = ClipData("", arrayOf(IMAGE_TYPE), ClipData.Item(it))
                putExtra(Intent.EXTRA_STREAM, it)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

        val chooserIntent =
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.select_the_app_you_want_to_share_with),
            )

        intentCreated(chooserIntent)
    }
}

private fun generateFileUri(
    context: Context,
    tempFile: File,
): Uri? =
    try {
        val uri =
            FileProvider.getUriForFile(
                context,
                "${context.packageName}$PROVIDER_AUTHORITIES",
                tempFile,
            )
        uri
    } catch (exception: Exception) {
        Timber.d(exception)
        null
    }
