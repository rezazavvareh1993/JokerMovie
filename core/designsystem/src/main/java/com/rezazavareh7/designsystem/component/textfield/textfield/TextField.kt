package com.rezazavareh7.designsystem.component.textfield.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.JokerMovieTheme
import kotlinx.coroutines.delay

@Composable
fun TextFieldComponent(textFieldComponentParams: TextFieldComponentParams) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var focus by remember { mutableStateOf(false) }

    var textFieldValue by remember {
        mutableStateOf("")
    }

    if (textFieldComponentParams.isEnabled) {
        LaunchedEffect("") {
            delay(300)
            keyboardController?.show()
            focusRequester.requestFocus()
            focus = true
        }
    } else {
        focus = false
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            enabled = textFieldComponentParams.isEnabled,
            modifier =
                textFieldComponentParams.modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (focus != it.isFocused) {
                            focus = it.isFocused
                            if (!it.isFocused) {
                                keyboardController?.hide()
                            }
                        }
                    },
            colors =
                TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                textFieldComponentParams.onValueChange.invoke(textFieldValue)
            },
            trailingIcon = trailingIconOfTextField(textFieldComponentParams),
            leadingIcon = leadingIconOfTextField(textFieldComponentParams),
            label = labelOfTextField(textFieldComponentParams),
            supportingText = supportingTextOfTextField(textFieldComponentParams),
            placeholder = placeHolderOfTextField(textFieldComponentParams),
            isError = textFieldComponentParams.isError,
            singleLine = true,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = textFieldComponentParams.keyboardType,
                    imeAction = ImeAction.Done,
                ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
    }
}

private fun trailingIconOfTextField(textFieldComponentParams: TextFieldComponentParams): @Composable (() -> Unit)? =
    if (textFieldComponentParams.hasTrailingIcon) {
        {
            IconComponent(
                drawableId = textFieldComponentParams.trailingIcon,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    } else {
        null
    }

private fun leadingIconOfTextField(textFieldComponentParams: TextFieldComponentParams): @Composable (() -> Unit)? =
    if (textFieldComponentParams.hasLeadingIcon) {
        {
            IconComponent(
                drawableId =
                    if (textFieldComponentParams.isError) {
                        LocalJokerIconPalette.current.icJokerError
                    } else {
                        textFieldComponentParams.leadingIcon
                    },
                tint =
                    if (textFieldComponentParams.isError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
            )
        }
    } else {
        null
    }

private fun placeHolderOfTextField(textFieldComponentParams: TextFieldComponentParams): @Composable (() -> Unit)? =
    if (textFieldComponentParams.hasPlaceHolder) {
        {
            Text(
                text = textFieldComponentParams.placeHolder,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    } else {
        null
    }

private fun supportingTextOfTextField(textFieldComponentParams: TextFieldComponentParams): @Composable (() -> Unit)? =
    if (textFieldComponentParams.hasSupportText) {
        { Text(text = textFieldComponentParams.supportText) }
    } else {
        null
    }

private fun labelOfTextField(textFieldComponentParams: TextFieldComponentParams): @Composable (() -> Unit)? =
    if (textFieldComponentParams.hasLabel) {
        { Text(text = textFieldComponentParams.label) }
    } else {
        null
    }

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun TextFieldComponentPreview() {
    JokerMovieTheme {
        TextFieldComponent(
            TextFieldComponentParams(
                onValueChange = {},
            ),
        )
    }
}
