package com.rezazavareh7.designsystem.custom

import androidx.compose.runtime.staticCompositionLocalOf
import com.rezazavareh7.designsystem.R

data class JokerMovieIconPalette(
    val icJokerSearch: Int = R.drawable.ic_search,
    val icJokerError: Int = R.drawable.ic_sample_search,
    val icJokerBack: Int = R.drawable.ic_sample_search,
    val imgJokerBackground: Int = R.drawable.img_background,
    val icMovie: Int = R.drawable.ic_movie,
    val icStar: Int = R.drawable.ic_star,
    val icBorderStar: Int = R.drawable.ic_border_star,
)

val LocalJokerIconPalette = staticCompositionLocalOf { JokerMovieIconPalette() }
