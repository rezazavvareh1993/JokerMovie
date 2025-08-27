package com.rezazavareh7.designsystem.component.text.label

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
fun LabelLargeTextComponent(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overFlow: TextOverflow = TextOverflow.Visible,
    textAlign: TextAlign = TextAlign.Start,
    isClickable: Boolean = false,
    onClickable: () -> Unit = {},
) {
    TextComponent(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = overFlow,
        textAlign = textAlign,
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier,
        isClickable = isClickable,
        onClickable = onClickable,
    )
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun LabelLargeTextComponentPreview() {
    JokerMovieTheme {
        LabelLargeTextComponent(text = "Preview")
    }
}
