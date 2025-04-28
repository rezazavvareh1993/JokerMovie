package com.rezazavareh7.designsystem.component.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class SystemBarVisibilityManager {
    private val _isBottomBarVisible = mutableStateOf(false)
    val isBottomBarVisible: State<Boolean> = _isBottomBarVisible

    private val _isLightBar = mutableStateOf(true)
    val isLightBar: State<Boolean> = _isLightBar

    fun showBottomBar() {
        _isBottomBarVisible.value = true
    }

    fun hideBottomBar() {
        _isBottomBarVisible.value = false
    }

    fun setDarkBar() {
        _isLightBar.value = false
    }

    fun setLightBar() {
        _isLightBar.value = true
    }
}
