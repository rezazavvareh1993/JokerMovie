package ir.bki.designsystem.component.textfield.textfield

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import ir.bki.designsystem.R

data class TextFieldComponentParams(
    val modifier: Modifier = Modifier,
    val isEnabled: Boolean = true,
    val isError: Boolean = false,
    val keyboardType: KeyboardType = KeyboardType.Number,
    val hasLabel: Boolean = false,
    val label: String = "",
    val hasPlaceHolder: Boolean = false,
    val placeHolder: String = "",
    val hasSupportText: Boolean = false,
    val supportText: String = "",
    val hasTrailingIcon: Boolean = false,
    val trailingIcon: Int = R.drawable.ic_sample_search,
    val hasLeadingIcon: Boolean = false,
    val leadingIcon: Int = R.drawable.ic_sample_search,
    val onValueChange: (String) -> Unit,
    val value: String = "",
    val limitationCharacter: Int = -1,
)
