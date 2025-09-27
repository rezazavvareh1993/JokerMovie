package com.rezazavareh7.movies.ui.moviedetails.component

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.text.label.LabelMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.movies.R
import com.rezazavareh7.movies.domain.model.Credit
import com.rezazavareh7.movies.domain.model.Role
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CreditListItemComponent(
    credit: Credit,
    onItemClicked: (Long) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(horizontal = 4.dp)
                .width(120.dp)
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = Shape.mediumRoundCorner,
                )
                .clickable { onItemClicked(credit.id) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        shape = Shape.highRoundCorner,
                    ),
        ) {
            ShowGlideImageByUrl(
                modifier =
                    Modifier
                        .matchParentSize()
                        .clip(Shape.mediumRoundCornerTop),
                imageUrlPath = credit.pathUrl,
                contentScale = ContentScale.FillBounds,
                context = LocalContext.current,
                placeHolder = LocalJokerIconPalette.current.icMovie,
            )
        }
        Spacer(Modifier.height(8.dp))
        LabelMediumTextComponent(
            text = credit.name,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
            maxLines = 2,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.MiddleEllipsis,
        )
        LabelMediumTextComponent(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            text = if (credit.role == Role.DIRECTOR) stringResource(R.string.director) else credit.characterName,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            overflow = TextOverflow.MiddleEllipsis,
        )
    }
}
