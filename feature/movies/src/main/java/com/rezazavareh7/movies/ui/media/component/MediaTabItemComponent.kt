package com.rezazavareh7.movies.ui.media.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.preview.JockerPreview
import com.rezazavareh7.designsystem.theme.JokerMovieTheme
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.ui.media.MediaTabItem

@Composable
fun MediaItemComponent(
    item: MediaTabItem,
    isSelected: Boolean,
) {
    val color =
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outlineVariant
        }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconComponent(
            drawableId = item.icon,
            tint = color,
            iconSize = 24.dp,
            boxSize = 24.dp,
        )
        TitleMediumTextComponent(
            text = item.name,
            color = color,
        )
    }
}

@JockerPreview
@Composable
private fun SignUpItemComponentPreview() {
    JokerMovieTheme {
        MediaItemComponent(
            MediaTabItem(
                name = stringResource(R.string.movie),
                type = MediaCategory.MOVIE,
                icon = LocalJokerIconPalette.current.icMovie,
            ),
            true,
        )
    }
}
