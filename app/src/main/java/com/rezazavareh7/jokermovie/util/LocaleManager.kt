package com.rezazavareh7.jokermovie.util

import android.content.Context
import android.content.res.Configuration
import com.rezazavareh.usecase.GetLanguageUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

class LocaleManager
    @Inject
    constructor(
        private val getLanguageUseCase: GetLanguageUseCase,
    ) {
        fun setLocale(
            baseContext: Context?,
            resetApp: () -> Unit,
        ) {
            var language = ""
            runBlocking {
                val currentLanguageLocale = getLanguageUseCase().first()
                language = currentLanguageLocale.split("-").first()
            }
            if (language.isNotEmpty()) {
                val locale = Locale(language)
                val resources = baseContext?.resources
                val configuration = Configuration(resources?.configuration)
                configuration.setLocale(locale)
                configuration.setLayoutDirection(locale)
                resources?.updateConfiguration(configuration, resources.displayMetrics)
//                val context = baseContext?.createConfigurationContext(configuration) ?: baseContext
                resetApp()
            }
        }
    }
