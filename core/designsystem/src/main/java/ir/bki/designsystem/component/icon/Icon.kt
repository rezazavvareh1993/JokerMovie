package ir.bki.designsystem.component.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.bki.designsystem.custom.LocalPalizIconPalette
import ir.bki.designsystem.theme.PalizTheme

@Composable
fun IconComponent(
    modifier: Modifier = Modifier,
    drawableId: Int,
    boxSize: Dp = 24.dp,
    iconSize: Dp = 24.dp,
    tint: Color = Color.Unspecified,
    isClickable: Boolean = false,
    onClick: (() -> Unit) = {},
) {
    Box(
        modifier =
            if (isClickable) {
                modifier
                    .size(boxSize)
                    .clickable(onClick = { onClick.invoke() })
            } else {
                modifier
                    .size(boxSize)
            },
        contentAlignment = Alignment.Center,
        content = {
            Icon(
                painter = painterResource(id = drawableId),
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(iconSize),
            )
        },
    )
}

@Composable
fun IconComponent(
    modifier: Modifier = Modifier,
    drawableId: Int,
    tint: Color = Color.Unspecified,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            Icon(
                painter = painterResource(id = drawableId),
                contentDescription = null,
                tint = tint,
            )
        },
    )
}

@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun IconComponentPreview() {
    PalizTheme {
        IconComponent(
            drawableId = LocalPalizIconPalette.current.icPalizSampleIcon,
            tint = Color.Unspecified,
        )
    }
}
