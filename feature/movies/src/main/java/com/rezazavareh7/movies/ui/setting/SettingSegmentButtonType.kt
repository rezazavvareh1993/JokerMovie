package com.rezazavareh7.movies.ui.setting

enum class ThemeSegmentButtonType {
    LIGHT,
    DARK,
}

enum class LanguageSegmentButtonType(val locale: String) {
    ENGLISH("en-Us"),
    FARSI("fa-IR"),
    ;

    companion object {
        fun getType(type: String): LanguageSegmentButtonType =
            when (type) {
                "ENGLISH" -> ENGLISH
                else -> FARSI
            }
    }
}
