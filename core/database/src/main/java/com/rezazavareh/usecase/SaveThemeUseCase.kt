package com.rezazavareh.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.THEME_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveThemeUseCase
    @Inject
    constructor(
        private val dataStore: RegularDataStoreManager,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(theme: String) {
            withContext(dispatcher) {
                dataStore.saveData(key = THEME_KEY, value = theme)
            }
        }
    }
