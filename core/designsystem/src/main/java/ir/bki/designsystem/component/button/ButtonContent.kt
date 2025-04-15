package ir.bki.designsystem.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import ir.bki.designsystem.theme.PalizTheme

@Composable
internal fun ButtonContentComponent(
    content: @Composable () -> Unit,
    hasLeadingIcon: Boolean = false,
    leadingIcon: @Composable (() -> Unit) = {},
) {
    if (hasLeadingIcon) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start =
                    if (hasLeadingIcon) {
                        ButtonDefaults.IconSpacing
                    } else {
                        0.dp
                    },
            ),
    ) {
        content()
    }
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun ButtonContentComponentPreview() {
    PalizTheme {
        ButtonContentComponent(
            content = {
                Text(text = "Button")
            },
        )
    }
}
