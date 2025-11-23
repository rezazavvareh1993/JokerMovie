package com.rezazavareh7.movies.ui.media.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.custom.LocalJokerColorPalette
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.preview.JockerPreview
import com.rezazavareh7.designsystem.theme.JokerMovieTheme
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.ui.model.MediaTabItem

@Composable
fun MediaTabRowComponent(
    modifier: Modifier = Modifier,
    currentTabIndex: Int,
    onTabClick: (MediaCategory, Int) -> Unit,
) {
    TabRow(
        modifier = modifier.fillMaxWidth(),
        containerColor = Color.Transparent,
        selectedTabIndex = currentTabIndex,
        divider = { HorizontalDivider(thickness = 2.dp) },
        indicator = { tabPosition ->
            TabRowDefaults.SecondaryIndicator(
                modifier =
                    Modifier
                        .tabIndicatorOffset(tabPosition[currentTabIndex])
                        .clip(Shape.highRoundCorner),
                height = 2.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        },
    ) {
        getTabRowItems().forEachIndexed { index, item ->
            val isSelected = currentTabIndex == index
            Tab(
                selectedContentColor = LocalJokerColorPalette.current.yellow,
                unselectedContentColor = MaterialTheme.colorScheme.error,
                modifier =
                    Modifier
                        .clip(Shape.highRoundCorner)
                        .padding(bottom = 8.dp),
                selected = isSelected,
                onClick = {
                    onTabClick(item.type, index)
                },
            ) {
                MediaItemComponent(item, isSelected = isSelected)
            }
        }
    }
}

@Composable
private fun getTabRowItems(): List<MediaTabItem> =
    listOf(
        MediaTabItem(
            name = stringResource(R.string.movie),
            type = MediaCategory.MOVIE,
            icon = LocalJokerIconPalette.current.icMovie,
        ),
        MediaTabItem(
            name = stringResource(R.string.series),
            type = MediaCategory.SERIES,
            icon = LocalJokerIconPalette.current.icTV,
        ),
    )

@JockerPreview
@Composable
private fun MediaTabRowPreview() {
    JokerMovieTheme {
        MediaTabRowComponent(
            currentTabIndex = 0,
            onTabClick = { _, _ -> },
        )
    }
}
