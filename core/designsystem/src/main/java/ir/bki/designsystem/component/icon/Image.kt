package ir.bki.designsystem.component.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import ir.bki.designsystem.custom.LocalPalizIconPalette
import ir.bki.designsystem.theme.PalizTheme

@Composable
fun ImageComponent(
    painterId: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(painterId),
        modifier = modifier,
        contentDescription = null,
    )
}

@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun ImageComponentPreview() {
    PalizTheme {
        ImageComponent(
            painterId = LocalPalizIconPalette.current.icPalizSampleIcon,
        )
    }
}
