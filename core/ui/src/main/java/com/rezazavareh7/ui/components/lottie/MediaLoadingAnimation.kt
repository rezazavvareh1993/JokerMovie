package com.rezazavareh7.ui.components.lottie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rezazavareh7.designsystem.R

@Composable
fun MediaLoadingAnimation(modifier: Modifier = Modifier) {
    LottieAnimationComponent(
        lottieResource = R.raw.lottie_video_loading,
        modifier = modifier.fillMaxSize(),
    )
}
