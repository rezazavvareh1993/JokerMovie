package com.rezazavareh7.movies.data.repositoryimpl

import com.rezazavareh7.movies.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl
    @Inject
    constructor() : SettingRepository {
        override suspend fun saveLanguage(language: String) {
            TODO("Not yet implemented")
        }

        override fun getLanguage(): Flow<String> {
            TODO("Not yet implemented")
        }
    }
