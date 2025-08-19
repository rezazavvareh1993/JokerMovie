package com.rezazavareh.prefrences

import kotlinx.coroutines.flow.Flow

interface RegularDataStoreManager {
    suspend fun saveData(
        key: String,
        value: String,
    )

    suspend fun saveData(
        key: String,
        value: Boolean,
        defaultValue: Boolean = false,
    )

    fun getString(
        key: String,
        defaultValue: String = "",
    ): Flow<String>

    fun getBoolean(
        key: String,
        defaultValue: Boolean = false,
    ): Flow<Boolean>

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
