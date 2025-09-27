import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchedListItem(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    groupName: String,
    item: MediaData,
    favoriteList: List<MediaData>,
    navigateToMediaDetailsScreen: (MediaData, String) -> Unit,
    onRemoveFavoriteClicked: (MediaData) -> Unit,
) {
    var deletedItemId by remember { mutableLongStateOf(-1L) }

    LaunchedEffect(deletedItemId) {
        if (deletedItemId != -1L) {
            delay(300)
            if (favoriteList.isNotEmpty()) {
                onRemoveFavoriteClicked(item)
            } else {
                deletedItemId = -1
            }
        }
    }

    with(sharedTransitionScope) {
        AnimatedVisibility(
            visible = !favoriteList.map { it.id }.contains(deletedItemId),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(shape = Shape.highRoundCorner)
                        .clickable {
                            navigateToMediaDetailsScreen(
                                item,
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
                        onClick = { deletedItemId = item.id },
                    )
                }
            }
        }
    }
}
