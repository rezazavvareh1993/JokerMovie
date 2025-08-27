package com.rezazavareh.usecase

import com.rezazavareh.prefrences.LANGUAGE_KEY
import com.rezazavareh.prefrences.RegularDataStoreManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveLanguageUseCase
    @Inject
    constructor(
        private val dataStore: RegularDataStoreManager,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(language: String) {
            withContext(dispatcher) {
                dataStore.saveData(key = LANGUAGE_KEY, value = language)
            }
        }
    }
