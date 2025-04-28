package com.rezazavareh7.common.doamin

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveData(
        key: String,
        value: String,
    )

    suspend fun saveData(
        key: String,
        value: Boolean,
    )

    fun getString(key: String): Flow<String>

    fun getBoolean(key: String): Flow<Boolean>

    suspend fun <T : Any> getObject(
        key: String,
        mapper: (String) -> Result<T>,
    ): Flow<Result<T>>

    suspend fun <T : Any> saveObject(
        key: String,
        value: T,
        toJson: (T) -> String,
    )

    suspend fun clearAllData()
}
