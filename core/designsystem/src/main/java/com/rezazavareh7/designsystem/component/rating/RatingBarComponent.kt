package com.rezazavareh7.designsystem.component.rating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette

@Composable
fun RatingBarComponent(
    modifier: Modifier = Modifier,
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    stars: Int = 5,
    starSize: Int = 32,
) {
    Row(modifier = modifier) {
        for (i in 1..stars) {
            IconComponent(
                drawableId =
                    if (i <= rating) {
                        LocalJokerIconPalette.current.icStar
                    } else {
                        LocalJokerIconPalette.current.icBorderStar
                    },
                tint = MaterialTheme.colorScheme.primary,
                modifier =
                    Modifier
                        .size(starSize.dp)
                        .clickable { onRatingChanged(i.toFloat()) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarComponentPreview() {
    var rating by remember { mutableStateOf(3f) }

    RatingBarComponent(
        rating = rating,
        onRatingChanged = { newRating ->
            rating = newRating
        },
        stars = 5,
        starSize = 32,
    )
}
