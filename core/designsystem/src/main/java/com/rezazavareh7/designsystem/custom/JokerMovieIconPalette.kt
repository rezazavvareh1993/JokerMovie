package com.rezazavareh7.designsystem.custom

import androidx.compose.runtime.staticCompositionLocalOf
import com.rezazavareh7.designsystem.R

data class JokerMovieIconPalette(
    val icSearch: Int = R.drawable.ic_search,
    val icBack: Int = R.drawable.ic_back,
    val icMovie: Int = R.drawable.ic_movie,
    val icStar: Int = R.drawable.ic_star,
    val icEmptyStar: Int = R.drawable.ic_empty_star,
    val icMainLogo: Int = R.drawable.joker_logo,
    val icLogo: Int = R.drawable.joker_main_logo,
    val icCancel: Int = R.drawable.ic_cancel,
    val icLike: Int = R.drawable.ic_like,
    val icDislike: Int = R.drawable.ic_dislike,
    val icEmpty: Int = R.drawable.ic_empty,
    val icInfo: Int = R.drawable.ic_info,
    val icMoon: Int = R.drawable.ic_moon,
    val icSun: Int = R.drawable.ic_sun,
    val icSetting: Int = R.drawable.ic_setting,
    val icTV: Int = R.drawable.ic_tv,
    val icDelete: Int = R.drawable.ic_delete,
    val icDownload: Int = R.drawable.ic_download,
    val icShare: Int = R.drawable.ic_share,
    val icImages: Int = R.drawable.ic_images,
    val icFavorite: Int = R.drawable.ic_favorite,
)

val LocalJokerIconPalette = staticCompositionLocalOf { JokerMovieIconPalette() }
