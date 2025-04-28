package com.rezazavareh7.common.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

const val DEFAULT_APP_LOCALE = "fa"

object LocaleManager {
    fun setLocale(
        baseContext: Context?,
        language: String = DEFAULT_APP_LOCALE,
    ): Context? {
        val locale = Locale(language)
        val configuration = Configuration(baseContext?.resources?.configuration)
        configuration.setLocale(locale)
        return baseContext?.createConfigurationContext(configuration) ?: baseContext
    }
}
