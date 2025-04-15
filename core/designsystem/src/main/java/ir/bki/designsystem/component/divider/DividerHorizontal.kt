package ir.bki.designsystem.component.divider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import ir.bki.designsystem.theme.PalizTheme

@Composable
fun HorizontalDividerComponent(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
) {
    HorizontalDivider(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        color = color,
        thickness = 1.dp,
    )
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun HorizontalDividerComponentPreview() {
    PalizTheme {
        HorizontalDividerComponent()
    }
}
