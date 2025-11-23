package com.rezazavareh7.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicString(
        val value: String,
    ) : UiText

    class StringResourceId(
        val id: Int,
        val args: Array<Any> = arrayOf(),
    ) : UiText

    @Composable
    fun asString(): String =
        when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(id = id, formatArgs = args)
        }
}
