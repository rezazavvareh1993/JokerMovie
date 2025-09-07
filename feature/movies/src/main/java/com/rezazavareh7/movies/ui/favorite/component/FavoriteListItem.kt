import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.common.util.extensions.formattedStringOneDecimal
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.body.BodyMediumTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.movies.domain.model.FavoriteData
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteListItem(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    groupName: String,
    item: FavoriteData,
    navigateToMediaDetailsScreen: (Long, String, String) -> Unit,
    onRemoveFavoriteClicked: (FavoriteData) -> Unit,
) {
    with(sharedTransitionScope) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(shape = Shape.highRoundCorner)
                    .clickable {
                        navigateToMediaDetailsScreen(
                            item.id,
                            item.category.name,
                            groupName,
                        )
                    },
        ) {
            ShowGlideImageByUrl(
                modifier =
                    Modifier
                        .matchParentSize()
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "poster$groupName${item.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            renderInOverlayDuringTransition = false,
                        ),
                context = LocalContext.current,
                imageUrlPath = item.posterPath,
            )
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .background(
                            brush =
                                Brush.verticalGradient(
                                    0.5f to MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                    0.85f to MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                    1f to Color.Transparent,
                                ),
                        ),
            )
            Row(
                modifier =
                    Modifier
                        .matchParentSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    TitleMediumTextComponent(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = "title$groupName${item.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    renderInOverlayDuringTransition = false,
                                ),
                        text = item.title,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BodyMediumTextComponent(
                                text = "Release Date: ",
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            BodyMediumTextComponent(
                                item.releaseDate,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BodyMediumTextComponent(
                                text = "Rate: ",
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            BodyMediumTextComponent(
                                item.voteAverage.formattedStringOneDecimal(),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
                IconComponent(
                    drawableId = LocalJokerIconPalette.current.icDelete,
                    isClickable = true,
                    tint = MaterialTheme.colorScheme.onSurface,
                    onClick = { onRemoveFavoriteClicked(item) },
                )
            }
        }
    }
}
