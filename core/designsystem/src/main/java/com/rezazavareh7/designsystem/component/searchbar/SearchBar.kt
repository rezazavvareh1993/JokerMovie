package com.rezazavareh7.designsystem.component.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.body.BodyLargeTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.JokerMovieTheme
import androidx.compose.ui.graphics.Shape as ComposeShape
import com.rezazavareh7.designsystem.theme.Shape as CustomShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    query: String = "",
    onSearch: () -> Unit,
    placeHolder: String,
    isExpanded: Boolean,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    expandedLayerShape: ComposeShape = CustomShape.highRoundCornerTop,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit = {},
    maxQueryLength: Int = Int.MAX_VALUE,
) {
    Box(
        modifier =
            modifier
                .wrapContentSize()
                .then(
                    if (isExpanded) {
                        modifier.background(
                            color = containerColor,
                            shape = expandedLayerShape,
                        )
                    } else {
                        modifier
                    },
                ),
    ) {
        SearchBar(
            colors =
                SearchBarDefaults.colors(
                    containerColor = if (isExpanded) Color.Transparent else MaterialTheme.colorScheme.surfaceContainer,
                    dividerColor = MaterialTheme.colorScheme.outlineVariant,
                ),
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { newQuery ->
                        if (newQuery.length <= maxQueryLength) {
                            onQueryChange(newQuery)
                        }
                    },
                    onSearch = {
                        onSearch()
                    },
                    expanded = isExpanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = {
                        BodyLargeTextComponent(
                            text = placeHolder,
                            color = MaterialTheme.colorScheme.outlineVariant,
                        )
                    },
                    trailingIcon = {
                        if (isExpanded) {
                            IconComponent(
                                drawableId = LocalJokerIconPalette.current.icCancel,
                                tint = MaterialTheme.colorScheme.outline,
                                isClickable = true,
                                onClick = { onExpandedChange(false) },
                            )
                        }
                    },
                    leadingIcon = {
                        IconComponent(
                            drawableId = LocalJokerIconPalette.current.icSearch,
                            tint = MaterialTheme.colorScheme.outline,
                            isClickable = isExpanded,
                            onClick = onSearch,
                        )
                    },
                )
            },
            expanded = isExpanded,
            onExpandedChange = onExpandedChange,
            content = { content() },
        )
    }
}

@PreviewFontScale
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun SearchBarComponentPreview() {
    JokerMovieTheme {
        SearchBarComponent(
            onQueryChange = {},
            onSearch = {},
            placeHolder = "Search",
            isExpanded = false,
            onExpandedChange = {},
        )
    }
}
