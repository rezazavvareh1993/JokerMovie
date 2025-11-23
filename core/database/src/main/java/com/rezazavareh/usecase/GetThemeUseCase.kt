package com.rezazavareh.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.THEME_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val DEFAULT_THEME = "LIGHT"

class GetThemeUseCase
    @Inject
    constructor(
        private val dataStore: RegularDataStoreManager,
        private val dispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(): Flow<String> =
            withContext(dispatcher) {
                dataStore.getString(key = THEME_KEY, defaultValue = DEFAULT_THEME)
            }
    }
