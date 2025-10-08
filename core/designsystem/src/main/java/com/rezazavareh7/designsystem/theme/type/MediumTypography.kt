package com.rezazavareh7.designsystem.theme.type

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object MediumTypography {
    val mediumTypography =
        Typography(
            headlineLarge =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 55.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    lineHeight = 62.sp,
                    letterSpacing = 0.25.sp,
                ),
            headlineMedium =
                TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 43.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    lineHeight = 60.sp,
                    letterSpacing = 0.sp,
                ),
            headlineSmall =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 26.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    lineHeight = 42.sp,
                    letterSpacing = 0.sp,
                ),
            titleLarge =
                TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    lineHeight = 26.sp,
                    letterSpacing = 0.25.sp,
                ),
            titleMedium =
                TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    letterSpacing = 0.sp,
                ),
            titleSmall =
                TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    letterSpacing = 0.sp,
                ),
            labelLarge =
                TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 15.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    letterSpacing = 0.sp,
                ),
            labelMedium =
                TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    letterSpacing = 0.sp,
                ),
            bodyLarge =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    letterSpacing = 0.sp,
                ),
            bodyMedium =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    letterSpacing = 0.sp,
                ),
            bodySmall =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = AppFont.vazirFontFamily,
                    letterSpacing = 0.sp,
                ),
            displayLarge =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 26.sp,
                    fontFamily = AppFont.jokerFontFamily,
                    lineHeight = 42.sp,
                    letterSpacing = 0.sp,
                ),
        )
}
