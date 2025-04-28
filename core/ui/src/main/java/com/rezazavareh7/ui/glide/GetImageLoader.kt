package com.rezazavareh7.ui.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

fun getImageLoader(context: Context): RequestManager {
    return Glide.with(context)
}
