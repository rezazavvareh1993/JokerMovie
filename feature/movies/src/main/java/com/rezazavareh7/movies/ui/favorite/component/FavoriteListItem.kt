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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rezazavareh7.common.util.extensions.formattedStringOneDecimal
import com.rezazavareh7.designsystem.component.icon.IconComponent
import com.rezazavareh7.designsystem.component.text.title.TitleMediumTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.designsystem.util.getScreenDpSize
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteListItem(
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
                        .height(getScreenDpSize().height * 0.12f)
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
                Column(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TitleMediumTextComponent(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState(key = "title$groupName${item.id}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        renderInOverlayDuringTransition = false,
                                    ).padding(end = 16.dp),
                            text = item.title,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        IconComponent(
                            drawableId = LocalJokerIconPalette.current.icDelete,
                            isClickable = true,
                            tint = MaterialTheme.colorScheme.onSurface,
                            onClick = { deletedItemId = item.id },
                            iconSize = 20.dp,
                            boxSize = 20.dp,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TitleMediumTextComponent(
                            text = item.releaseDate,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            IconComponent(
                                drawableId = LocalJokerIconPalette.current.icIMDB,
                                iconSize = 16.dp,
                                boxSize = 16.dp,
                            )
                            TitleMediumTextComponent(
                                text = item.voteAverage.formattedStringOneDecimal(),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }
        }
    }
}
