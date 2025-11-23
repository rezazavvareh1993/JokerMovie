package com.rezazavareh7.designsystem.component.divider

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun VerticalDividerComponent(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
) {
    VerticalDivider(
        modifier = modifier,
        color = color,
        thickness = 1.dp,
    )
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun VerticalDividerComponentPreview() {
    JokerMovieTheme {
        VerticalDividerComponent()
    }
}
