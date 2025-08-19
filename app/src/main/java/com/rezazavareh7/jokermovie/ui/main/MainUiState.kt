package com.rezazavareh7.jokermovie.ui.main

import com.rezazavareh7.movies.ui.setting.LanguageSegmentButtonType
import com.rezazavareh7.movies.ui.setting.ThemeSegmentButtonType

data class MainUiState(
    val currentLanguage: LanguageSegmentButtonType? = null,
    val currentTheme: ThemeSegmentButtonType = ThemeSegmentButtonType.LIGHT,
)
