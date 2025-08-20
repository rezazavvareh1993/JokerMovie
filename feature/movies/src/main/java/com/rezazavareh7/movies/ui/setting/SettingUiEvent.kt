package com.rezazavareh7.movies.ui.setting

sealed class SettingUiEvent {
    data class OnLanguageSegmentButtonClicked(val index: Int, val locale: String) : SettingUiEvent()

    data class OnThemeSegmentButtonClicked(val index: Int, val type: String) : SettingUiEvent()
}
