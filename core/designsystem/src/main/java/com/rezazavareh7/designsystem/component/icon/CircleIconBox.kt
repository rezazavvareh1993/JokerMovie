package com.rezazavareh7.designsystem.component.icon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun CircleIconBoxComponent(
    modifier: Modifier = Modifier,
    icon: Int,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.primaryContainer,
    boxSize: Dp = 48.dp,
    iconSize: Dp = 32.dp,
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    isClickable: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            if (isClickable) {
                modifier
                    .size(boxSize)
                    .background(backgroundColor, shape = CircleShape)
                    .border(BorderStroke(borderWidth, borderColor), shape = CircleShape)
                    .clickable { onClick() }
            } else {
                modifier
                    .size(boxSize)
                    .background(backgroundColor, shape = CircleShape)
                    .border(BorderStroke(borderWidth, borderColor), shape = CircleShape)
            },
    ) {
        IconComponent(
            drawableId = icon,
            tint = iconTint,
            iconSize = iconSize,
            boxSize = iconSize,
        )
    }
}

@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun CircleIconBoxComponentPreview() {
    JokerMovieTheme {
        CircleIconBoxComponent(
            icon = LocalJokerIconPalette.current.icJokerSearch,
        )
    }
}
