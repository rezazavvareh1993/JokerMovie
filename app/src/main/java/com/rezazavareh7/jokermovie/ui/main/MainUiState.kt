package com.rezazavareh7.jokermovie.ui.main

import com.rezazavareh7.movies.ui.setting.ThemeSegmentButtonType

data class MainUiState(
    val currentLanguage: String = "",
    val currentTheme: ThemeSegmentButtonType = ThemeSegmentButtonType.LIGHT,
)
