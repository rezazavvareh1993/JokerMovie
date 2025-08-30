package com.rezazavareh7.designsystem.component.progressbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.preview.JockerPreview
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun CircularProgressBarComponent(
    modifier: Modifier = Modifier,
    size: Dp = 42.dp,
    isVisible: Boolean = true,
) {
    AnimatedVisibility(visible = isVisible) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(size))
        }
    }
}

@JockerPreview
@Composable
private fun CircularProgressBarComponentPreview() {
    JokerMovieTheme {
        CircularProgressBarComponent()
    }
}
