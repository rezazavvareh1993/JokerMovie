package com.rezazavareh7.common.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rezazavareh7.common.doamin.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val DATA_STORE_PREFERENCES_KEY = "jokermovie_app_datastore_preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_PREFERENCES_KEY)

internal class DataStoreManagerImpl
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) : DataStoreManager {
        override suspend fun saveData(
            key: String,
            value: String,
        ) {
            context.dataStore.edit { prefs ->
                prefs[stringPreferencesKey(key)] = value
            }
        }

        override suspend fun saveData(
            key: String,
            value: Boolean,
        ) {
            context.dataStore.edit { prefs ->
                prefs[booleanPreferencesKey(key)] = value
            }
        }

        override fun getString(key: String): Flow<String> =
            context.dataStore.data.map { prefs ->
                prefs[stringPreferencesKey(key)] ?: ""
            }

        override fun getBoolean(key: String): Flow<Boolean> =
            context.dataStore.data.map { prefs ->
                prefs[booleanPreferencesKey(key)] ?: false
            }

        override suspend fun <T : Any> getObject(
            key: String,
            mapper: (String) -> Result<T>,
        ): Flow<Result<T>> =
            getString(key).map { jsonString ->
                mapper(jsonString)
            }

        override suspend fun <T : Any> saveObject(
            key: String,
            value: T,
            toJson: (T) -> String,
        ) {
            val jsonString = toJson(value)
            saveData(key, jsonString)
        }

        override suspend fun clearAllData() {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
        }
    }
