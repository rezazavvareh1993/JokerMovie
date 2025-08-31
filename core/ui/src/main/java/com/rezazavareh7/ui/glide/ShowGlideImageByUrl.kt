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
import com.rezazavareh7.ui.util.Constants.IMAGE_BASE_URL
import com.rezazavareh7.ui.util.Constants.IMAGE_THUMBNAIL_BASE_URL
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun ShowGlideImageByUrl(
    modifier: Modifier = Modifier,
    context: Context,
    imageUrlPath: String,
    thumbnailPath: String = imageUrlPath,
    contentScale: ContentScale = ContentScale.Crop,
    isClickable: Boolean = false,
    isZoomable: Boolean = false,
    longClickOnItem: (String) -> Unit = {},
    clickOnItem: (String) -> Unit = {},
    placeHolder: Int = LocalJokerIconPalette.current.icMovie,
) {
    val requestOptions =
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .dontAnimate()

    val imageUrlRequest =
        getImageLoader(context).load("$IMAGE_BASE_URL$imageUrlPath").placeholder(placeHolder)

    val thumbnailUrlRequest =
        getImageLoader(context).asDrawable().load("$IMAGE_THUMBNAIL_BASE_URL$thumbnailPath")

    val requestManager = imageUrlRequest.thumbnail(thumbnailUrlRequest)

    GlideImage(
        model = imageUrlRequest,
        contentDescription = null,
        contentScale = contentScale,
        modifier =
            modifier.zoomableClickableModifier(
                isZoomable = isZoomable,
                isClickable = isClickable,
                imageUrl = imageUrlPath,
                clickOnItem = clickOnItem,
                longClickOnItem = longClickOnItem,
            ),
        requestBuilderTransform = {
            requestManager.apply(requestOptions)
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Modifier.zoomableClickableModifier(
    isZoomable: Boolean,
    isClickable: Boolean,
    imageUrl: String,
    clickOnItem: (String) -> Unit,
    longClickOnItem: (String) -> Unit,
): Modifier {
    return when {
        isZoomable ->
            this.then(
                Modifier.zoomable(
                    state = rememberZoomableState(),
                    onClick = {
                        if (isClickable) clickOnItem(imageUrl)
                    },
                    onLongClick = {
                        if (isClickable) longClickOnItem(imageUrl)
                    },
                ),
            )

        isClickable ->
            this.then(
                Modifier.combinedClickable(
                    onClick = { clickOnItem(imageUrl) },
                    onLongClick = { longClickOnItem(imageUrl) },
                ),
            )

        else -> this
    }
}
