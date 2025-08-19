package com.rezazavareh7.designsystem.component.button.segmentbutton.singlechoice

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.runtime.Composable

@Composable
fun SingleChoiceSegmentedButtonRowScope.GetSegmentButtonWithoutIcon(
    index: Int,
    key: String,
    count: Int,
    label: @Composable () -> Unit,
    onClick: (String) -> Unit,
    selected: Boolean,
) {
    SegmentedButton(
        shape = SegmentedButtonDefaults.itemShape(index = index, count = count),
        onClick = {
            onClick(key)
        },
        colors =
            SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                inactiveContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                activeBorderColor = MaterialTheme.colorScheme.outline,
                inactiveBorderColor = MaterialTheme.colorScheme.outline,
                activeContentColor = MaterialTheme.colorScheme.surface,
                inactiveContentColor = MaterialTheme.colorScheme.surface,
            ),
        selected = selected,
        label = {
            label.invoke()
        },
    )
}
