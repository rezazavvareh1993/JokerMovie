package com.rezazavareh7.designsystem.component.text

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
internal fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Visible,
    color: Color,
    isClickable: Boolean,
    onClickable: () -> Unit = {},
) {
    Text(
        text = text,
        color = color,
        style = style,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        modifier =
            if (isClickable) {
                modifier.clickable(onClick = { onClickable.invoke() })
            } else {
                modifier
            },
    )
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun TextComponentPreview() {
    JokerMovieTheme {
        TextComponent(
            text = "Text",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            isClickable = false,
        )
    }
}
