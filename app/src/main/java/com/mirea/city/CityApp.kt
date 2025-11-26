package com.mirea.city

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mirea.city.data.repository.citiesPhoto
import com.mirea.city.data.repository.drawerItems
import com.mirea.city.data.repository.getPlacesByCityId
import com.mirea.city.presentation.state.CityAppState
import com.mirea.city.navigation.Screen
import com.mirea.city.navigation.cityNavigationGraph
import com.mirea.city.navigation.navigateToFavorites
import com.mirea.city.presentation.ui.screen.CitiesDetailsExpandedScreen
import com.mirea.city.presentation.ui.screen.CitiesHomeExpandedScreen
import com.mirea.city.presentation.ui.screen.CitiesPlacesExpandedScreen
import com.mirea.city.presentation.utils.CityContentType
import com.mirea.city.presentation.utils.CityNavigationType
import com.mirea.city.presentation.viewmodel.CityAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass,
    cityAppViewModel: CityAppViewModel = viewModel()
) {
    val navController = rememberNavController()
    val appState by cityAppViewModel.appState.collectAsState()

    val (navigationType, contentType) = when(windowSize) {
        WindowWidthSizeClass.Compact -> {
            Pair(CityNavigationType.DRAWER_NAVIGATION, CityContentType.LIST_ONLY)
        }
        WindowWidthSizeClass.Medium -> {
            Pair(CityNavigationType.NAVIGATION_RAIL, CityContentType.LIST_ONLY)
        }
        WindowWidthSizeClass.Expanded -> {
            Pair(CityNavigationType.PERMANENT_NAVIGATION_DRAWER, CityContentType.LIST_AND_DETAILS)
        }
        else -> {
            Pair(CityNavigationType.DRAWER_NAVIGATION, CityContentType.LIST_ONLY)
        }
    }

    LaunchedEffect(windowSize) {
        cityAppViewModel.updateNavigationType(navigationType)
    }

    if (navigationType == CityNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(modifier = Modifier.width(180.dp)) {
                    Column {
                        Text(
                            text = stringResource(R.string.home),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp)
                        )

                        Divider()

                        drawerItems.forEach { item ->
                            NavigationDrawerItem(
                                label = { Text(stringResource(item.title)) },
                                selected = false,
                                onClick = {
                                    when (item.route) {
                                        "home" -> {
                                            cityAppViewModel.clearSelection()
                                        }
                                        "favorites" -> navController.navigateToFavorites()
                                    }
                                },
                                icon = {
                                    when (item.route) {
                                        "home" -> Icon(Icons.Outlined.Place, contentDescription = null)
                                        "favorites" -> Icon(Icons.Default.Favorite, contentDescription = null)
                                        else -> Icon(Icons.Default.Home, contentDescription = null)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) {
            ExpandedScreenLayout(
                navController = navController,
                cityAppViewModel = cityAppViewModel,
                appState = appState,
                modifier = Modifier.fillMaxSize()
            )
        }
    } else {
        CityAppContent(
            navigationType = navigationType,
            navController = navController,
            cityAppViewModel = cityAppViewModel
        )
    }
}


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityTopAppBar(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    showMenuIcon: Boolean = true,
    showBackButton: Boolean = false,
    showSearchIcon: Boolean = true,
    showShareIcon: Boolean = false,
    onShareClick: (() -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    title: @Composable () -> Unit = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(R.raw.icon_city_skyline)
            )

            LottieAnimation(
                composition = composition,
                iterations = Int.MAX_VALUE,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color.Blue,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
) {
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
    ) {
        CenterAlignedTopAppBar(
            title = { title() },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "BackButton"
                        )
                    }
                } else if (showMenuIcon) {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                        )
                    }
                }
            },
            actions = {
                if (showSearchIcon) {
                    IconButton(
                        onClick = onSearchClick ?: {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search everything"
                        )
                    }
                }

                if (showShareIcon) {
                    IconButton(
                        onClick = {
                            onShareClick?.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share this place"
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CityAppContent(
    navigationType: CityNavigationType,
    navController: NavHostController,
    cityAppViewModel: CityAppViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        cityNavigationGraph(
            navController = navController,
            cityAppViewModel = cityAppViewModel,
            navigationType = navigationType
        )
    }
}



@Composable
fun ExpandedScreenLayout(
    navController: NavHostController,
    cityAppViewModel: CityAppViewModel,
    appState: CityAppState,
    modifier: Modifier = Modifier
) {
    val selectedCityId = appState.isSelectedCityId ?: citiesPhoto.firstOrNull()?.cityId
    val places = if (selectedCityId != null) getPlacesByCityId(selectedCityId) else emptyList()
    val selectedPlaceId = appState.isSelectedPlaceId

    LaunchedEffect(Unit) {
        if (selectedCityId != null && appState.isSelectedCityId == null) {
            cityAppViewModel.selectCity(selectedCityId)
        }
    }

    Row(modifier = modifier) {
        Surface(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            tonalElevation = 4.dp
        ) {
            CitiesHomeExpandedScreen(
                onCityClick = { cityId, cityNameRes ->
                    cityAppViewModel.selectCity(cityId)
                },
                onFavoritesClick = {
                    navController.navigateToFavorites()
                },
                selectedCityId = selectedCityId,
                onCitySelected = { cityId ->
                    cityAppViewModel.selectCity(cityId)
                }
            )
        }

        Surface(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            tonalElevation = 2.dp
        ) {
            if (selectedCityId != null) {
                if (selectedPlaceId != null) {
                    val place = places.find { it.placeId == selectedPlaceId }
                    if (place != null) {
                        CitiesDetailsExpandedScreen(
                            cityAppViewModel = cityAppViewModel,
                            placeId = selectedPlaceId,
                            imageResId = place.placesImageResId,
                            titleResId = place.placesTitleId,
                            onBackClick = {
                                cityAppViewModel.selectPlace(null)
                            }
                        )
                    } else {
                        CitiesPlacesExpandedScreen(
                            cityId = selectedCityId,
                            places = places,
                            onPlaceClick = { place ->
                                cityAppViewModel.selectPlace(place.placeId)
                            },
                            selectedPlaceId = selectedPlaceId,
                            onPlaceSelected = { placeId ->
                                cityAppViewModel.selectPlace(placeId)
                            },
                            onBackClick = {
                                cityAppViewModel.selectCity(null)
                            }
                        )
                    }
                } else {
                    CitiesPlacesExpandedScreen(
                        cityId = selectedCityId,
                        places = places,
                        onPlaceClick = { place ->
                            cityAppViewModel.selectPlace(place.placeId)
                        },
                        selectedPlaceId = selectedPlaceId,
                        onPlaceSelected = { placeId ->
                            cityAppViewModel.selectPlace(placeId)
                        },
                        onBackClick = {
                            cityAppViewModel.selectCity(null)
                        }
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Select a city to see places",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}