package com.rezazavareh7.movies.ui.media.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette

@Composable
fun SearchHistoryListItemComponent(
    query: String,
    clickOnItem: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .clickable {
                    clickOnItem(query)
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconComponent(
            drawableId = LocalJokerIconPalette.current.icHistory,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            boxSize = 20.dp,
            iconSize = 20.dp,
        )
        Spacer(Modifier.width(8.dp))
        TitleMediumTextComponent(
            text = query,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
