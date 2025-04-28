package com.rezazavareh7.jokermovie.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rezazavareh7.designsystem.component.navigation.SystemBarVisibilityManager

@Composable
fun SetStatusBarColor(systemBarVisibilityManager: SystemBarVisibilityManager) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = systemBarVisibilityManager.isLightBar.value

    systemUiController.setSystemBarsColor(
        color = getBarColor(systemBarVisibilityManager),
        darkIcons = useDarkIcons,
    )
}

@Composable
private fun getBarColor(systemBarVisibilityManager: SystemBarVisibilityManager): Color =
    if (!systemBarVisibilityManager.isLightBar.value) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.surface
    }
