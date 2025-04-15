package ir.bki.designsystem.custom

import androidx.compose.runtime.staticCompositionLocalOf
import ir.bki.designsystem.R

data class PalizIconPalette(
    val icPalizSampleIcon: Int = R.drawable.ic_sample_search,
    val icPalizError: Int = R.drawable.ic_paliz_error_light,
    val icPalizBack: Int = R.drawable.ic_sample_search,
)

val LocalPalizIconPalette = staticCompositionLocalOf { PalizIconPalette() }