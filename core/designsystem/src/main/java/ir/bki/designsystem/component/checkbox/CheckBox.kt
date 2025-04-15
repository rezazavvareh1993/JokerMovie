package ir.bki.designsystem.component.checkbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import ir.bki.designsystem.theme.PalizTheme

@Composable
fun CheckBoxComponent(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    isCheck: Boolean = false,
    title: String = "",
    onCheckedChange: (Boolean) -> Unit,
) {
    AnimatedVisibility(modifier = modifier, visible = isCheck) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                modifier = modifier,
                checked = isCheck,
                onCheckedChange = { onCheckedChange.invoke(it) },
                enabled = isEnable,
                colors =
                    CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colorScheme.onSurface,
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = MaterialTheme.colorScheme.surface,
                    ),
            )
//            TitleSmallTextComponent(text = title)//TODO replace text component
        }
    }
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun CheckBoxComponentPreview() {
    PalizTheme {
        CheckBoxComponent(title = "Title", onCheckedChange = {})
    }
}
