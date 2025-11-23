@file:OptIn(ExperimentalMaterial3Api::class)

package com.rezazavareh7.designsystem.component.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun ToolbarComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    height: Dp = 56.dp,
    isBottomSheet: Boolean = false,
    hasBackButton: Boolean = false,
    backButtonColor: Color = MaterialTheme.colorScheme.primary,
    startContent: @Composable RowScope.() -> Unit = {},
    onBackClicked: () -> Unit = {},
    endContent: @Composable RowScope.() -> Unit = {},
) {
    Surface(
        color = backgroundColor,
    ) {
        Box(modifier = Modifier.statusBarsPadding()) {
            Row(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .height(if (isBottomSheet) 48.dp else height)
                        .padding(
                            top = WindowInsets.statusBars.getBottom(LocalDensity.current).dp,
                            start = 16.dp,
                            end = 16.dp,
                        ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (hasBackButton) {
                        IconComponent(
                            boxSize = 32.dp,
                            drawableId = LocalJokerIconPalette.current.icBack,
                            tint = backButtonColor,
                            isClickable = true,
                        ) {
                            onBackClicked()
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    startContent()
                }
                endContent()
            }
        }
    }
}

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun ToolbarComponentPreview() {
    JokerMovieTheme { ToolbarComponent() }
}
