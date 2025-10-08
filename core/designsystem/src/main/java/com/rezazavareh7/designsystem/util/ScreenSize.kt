package com.rezazavareh7.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize

@Composable
fun getScreenDpSize(): DpSize {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    return remember(windowInfo.containerSize) {
        with(density) {
            DpSize(
                windowInfo.containerSize.width.toDp(),
                windowInfo.containerSize.height.toDp(),
            )
        }
    }
}

@Composable
fun getScreenSizeType(): ScreenSize {
    val screenWidth = getScreenDpSize().width.value
    return if (screenWidth >= 400f) {
        ScreenSize.LARGE
    } else if (screenWidth >= 360f) {
        ScreenSize.MEDIUM
    } else {
        ScreenSize.SMALL
    }
}

enum class ScreenSize {
    LARGE,
    MEDIUM,
    SMALL,
}
