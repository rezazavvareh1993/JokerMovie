package com.rezazavareh7.movies.ui.images.component

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

private const val DEFAULT_SWIPE_THRESHOLD = 200f
private const val DRAG_RESET_VALUE = 0f

@Composable
fun VerticalSwipeDetectorComponent(
    onSwipeUp: () -> Unit,
    onSwipeDown: () -> Unit,
    swipeThreshold: Float = DEFAULT_SWIPE_THRESHOLD,
    content: @Composable () -> Unit,
) {
    var totalVerticalDrag by remember { mutableFloatStateOf(DRAG_RESET_VALUE) }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragStart = {
                            totalVerticalDrag = DRAG_RESET_VALUE
                        },
                        onVerticalDrag = { _, dragAmountY ->
                            totalVerticalDrag += dragAmountY
                        },
                        onDragEnd = {
                            when {
                                totalVerticalDrag > swipeThreshold -> {
                                    onSwipeDown()
                                }
                                totalVerticalDrag < -swipeThreshold -> {
                                    onSwipeUp()
                                }
                            }
                        },
                    )
                },
    ) {
        content()
    }
}
