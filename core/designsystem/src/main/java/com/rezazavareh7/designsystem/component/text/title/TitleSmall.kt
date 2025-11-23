package com.rezazavareh7.designsystem.component.text.title

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
fun TitleSmallTextComponent(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Visible,
    textAlign: TextAlign = TextAlign.Start,
    isClickable: Boolean = false,
    onClickable: () -> Unit = {},
) {
    TextComponent(
        text = text,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier,
        overflow = overflow,
        isClickable = isClickable,
        onClickable = onClickable,
    )
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun TitleSmallTextComponentPreview() {
    JokerMovieTheme {
        TitleSmallTextComponent(text = "TitleSmallTextComponent")
    }
}
