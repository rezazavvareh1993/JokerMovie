package com.rezazavareh7.designsystem.component.searchbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    content: @Composable () -> Unit = {},
    maxQueryLength: Int = Int.MAX_VALUE,
) {
    var isActive by remember { mutableStateOf(false) }

    SearchBar(
        colors =
            SearchBarDefaults.colors(
                containerColor = if (isActive) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceContainer,
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
                    isActive = false
                },
                expanded = isActive,
                onExpandedChange = { isActive = it },
                placeholder = {
                    BodyLargeTextComponent(text = placeHolder, color = MaterialTheme.colorScheme.onSurfaceVariant)
                },
                leadingIcon = {
                    if (isActive) {
                        IconComponent(
                            drawableId = LocalJokerIconPalette.current.icCancel,
                            tint = MaterialTheme.colorScheme.onSurface,
                            isClickable = true,
                            onClick = {
                                onQueryChange("")
                                isActive = false
                            },
                        )
                    } else {
                        IconComponent(
                            drawableId = LocalJokerIconPalette.current.icSearch,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
            )
        },
        expanded = isActive,
        onExpandedChange = { isActive = it },
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
        )
    }
}
