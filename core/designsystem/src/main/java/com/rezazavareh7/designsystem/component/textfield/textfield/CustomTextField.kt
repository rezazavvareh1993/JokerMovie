package com.rezazavareh7.designsystem.component.textfield.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun CustomTextFieldComponent(textFieldComponentParams: TextFieldComponentParams) {
    val focusManager = LocalFocusManager.current

    Column(modifier = textFieldComponentParams.modifier) {
        TextField(
            colors =
                TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
            modifier = Modifier.fillMaxWidth(),
            value = textFieldComponentParams.value,
            onValueChange = { newValue ->
                if (textFieldComponentParams.limitationCharacter != -1) {
                    if (newValue.length <= textFieldComponentParams.limitationCharacter) {
                        textFieldComponentParams.onValueChange(newValue)
                    }
                } else {
                    textFieldComponentParams.onValueChange(newValue)
                }
            },
            placeholder = placeHolderOfTextField(textFieldComponentParams),
            textStyle = MaterialTheme.typography.headlineSmall,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
            leadingIcon = leadingIconOfTextField(textFieldComponentParams),
            maxLines = textFieldComponentParams.maxLine,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
    }
}

private fun placeHolderOfTextField(textFieldComponentParams: TextFieldComponentParams): @Composable (() -> Unit)? =
    if (textFieldComponentParams.hasPlaceHolder) {
        {
            TitleMediumTextComponent(
                text = textFieldComponentParams.placeHolder,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    } else {
        null
    }

private fun leadingIconOfTextField(textFieldComponentParams: TextFieldComponentParams): @Composable (() -> Unit)? =
    if (textFieldComponentParams.hasLeadingIcon) {
        {
            IconComponent(
                isClickable = textFieldComponentParams.isLeadingIconClickable,
                onClick = textFieldComponentParams.clickOnLeadingIcon,
                drawableId =
                    if (textFieldComponentParams.isError) {
                        LocalJokerIconPalette.current.icInfo
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

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun CustomTextFieldComponentPreview() {
    JokerMovieTheme {
        CustomTextFieldComponent(
            TextFieldComponentParams(
                onValueChange = {},
            ),
        )
    }
}
