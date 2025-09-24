package com.rezazavareh7.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object Shape {
    val lowRoundCorner: Shape = RoundedCornerShape(4.dp)
    val mediumRoundCorner: Shape = RoundedCornerShape(8.dp)
    val highRoundCorner: Shape = RoundedCornerShape(16.dp)
    val highRoundCornerTop: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val mediumRoundCornerTop: Shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
}
