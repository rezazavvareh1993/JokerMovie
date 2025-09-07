package com.rezazavareh7.designsystem.component.searchbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.body.BodyLargeTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.JokerMovieTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    query: String = "",
    onSearch: () -> Unit,
    placeHolder: String,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit = {},
    maxQueryLength: Int = Int.MAX_VALUE,
) {
    SearchBar(
        colors =
            SearchBarDefaults.colors(
                containerColor = if (isExpanded) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceContainer,
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
                    onExpandedChange(false)
                },
                expanded = isExpanded,
                onExpandedChange = onExpandedChange,
                placeholder = {
                    BodyLargeTextComponent(
                        text = placeHolder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (isExpanded)
                        IconComponent(
                            drawableId = LocalJokerIconPalette.current.icCancel,
                            tint = MaterialTheme.colorScheme.onSurface,
                            isClickable = true,
                            onClick = { onExpandedChange(false) },
                        )
                },
                leadingIcon = {
                    IconComponent(
                        drawableId = LocalJokerIconPalette.current.icSearch,
                        tint = MaterialTheme.colorScheme.onSurface,
                        isClickable = isExpanded,
                        onClick = onSearch,
                    )
                },
            )
        },
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        content = { content() },
    )
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
            onExpandedChange = {}
        )
    }
}
