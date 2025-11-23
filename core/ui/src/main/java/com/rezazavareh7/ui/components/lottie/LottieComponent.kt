package com.rezazavareh7.ui.components.lottie

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationComponent(
    modifier: Modifier = Modifier,
    lottieResource: Int,
    iterations: Int = LottieConstants.IterateForever,
    onFinish: () -> Unit = {},
) {
    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(lottieResource),
    )

    val preLoaderProgress by animateLottieCompositionAsState(
        composition = preLoaderLottieComposition,
        iterations = iterations,
        isPlaying = true,
    )

    LaunchedEffect(preLoaderProgress) {
        if (preLoaderProgress == 1f) {
            onFinish()
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        LottieAnimation(
            modifier = Modifier.align(Alignment.Center),
            composition = preLoaderLottieComposition,
            progress = { preLoaderProgress },
        )
    }
}
