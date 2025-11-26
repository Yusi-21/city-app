package com.mirea.city.presentation.ui.screen

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.mirea.city.CityTopAppBar
import com.mirea.city.R
import com.mirea.city.data.repository.getCityAndCountryByPlaceId
import com.mirea.city.data.repository.getDetailsByPlaceId
import com.mirea.city.data.model.PlacePhoto
import com.mirea.city.navigation.Screen
import com.mirea.city.presentation.utils.CityNavigationType
import com.mirea.city.presentation.viewmodel.CityAppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesDetailsScreen(
    navigationType: CityNavigationType,
    cityAppViewModel: CityAppViewModel,
    placeId: String,
    @DrawableRes imageResId: Int,
    @StringRes titleResId: Int,
    onBackClick: () -> Unit,
    currentRoute: String = Screen.Home.route,
    onNavigationItemClick: (String) -> Unit = { }
) {
    when (navigationType) {
        CityNavigationType.DRAWER_NAVIGATION -> {
            CitiesDetailsCompactScreen(
                cityAppViewModel = cityAppViewModel,
                placeId = placeId,
                imageResId = imageResId,
                titleResId = titleResId,
                onBackClick = onBackClick
            )
        }
        CityNavigationType.NAVIGATION_RAIL -> {
            CitiesDetailsMediumScreen(
                cityAppViewModel = cityAppViewModel,
                placeId = placeId,
                imageResId = imageResId,
                titleResId = titleResId,
                onBackClick = onBackClick,
                currentRoute = currentRoute,
                onNavigationItemClick = onNavigationItemClick
            )
        }
        CityNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            CitiesDetailsExpandedScreen(
                cityAppViewModel = cityAppViewModel,
                placeId = placeId,
                imageResId = imageResId,
                titleResId = titleResId,
                onBackClick = onBackClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesDetailsCompactScreen(
    cityAppViewModel: CityAppViewModel,
    placeId: String,
    @DrawableRes imageResId: Int,
    @StringRes titleResId: Int,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val placeDetails = getDetailsByPlaceId(placeId)

    var isFavorite by rememberSaveable(placeId) {
        mutableStateOf(cityAppViewModel.isFavorite(placeId))
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CityTopAppBar(
                scrollBehavior = scrollBehavior,
                onMenuClick = onBackClick,
                showSearchIcon = false,
                showBackButton = true,
                showShareIcon = true,
                onShareClick = {
                    sharePlaceDetails(
                        context = context,
                        placeId = placeId,
                        titleResId = titleResId
                    )
                },
                title = {
                    Text(
                        text = stringResource(titleResId),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.clip(CircleShape),
                onClick = {
                    val place = PlacePhoto(
                        placesImageResId = imageResId,
                        placesTitleId = titleResId,
                        placeId = placeId
                    )

                    val wasFavorite = isFavorite

                    if (isFavorite) {
                        cityAppViewModel.removeFromFavorites(placeId)
                        isFavorite = false
                    } else {
                        cityAppViewModel.addToFavorites(place)
                        isFavorite = true
                    }

                    scope.launch {
                        val message = if (wasFavorite) {
                            "Removed from Favorites"
                        } else {
                            "Added to Favorites"
                        }
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.Favorite,
                    contentDescription = if (isFavorite) "Remove from Favorites" else "Add this place to favorites",
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        if (placeDetails == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Details not found for this place")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                content = {
                    item {
                        Image(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(imageResId),
                            contentDescription = null
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.address),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(placeDetails.addressId),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = stringResource(R.string.about),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(placeDetails.descId),
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesDetailsMediumScreen(
    cityAppViewModel: CityAppViewModel,
    placeId: String,
    @DrawableRes imageResId: Int,
    @StringRes titleResId: Int,
    onBackClick: () -> Unit,
    currentRoute: String,
    onNavigationItemClick: (String) -> Unit
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val placeDetails = getDetailsByPlaceId(placeId)

    var isFavorite by rememberSaveable(placeId) {
        mutableStateOf(cityAppViewModel.isFavorite(placeId))
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CityTopAppBar(
                scrollBehavior = scrollBehavior,
                onMenuClick = onBackClick,
                showSearchIcon = false,
                showBackButton = true,
                showShareIcon = true,
                showMenuIcon = false,
                onShareClick = {
                    sharePlaceDetails(
                        context = context,
                        placeId = placeId,
                        titleResId = titleResId
                    )
                },
                title = {
                    Text(
                        text = stringResource(titleResId),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.clip(CircleShape),
                onClick = {
                    val place = PlacePhoto(
                        placesImageResId = imageResId,
                        placesTitleId = titleResId,
                        placeId = placeId
                    )

                    val wasFavorite = isFavorite

                    if (isFavorite) {
                        cityAppViewModel.removeFromFavorites(placeId)
                        isFavorite = false
                    } else {
                        cityAppViewModel.addToFavorites(place)
                        isFavorite = true
                    }

                    scope.launch {
                        val message = if (wasFavorite) {
                            "Removed from Favorites"
                        } else {
                            "Added to Favorites"
                        }
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.Favorite,
                    contentDescription = if (isFavorite) "Remove from Favorites" else "Add this place to favorites",
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        if (placeDetails == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Details not found for this place")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                content = {
                    item {
                        Image(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(imageResId),
                            contentDescription = null
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.address),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(placeDetails.addressId),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = stringResource(R.string.about),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(placeDetails.descId),
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesDetailsExpandedScreen(
    cityAppViewModel: CityAppViewModel,
    placeId: String,
    @DrawableRes imageResId: Int,
    @StringRes titleResId: Int,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val placeDetails = getDetailsByPlaceId(placeId)

    var isFavorite by rememberSaveable(placeId) {
        mutableStateOf(cityAppViewModel.isFavorite(placeId))
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CityTopAppBar(
                scrollBehavior = scrollBehavior,
                onMenuClick = onBackClick,
                showSearchIcon = false,
                showBackButton = true,
                showShareIcon = true,
                showMenuIcon = false,
                onShareClick = {
                    sharePlaceDetails(
                        context = context,
                        placeId = placeId,
                        titleResId = titleResId
                    )
                },
                title = {
                    Text(
                        text = stringResource(titleResId),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.clip(CircleShape),
                onClick = {
                    val place = PlacePhoto(
                        placesImageResId = imageResId,
                        placesTitleId = titleResId,
                        placeId = placeId
                    )

                    val wasFavorite = isFavorite

                    if (isFavorite) {
                        cityAppViewModel.removeFromFavorites(placeId)
                        isFavorite = false
                    } else {
                        cityAppViewModel.addToFavorites(place)
                        isFavorite = true
                    }

                    scope.launch {
                        val message = if (wasFavorite) {
                            "Removed from Favorites"
                        } else {
                            "Added to Favorites"
                        }
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.Favorite,
                    contentDescription = if (isFavorite) "Remove from Favorites" else "Add this place to favorites",
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        if (placeDetails == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Details not found for this place")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                content = {
                    item {
                        Image(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(imageResId),
                            contentDescription = null
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.address),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(placeDetails.addressId),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = stringResource(R.string.about),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = stringResource(placeDetails.descId),
                                    style = MaterialTheme.typography.bodyLarge,
                                    lineHeight = 24.sp,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            )
        }
    }
}

private fun sharePlaceDetails(
    context: Context,
    placeId: String,
    @StringRes titleResId: Int
) {
    val placeDetails = getDetailsByPlaceId(placeId)
    val (city, country) = getCityAndCountryByPlaceId(placeId)

    val shareText = buildString {
        append("${context.getString(titleResId)}\n\n")

        if (placeDetails != null) {
            append("Address: ${context.getString(placeDetails.addressId)}\n\n")
            append("About: ${context.getString(placeDetails.descId)}")
        } else {
            append("Information for this place not found")
        }

        append("\n\n-- Shared from Cities App --")
    }

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        putExtra(Intent.EXTRA_SUBJECT, "${context.getString(titleResId)} - $city, $country")
    }

    val chooserIntent = Intent.createChooser(shareIntent, "Share info for this place")
    ContextCompat.startActivity(context, chooserIntent, null)
}
