package com.rezazavareh7.designsystem.component.text.body

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.component.text.TextComponent
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun BodyLargeTextComponent(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    overflow: TextOverflow = TextOverflow.Visible,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    isClickable: Boolean = false,
    onClickable: () -> Unit = {},
) {
    TextComponent(
        text = text,
        modifier = modifier,
        color = color,
        overflow = overflow,
        maxLines = maxLines,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = textAlign,
        isClickable = isClickable,
        onClickable = onClickable,
    )
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun BodyLargeTextComponentPreview() {
    JokerMovieTheme {
        BodyLargeTextComponent(text = "Preview")
    }
}
