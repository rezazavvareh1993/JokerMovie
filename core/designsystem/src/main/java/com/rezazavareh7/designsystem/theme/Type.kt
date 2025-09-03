package com.rezazavareh7.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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

val replyTypography =
    Typography(
        headlineLarge =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 57.sp,
                fontFamily = AppFont.vazirFontFamily,
                lineHeight = 64.sp,
                letterSpacing = 0.25.sp,
            ),
        headlineMedium =
            TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 45.sp,
                fontFamily = AppFont.vazirFontFamily,
                lineHeight = 62.sp,
                letterSpacing = 0.sp,
            ),
        headlineSmall =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                fontFamily = AppFont.vazirFontFamily,
                lineHeight = 44.sp,
                letterSpacing = 0.sp,
            ),
        titleLarge =
            TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = AppFont.vazirFontFamily,
                lineHeight = 28.sp,
                letterSpacing = 0.25.sp,
            ),
        titleMedium =
            TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = AppFont.vazirFontFamily,
                letterSpacing = 0.sp,
            ),
        titleSmall =
            TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontFamily = AppFont.vazirFontFamily,
                letterSpacing = 0.sp,
            ),
        labelLarge =
            TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = AppFont.vazirFontFamily,
                letterSpacing = 0.sp,
            ),
        labelMedium =
            TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                lineHeight = 16.sp,
                fontFamily = AppFont.vazirFontFamily,
                letterSpacing = 0.sp,
            ),
        bodyLarge =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = AppFont.vazirFontFamily,
                letterSpacing = 0.sp,
            ),
        bodyMedium =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 30.sp,
                fontFamily = AppFont.vazirFontFamily,
                letterSpacing = 0.sp,
            ),
        bodySmall =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 20.sp,
                fontFamily = AppFont.vazirFontFamily,
                letterSpacing = 0.sp,
            ),
        displayLarge =
            TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 28.sp,
                fontFamily = AppFont.jokerFontFamily,
                lineHeight = 44.sp,
                letterSpacing = 0.sp,
            ),
    )
