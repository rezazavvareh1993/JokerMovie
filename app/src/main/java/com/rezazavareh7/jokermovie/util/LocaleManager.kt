package com.rezazavareh7.jokermovie.util

import android.content.Context
import android.content.res.Configuration
import com.rezazavareh.usecase.GetLanguageUseCase
import com.rezazavareh7.movies.ui.setting.LanguageSegmentButtonType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

class LocaleManager
    @Inject
    constructor(
        private val getLanguageUseCase: GetLanguageUseCase,
    ) {
        fun setLocale(baseContext: Context?): Context? {
            var language = ""
            runBlocking {
                val type = getLanguageUseCase().first()
                language = LanguageSegmentButtonType.getType(type).locale.split("-").first()
            }
            return if (language.isNotEmpty()) {
                val locale = Locale(language)
                val resources = baseContext?.resources
                val configuration = Configuration(resources?.configuration)
                configuration.setLocale(locale)
                configuration.setLayoutDirection(locale)
                resources?.updateConfiguration(configuration, resources.displayMetrics)

                return baseContext?.createConfigurationContext(configuration) ?: baseContext
            } else {
                baseContext
            }
        }
    }
