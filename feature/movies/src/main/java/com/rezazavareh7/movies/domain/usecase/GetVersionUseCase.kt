package com.rezazavareh7.movies.domain.usecase

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.VERSION
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetVersionUseCase
    @Inject
    constructor(
        private val regularDataStoreManager: RegularDataStoreManager,
    ) {
        suspend operator fun invoke(): String = regularDataStoreManager.getString(VERSION).first()
    }
