package com.mirea.city.presentation.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirea.city.CityTopAppBar
import com.mirea.city.R
import com.mirea.city.data.repository.citiesPhoto
import com.mirea.city.data.repository.getPlacesByCityId
import com.mirea.city.data.model.PlacePhoto
import com.mirea.city.navigation.Screen
import com.mirea.city.presentation.utils.CityNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesPlacesScreen(
    navigationType: CityNavigationType,
    cityId: String,
    @StringRes cityNameRes: Int,
    onPlaceClick: (PlacePhoto) -> Unit,
    onBackClick: () -> Unit,
    currentRoute: String = Screen.Home.route,
    onNavigationItemClick: (String) -> Unit = { },
    selectedPlaceId: String? = null,
    onPlaceSelected: (String?) -> Unit = { }
) {
    val places = getPlacesByCityId(cityId)

    when (navigationType) {
        CityNavigationType.DRAWER_NAVIGATION -> {
            CitiesPlacesCompactScreen(
                cityId = cityId,
                cityNameRes = cityNameRes,
                onPlaceClick = onPlaceClick,
                onBackClick = onBackClick
            )
        }
        CityNavigationType.NAVIGATION_RAIL -> {
            CitiesPlacesMediumScreen(
                cityId = cityId,
                cityNameRes = cityNameRes,
                onPlaceClick = onPlaceClick,
                onBackClick = onBackClick,
                currentRoute = currentRoute,
                onNavigationItemClick = onNavigationItemClick
            )
        }
        CityNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            CitiesPlacesExpandedScreen(
                cityId = cityId,
                places = places,
                onPlaceClick = onPlaceClick,
                selectedPlaceId = selectedPlaceId,
                onPlaceSelected = onPlaceSelected,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
fun PlaceImage(
    @DrawableRes placeImage: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .padding(dimensionResource(R.dimen.small))
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        painter = painterResource(placeImage),
        contentDescription = null
    )
}

@Composable
fun PlaceTitle(
    @StringRes placeTitle: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            stringResource(placeTitle),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 18.sp,
            modifier = Modifier.padding(dimensionResource(R.dimen.small))
        )
    }
}

@Composable
fun PlaceItem(
    placesPhoto: PlacePhoto,
    onPlaceClick: (PlacePhoto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .clickable {
                onPlaceClick(placesPhoto)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlaceImage(
                placesPhoto.placesImageResId,
                modifier = Modifier
                    .weight(0.4f)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = 8.dp)
            ) {
                PlaceTitle(placesPhoto.placesTitleId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesPlacesCompactScreen(
    cityId: String,
    @StringRes cityNameRes: Int,
    onPlaceClick: (PlacePhoto) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val places = getPlacesByCityId(cityId)

    Scaffold(
        topBar = {
            CityTopAppBar(
                scrollBehavior = scrollBehavior,
                onMenuClick = onBackClick,
                showBackButton = true,
                showSearchIcon = false,
                showShareIcon = false,
                title = {
                    Text(
                        text = stringResource(cityNameRes),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        if (places.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No places found for this city")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = innerPadding
            ) {
                items(places) { place ->
                    PlaceItem(
                        placesPhoto = place,
                        onPlaceClick = onPlaceClick
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesPlacesMediumScreen(
    cityId: String,
    @StringRes cityNameRes: Int,
    onPlaceClick: (PlacePhoto) -> Unit,
    onBackClick: () -> Unit,
    currentRoute: String,
    onNavigationItemClick: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val places = getPlacesByCityId(cityId)

    Scaffold(
        topBar = {
            CityTopAppBar(
                scrollBehavior = scrollBehavior,
                onMenuClick = onBackClick,
                showBackButton = true,
                showSearchIcon = false,
                showShareIcon = false,
                showMenuIcon = false,
                title = {
                    Text(
                        text = stringResource(cityNameRes),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        if (places.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No places found for this city")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = innerPadding
            ) {
                items(places) { place ->
                    PlaceItem(
                        placesPhoto = place,
                        onPlaceClick = onPlaceClick
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun CitiesPlacesExpandedScreen(
    cityId: String,
    places: List<PlacePhoto>,
    onPlaceClick: (PlacePhoto) -> Unit,
    selectedPlaceId: String?,
    onPlaceSelected: (String?) -> Unit,
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val cityName = citiesPhoto.find { it.cityId == cityId }?.title ?: 0

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (cityName != 0) stringResource(cityName) else "Places",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 20.sp
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(places) { place ->
                PlaceItem(
                    placesPhoto = place,
                    onPlaceClick = {
                        if (selectedPlaceId == place.placeId) {
                            onPlaceSelected(null)
                        } else {
                            onPlaceClick(place)
                            onPlaceSelected(place.placeId)
                        }
                    }
                )
            }
        }
    }
}
