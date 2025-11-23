package com.rezazavareh7.movies.ui.navgraph

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

inline fun <reified T> customNavType(isNullableAllowed: Boolean = false): NavType<T> =
    object : NavType<T>(isNullableAllowed) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): T? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun serializeAsValue(value: T): String = Uri.encode(Json.encodeToString(value))

        override fun parseValue(value: String): T = Json.decodeFromString(Uri.decode(value))

        override fun put(
            bundle: Bundle,
            key: String,
            value: T,
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
