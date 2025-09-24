package com.rezazavareh.prefrences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val REGULAR_USER_PREFS_KEY = "app_prefs.preferences_pb"
const val LANGUAGE_KEY = "languageKey"
const val THEME_KEY = "themeKey"
const val SEARCH_MOVIE_HISTORY = "searchMovieHistory"
const val SEARCH_SERIES_HISTORY = "searchSeriesHistory"
const val VERSION = "version"

val Context.dataStore by preferencesDataStore(name = REGULAR_USER_PREFS_KEY)

internal class RegularDataStoreManagerImpl
    @Inject
    constructor(
        @param:ApplicationContext val context: Context,
    ) : RegularDataStoreManager {
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
            defaultValue: Boolean,
        ) {
            context.dataStore.edit { prefs ->
                prefs[booleanPreferencesKey(key)] = value
            }
        }

        override fun getString(
            key: String,
            defaultValue: String,
        ): Flow<String> =
            context.dataStore.data.map { prefs ->
                prefs[stringPreferencesKey(key)] ?: defaultValue
            }

        override fun getBoolean(
            key: String,
            defaultValue: Boolean,
        ): Flow<Boolean> =
            context.dataStore.data.map { prefs ->
                prefs[booleanPreferencesKey(key)] ?: defaultValue
            }

        override suspend fun clearAllData() {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
        }
    }
