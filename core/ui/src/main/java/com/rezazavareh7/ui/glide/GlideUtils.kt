package com.rezazavareh7.ui.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.rezazavareh7.ui.util.Constants.IMAGE_BASE_URL
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InterruptedIOException
import java.util.concurrent.ExecutionException

object GlideUtils {
    @Throws(CancellationException::class, ExecutionException::class, InterruptedIOException::class)
    suspend fun getCachedImageFile(
        context: Context,
        imageUrlPath: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): File =
        withContext(dispatcher) {
            val requestOptions =
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .onlyRetrieveFromCache(true)
                    .dontAnimate()

            val requestManager = Glide.with(context)

            requestManager
                .downloadOnly()
                .load("$IMAGE_BASE_URL$imageUrlPath")
                .apply(requestOptions)
                .submit()
                .get()
        }
}
