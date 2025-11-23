package com.rezazavareh7.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun FilledButtonComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = containerColor,
            ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun FilledButtonComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hasLeadingIcon: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    text: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
) {
    FilledButtonComponent(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        containerColor = containerColor,
        contentPadding =
            if (hasLeadingIcon) {
                ButtonDefaults.ButtonWithIconContentPadding
            } else {
                ButtonDefaults.ContentPadding
            },
    ) {
        ButtonContentComponent(
            content = text,
            hasLeadingIcon = hasLeadingIcon,
            leadingIcon = leadingIcon,
        )
    }
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun FilledButtonComponentPreview() {
    JokerMovieTheme {
        FilledButtonComponent(
            onClick = { },
            enabled = true,
        ) {
            ButtonContentComponent(
                content = {
                    Text(
                        text = "Button",
                    )
                },
                hasLeadingIcon = false,
            )
        }
    }
}
