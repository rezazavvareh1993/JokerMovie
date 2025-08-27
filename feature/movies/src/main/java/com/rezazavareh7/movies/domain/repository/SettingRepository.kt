package com.rezazavareh7.movies.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun saveLanguage(language: String)

    fun getLanguage(): Flow<String>
}
