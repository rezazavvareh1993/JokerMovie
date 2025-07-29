package com.rezazavareh7.designsystem.custom

import androidx.compose.runtime.staticCompositionLocalOf
import com.rezazavareh7.designsystem.R

data class JokerMovieIconPalette(
    val icSearch: Int = R.drawable.ic_search,
    val icError: Int = R.drawable.ic_sample_search,
    val icBack: Int = R.drawable.ic_back,
    val imgJokerBackground: Int = R.drawable.img_background,
    val icMovie: Int = R.drawable.ic_movie,
    val icStar: Int = R.drawable.ic_star,
    val icBorderStar: Int = R.drawable.ic_border_star,
    val icMainLogo: Int = R.drawable.joker_logo,
    val icLogo: Int = R.drawable.joker_main_logo,
    val icCancel: Int = R.drawable.ic_cancel,
)

val LocalJokerIconPalette = staticCompositionLocalOf { JokerMovieIconPalette() }
