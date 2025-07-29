package com.rezazavareh7.ui.glide

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun ShowGlideImageByUrl(
    modifier: Modifier = Modifier,
    context: Context,
    imageUrlPath: String,
    thumbnailPath: String = imageUrlPath,
    contentScale: ContentScale = ContentScale.Crop,
    isClickable: Boolean = false,
    longClickOnItem: (String) -> Unit = {},
    clickOnItem: (String) -> Unit = {},
    placeHolder: Int = LocalJokerIconPalette.current.icSearch,
) {
    val baseUrl = "https://image.tmdb.org/t/p/original"
    val baseThumbnailUrl = "https://image.tmdb.org/t/p/w185"

    val requestOptions =
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .dontAnimate()

    val imageUrlRequest =
        getImageLoader(context).load("$baseUrl$imageUrlPath").placeholder(placeHolder)

    val thumbnailUrlRequest =
        getImageLoader(context).asDrawable().load("$baseThumbnailUrl$thumbnailPath")

    val requestManager = imageUrlRequest.thumbnail(thumbnailUrlRequest)

    GlideImage(
        model = imageUrlRequest,
        contentDescription = null,
        contentScale = contentScale,
        modifier =
            if (isClickable) {
                modifier.combinedClickable(
                    onClick = { clickOnItem(imageUrlPath) },
                    onLongClick = { longClickOnItem(imageUrlPath) },
                )
            } else {
                modifier
            },
        requestBuilderTransform = {
            requestManager.apply(requestOptions)
        },
    )
}
