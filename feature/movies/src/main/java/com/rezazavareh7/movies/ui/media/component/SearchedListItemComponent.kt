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
import com.rezazavareh7.designsystem.component.text.title.TitleLargeTextComponent
import com.rezazavareh7.designsystem.component.text.title.TitleSmallTextComponent
import com.rezazavareh7.designsystem.custom.LocalJokerIconPalette
import com.rezazavareh7.designsystem.theme.Shape
import com.rezazavareh7.designsystem.util.getScreenDpSize
import com.rezazavareh7.movies.domain.model.MediaData
import com.rezazavareh7.ui.components.glide.ShowGlideImageByUrl

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchedListItemComponent(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    groupName: String,
    item: MediaData,
    isLiked: Boolean,
    onItemClicked: (MediaData, String) -> Unit,
    onFavoriteClicked: (Boolean, MediaData) -> Unit,
) {
    with(sharedTransitionScope) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(getScreenDpSize().height * 0.2f)
                    .clip(shape = Shape.highRoundCorner)
                    .clickable {
                        onItemClicked(item, groupName)
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
                                    0.3f to MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                                    1f to MaterialTheme.colorScheme.surface.copy(alpha = 1f),
                                    1f to Color.Transparent,
                                ),
                        ),
            )
            Column(
                modifier =
                    Modifier
                        .matchParentSize()
                        .padding(vertical = 20.dp, horizontal = 16.dp),
            ) {
                val releasedYear = item.releaseDate.split("-").first()
                TitleLargeTextComponent(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "title$groupName${item.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                renderInOverlayDuringTransition = false,
                            ),
                    text = if (releasedYear.isNotEmpty()) "${item.title} - $releasedYear" else item.title,
                    overflow = TextOverflow.MiddleEllipsis,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                )
                Spacer(Modifier.weight(1f))
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        modifier =
                            Modifier
                                .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconComponent(
                            drawableId = LocalJokerIconPalette.current.icIMDB,
                            iconSize = 20.dp,
                            boxSize = 20.dp,
                        )
                        Spacer(Modifier.width(4.dp))
                        TitleSmallTextComponent(
                            modifier = Modifier.padding(top = 4.dp),
                            text = item.voteAverage.formattedStringOneDecimal(),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    IconComponent(
                        drawableId = if (isLiked) LocalJokerIconPalette.current.icLike else LocalJokerIconPalette.current.icDislike,
                        isClickable = true,
                        tint = if (isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                        onClick = { onFavoriteClicked(!isLiked, item) },
                        iconSize = 20.dp,
                        boxSize = 20.dp,
                    )
                }
            }
        }
    }
}
