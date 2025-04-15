package ir.bki.designsystem.component.button

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import ir.bki.designsystem.component.icon.IconComponent
import ir.bki.designsystem.custom.LocalPalizIconPalette
import ir.bki.designsystem.theme.PalizTheme

@Composable
fun FloatActionButtonComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    icon: Int,
) {
    FloatingActionButton(
        modifier = modifier.padding(20.dp),
        onClick = { onClick() },
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        IconComponent(drawableId = icon, tint = contentColor)
    }
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun FloatActionButtonComponentPreview() {
    PalizTheme {
        FloatActionButtonComponent(
            onClick = { },
            icon = LocalPalizIconPalette.current.icPalizSampleIcon,
        )
    }
}
