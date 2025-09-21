package com.rezazavareh7.movies.ui.moviedetails.component

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.text.body.BodySmallTextComponent
import com.rezazavareh7.designsystem.component.text.label.LabelMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
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
                .padding(4.dp)
                .width(100.dp)
                .height(150.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = Shape.highRoundCorner,
                )
                .padding(8.dp)
                .clickable { onItemClicked(credit.id) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LabelMediumTextComponent(
            text = credit.name,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .wrapContentHeight(),
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.MiddleEllipsis,
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        shape = Shape.highRoundCorner,
                    ),
        ) {
            ShowGlideImageByUrl(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .clip(Shape.highRoundCorner),
                imageUrlPath = credit.pathUrl,
                contentScale = ContentScale.FillBounds,
                context = LocalContext.current,
                placeHolder = LocalJokerIconPalette.current.icMovie,
            )
        }
        Spacer(Modifier.height(4.dp))
        LabelMediumTextComponent(
            modifier = Modifier.fillMaxWidth(),
            text = if (credit.role == Role.DIRECTING) "Directing" else credit.characterName,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.MiddleEllipsis,
            textAlign = TextAlign.Center,
        )
    }
}
