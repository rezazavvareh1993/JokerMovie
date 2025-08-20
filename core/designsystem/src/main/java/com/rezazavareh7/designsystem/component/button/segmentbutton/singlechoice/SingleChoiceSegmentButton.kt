package com.rezazavareh7.designsystem.component.button.segmentbutton.singlechoice

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@Composable
fun SingleChoiceSegmentButtonComponent(
    modifier: Modifier = Modifier,
    options: LinkedHashMap<String, SegmentButtonItem>,
    selectedIndex: Int = 0,
    colors: SegmentedButtonColors =
        SegmentedButtonDefaults.colors(
            activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
            activeBorderColor = MaterialTheme.colorScheme.outline,
            inactiveBorderColor = MaterialTheme.colorScheme.outline,
            activeContentColor = MaterialTheme.colorScheme.onSurface,
            inactiveContentColor = MaterialTheme.colorScheme.onSurface,
        ),
    iconSize: Dp = 18.dp,
    buttonClicked: (String, Int) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.entries.forEachIndexed { index, item ->
            if (item.value.icon != null) {
                GetSegmentButtonWithIcon(
                    index = index,
                    key = item.key,
                    count = options.size,
                    selected = index == selectedIndex,
                    colors = colors,
                    onClick = {
                        buttonClicked(it, index)
                    },
                    label = {
                        Text(text = item.value.title)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(item.value.icon!!),
                            modifier = Modifier.size(iconSize),
                            contentDescription = null,
                        )
                    },
                )
            } else {
                GetSegmentButtonWithoutIcon(
                    index = index,
                    key = item.key,
                    count = options.size,
                    colors = colors,
                    selected = index == selectedIndex,
                    onClick = {
                        buttonClicked(it, index)
                    },
                    label = {
                        Text(text = item.value.title)
                    },
                )
            }
        }
    }
}

data class SegmentButtonItem(
    val title: String,
    @DrawableRes val icon: Int? = null,
)

@Preview
@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun SingleChoiceSegmentButtonComponentPreview() {
    JokerMovieTheme {
        SingleChoiceSegmentButtonComponent(
            options = linkedMapOf(),
            buttonClicked = { _, _ -> },
        )
    }
}
