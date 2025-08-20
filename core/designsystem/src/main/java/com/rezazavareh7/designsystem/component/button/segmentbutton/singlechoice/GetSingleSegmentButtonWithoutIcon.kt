package com.rezazavareh7.designsystem.component.button.segmentbutton.singlechoice

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.runtime.Composable

@Composable
fun SingleChoiceSegmentedButtonRowScope.GetSegmentButtonWithoutIcon(
    index: Int,
    key: String,
    count: Int,
    colors: SegmentedButtonColors,
    label: @Composable () -> Unit,
    onClick: (String) -> Unit,
    selected: Boolean,
) {
    SegmentedButton(
        shape = SegmentedButtonDefaults.itemShape(index = index, count = count),
        onClick = {
            onClick(key)
        },
        colors = colors,
        selected = selected,
        label = {
            label.invoke()
        },
    )
}
