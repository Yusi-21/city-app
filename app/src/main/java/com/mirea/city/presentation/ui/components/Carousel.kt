package com.mirea.city.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.mirea.city.data.model.PlacePhoto
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteCarousel(
    favorites: List<PlacePhoto>,
    onItemClick: (PlacePhoto) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        favorites.size
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        contentPadding = PaddingValues(horizontal = 80.dp),
        pageSpacing = 16.dp,
    ) { page ->
        val place = favorites[page]
        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

        CarouselItem(
            place = place,
            pageOffset = pageOffset,
            onClick = { onItemClick(place) }
        )
    }
}

@Composable
fun CarouselItem(
    place: PlacePhoto,
    pageOffset: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember(pageOffset) {
        0.8f + (0.2f * (1 - pageOffset.absoluteValue.coerceIn(0f, 1f)))
    }

    val alpha = remember(pageOffset) {
        0.5f + (0.5f * (1 - pageOffset.absoluteValue.coerceIn(0f, 1f)))
    }

    Card(
        modifier = modifier
            .width(280.dp)
            .height(350.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .clip(RoundedCornerShape(24.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = (8 * scale).dp.value.toInt().dp
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp)),
                painter = painterResource(place.placesImageResId),
                contentDescription = stringResource(place.placesTitleId),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = stringResource(place.placesTitleId),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )
            }
        }
    }
}