package com.rezazavareh7.designsystem.theme.type

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.rezazavareh7.designsystem.R

object AppFont {
    val vazirFontFamily =
        FontFamily(
            Font(R.font.vazirmatn_regular),
            Font(R.font.vazirmatn_regular, style = FontStyle.Italic),
            Font(R.font.vazirmatn_medium, FontWeight.Medium),
            Font(R.font.vazirmatn_medium, FontWeight.Medium, style = FontStyle.Italic),
            Font(R.font.vazirmatn_bold, FontWeight.Bold),
            Font(R.font.vazirmatn_bold, FontWeight.Bold, style = FontStyle.Italic),
        )

    val jokerFontFamily =
        FontFamily(
            Font(R.font.billion_stars_personal_use),
            Font(R.font.billion_stars_personal_use, FontWeight.Bold),
        )
}
