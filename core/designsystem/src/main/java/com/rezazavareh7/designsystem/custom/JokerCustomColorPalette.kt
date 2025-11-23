package com.rezazavareh7.designsystem.custom

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class JokerCustomColorPalette(
    val yellow: Color = Color.Unspecified,
)

val LocalJokerColorPalette = staticCompositionLocalOf { JokerCustomColorPalette() }
