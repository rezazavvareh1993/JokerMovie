package com.rezazavareh.usecase

import com.rezazavareh.prefrences.LANGUAGE_KEY
import com.rezazavareh.prefrences.RegularDataStoreManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val EN_LANGUAGE = "en-US"

class GetLanguageUseCase
    @Inject
    constructor(
        private val dataStore: RegularDataStoreManager,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(): Flow<String> =
            withContext(dispatcher) {
                dataStore.getString(key = LANGUAGE_KEY, defaultValue = EN_LANGUAGE)
            }
    }
