package com.rezazavareh7.movies.ui.movie.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.rating.RatingBarComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.movies.domain.model.MovieData
import com.rezazavareh7.ui.glide.ShowGlideImageByUrl

@Composable
fun MovieListItem(data: MovieData) {
    Column(
        modifier =
            Modifier
                .width(300.dp)
                .height(500.dp)
                .background(Color.Black, shape = Shape.highRoundCorner),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleMediumTextComponent(
            text = data.name,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surface,
            overFlow = TextOverflow.Ellipsis,
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = Shape.highRoundCorner,
                    ),
        ) {
            ShowGlideImageByUrl(
                modifier =
                    Modifier.fillMaxSize()
                        .clip(Shape.highRoundCorner),
                imageUrlPath = data.banner,
                contentScale = ContentScale.FillBounds,
                context = LocalContext.current,
                placeHolder = LocalJokerIconPalette.current.icMovie,
            )
        }

        RatingBarComponent(rating = data.rate, stars = 5, onRatingChanged = {})
        Spacer(Modifier.height(4.dp))
        TitleMediumTextComponent(text = data.releaseDate, color = Color.Gray)
    }
}
