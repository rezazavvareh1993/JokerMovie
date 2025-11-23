package com.rezazavareh7.designsystem.component.button

import androidx.compose.ui.unit.dp

internal object ButtonComponentDefaults {
    // OutlinedButton border color doesn't respect disabled state by default
    const val DISABLED_OUTLINED_BUTTON_BORDER_ALPHA = 0.12f

    // OutlinedButton default border width isn't exposed via ButtonDefaults
    val OutlinedButtonBorderWidth = 1.dp
}
