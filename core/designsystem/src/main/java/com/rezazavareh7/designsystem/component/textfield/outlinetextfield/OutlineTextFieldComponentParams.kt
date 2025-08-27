package com.rezazavareh7.designsystem.component.textfield.outlinetextfield

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import com.rezazavareh7.designsystem.R

data class OutlineTextFieldComponentParams(
    val modifier: Modifier = Modifier,
    val isEnabled: Boolean = true,
    val shape: Shape = com.rezazavareh7.designsystem.theme.Shape.highRoundCorner,
    val isError: Boolean = false,
    val keyboardType: KeyboardType = KeyboardType.Number,
    val hasLabel: Boolean = false,
    val label: String = "",
    val hasPlaceHolder: Boolean = false,
    val placeHolder: String = "",
    val hasSupportText: Boolean = false,
    val supportText: String = "",
    val hasTrailingIcon: Boolean = false,
    val trailingIcon: Int = R.drawable.ic_search,
    val hasLeadingIcon: Boolean = false,
    val leadingIcon: Int = R.drawable.ic_search,
    val onValueChange: (String) -> Unit,
    val value: String = "",
    val autoFocus: Boolean = true,
    val limitationCharacter: Int = 40,
    val isLeadingIconClickable: Boolean = true,
    val isTrailingIconClickable: Boolean = true,
    val clickOnLeadingIcon: () -> Unit = {},
    val clickOnTrailingIcon: () -> Unit = {},
)
