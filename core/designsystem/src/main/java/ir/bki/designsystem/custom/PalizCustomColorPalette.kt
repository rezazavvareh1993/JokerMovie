package ir.bki.designsystem.custom

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class PalizCustomColorPalette(
    val sampleColor: Color = Color.Unspecified,
)

val LocalPalizCustomColorPalette = staticCompositionLocalOf { PalizCustomColorPalette() }
