package ir.bki.designsystem.component.textfield.outlinetextfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import ir.bki.designsystem.component.icon.IconComponent
import ir.bki.designsystem.custom.LocalPalizIconPalette
import ir.bki.designsystem.theme.PalizTheme

@Composable
fun OutlineTextFieldComponent(outlineTextFieldComponentParams: OutlineTextFieldComponentParams) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var focus by remember { mutableStateOf(false) }

    if (outlineTextFieldComponentParams.isEnabled && outlineTextFieldComponentParams.autoFocus) {
        LaunchedEffect(Unit) {
            keyboardController?.show()
            focusRequester.requestFocus()
            focus = true
        }
    } else {
        focus = false
    }

    OutlinedTextField(
        value = outlineTextFieldComponentParams.value,
        onValueChange = { newValue ->
            outlineTextFieldComponentParams.onValueChange(newValue)
        },
        enabled = outlineTextFieldComponentParams.isEnabled,
        modifier =
            outlineTextFieldComponentParams.modifier
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
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
        trailingIcon = trailingIconOfOutlineTextField(outlineTextFieldComponentParams = outlineTextFieldComponentParams),
        leadingIcon = leadingIconOfOutlineTextField(outlineTextFieldComponentParams),
        label = labelOfOutlineTextField(outlineTextFieldComponentParams),
        supportingText = supportingTextOfOutlineTextField(outlineTextFieldComponentParams),
        placeholder = placeHolderOfOutlineTextField(outlineTextFieldComponentParams),
        isError = outlineTextFieldComponentParams.isError,
        singleLine = true,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = outlineTextFieldComponentParams.keyboardType,
                imeAction = ImeAction.Done,
            ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
    )
}

private fun trailingIconOfOutlineTextField(outlineTextFieldComponentParams: OutlineTextFieldComponentParams): @Composable (() -> Unit)? =
    if (outlineTextFieldComponentParams.hasTrailingIcon) {
        {
            IconComponent(
                drawableId = outlineTextFieldComponentParams.trailingIcon,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    } else {
        null
    }

private fun leadingIconOfOutlineTextField(outlineTextFieldComponentParams: OutlineTextFieldComponentParams): @Composable (() -> Unit)? =
    if (outlineTextFieldComponentParams.hasLeadingIcon) {
        {
            IconComponent(
                drawableId =
                    if (outlineTextFieldComponentParams.isError) {
                        LocalPalizIconPalette.current.icPalizError
                    } else {
                        outlineTextFieldComponentParams.leadingIcon
                    },
                tint =
                    if (outlineTextFieldComponentParams.isError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
            )
        }
    } else {
        null
    }

private fun placeHolderOfOutlineTextField(outlineTextFieldComponentParams: OutlineTextFieldComponentParams): @Composable (() -> Unit)? =
    if (outlineTextFieldComponentParams.hasPlaceHolder) {
        {
            Text(
                text = outlineTextFieldComponentParams.placeHolder,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    } else {
        null
    }

private fun supportingTextOfOutlineTextField(outlineTextFieldComponentParams: OutlineTextFieldComponentParams): @Composable (() -> Unit)? =
    if (outlineTextFieldComponentParams.hasSupportText) {
        {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right,
                text = outlineTextFieldComponentParams.supportText,
                color = if (outlineTextFieldComponentParams.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            )
        }
    } else {
        null
    }

private fun labelOfOutlineTextField(outlineTextFieldComponentParams: OutlineTextFieldComponentParams): @Composable (() -> Unit)? =
    if (outlineTextFieldComponentParams.hasLabel) {
        { Text(text = outlineTextFieldComponentParams.label) }
    } else {
        null
    }

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun OutlineTextFieldComponentPreview() {
    PalizTheme {
        OutlineTextFieldComponent(
            OutlineTextFieldComponentParams(
                onValueChange = {},
            ),
        )
    }
}
