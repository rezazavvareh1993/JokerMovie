package com.rezazavareh7.movies.ui.movie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.rating.RatingBarComponent
import com.rezazavareh7.designsystem.component.text.body.BodySmallTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.ui.glide.ShowGlideImageByUrl

@Composable
fun MovieListItem(
    movieItem: MovieData,
    isLiked: Boolean,
    onMovieClicked: (Long) -> Unit,
    onFavoriteClicked: (Boolean, MovieData) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .width(175.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.onSurface, shape = Shape.highRoundCorner)
                .padding(8.dp)
                .clickable { onMovieClicked(movieItem.id) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BodySmallTextComponent(
            text = movieItem.title,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surface,
            overflow = TextOverflow.Ellipsis,
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = Shape.highRoundCorner,
                    ),
        ) {
            ShowGlideImageByUrl(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .clip(Shape.highRoundCorner),
                imageUrlPath = movieItem.posterPath,
                contentScale = ContentScale.FillBounds,
                context = LocalContext.current,
                placeHolder = LocalJokerIconPalette.current.icMovie,
            )

            IconComponent(
                drawableId =
                    if (isLiked) {
                        LocalJokerIconPalette.current.icLike
                    } else {
                        LocalJokerIconPalette.current.icDislike
                    },
                tint = MaterialTheme.colorScheme.error,
                modifier =
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart),
                iconSize = 16.dp,
                boxSize = 16.dp,
                isClickable = true,
                onClick = { onFavoriteClicked(!isLiked, movieItem) },
            )
        }

        RatingBarComponent(
            rating = movieItem.voteAverage,
            starSize = 12.dp,
            stars = 5,
            onRatingChanged = {},
        )
        Spacer(Modifier.height(4.dp))
        BodySmallTextComponent(text = movieItem.releaseDate, color = Color.Gray)
    }
}
