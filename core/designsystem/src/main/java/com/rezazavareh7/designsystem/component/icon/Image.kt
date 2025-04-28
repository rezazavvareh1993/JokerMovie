package com.rezazavareh7.designsystem.component.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun ImageComponent(
    painterId: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    Image(
        painter = painterResource(painterId),
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = null,
    )
}

@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun ImageComponentPreview() {
    JokerMovieTheme {
        ImageComponent(
            painterId = LocalJokerIconPalette.current.icJokerSearch,
        )
    }
}
