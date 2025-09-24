package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.VERSION
import javax.inject.Inject

class SaveVersionUseCase
    @Inject
    constructor(
        private val regularDataStoreManager: RegularDataStoreManager,
    ) {
        suspend operator fun invoke(version: String) {
            regularDataStoreManager.saveData(VERSION, version)
        }
    }
