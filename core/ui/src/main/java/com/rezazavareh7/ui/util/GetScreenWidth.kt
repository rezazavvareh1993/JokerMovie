package com.rezazavareh7.ui.util

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
