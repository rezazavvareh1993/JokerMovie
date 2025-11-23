package com.rezazavareh7.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun OutlinedButtonComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        border =
            BorderStroke(
                width = ButtonComponentDefaults.OutlinedButtonBorderWidth,
                color =
                    if (enabled) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = ButtonComponentDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
                        )
                    },
            ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun OutlinedButtonComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hasLeadingIcon: Boolean = false,
    text: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
) {
    OutlinedButtonComponent(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
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
fun OutlinedButtonComponentPreview() {
    JokerMovieTheme {
        OutlinedButtonComponent(
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
