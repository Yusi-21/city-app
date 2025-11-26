package com.mirea.city.presentation.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.mirea.city.data.repository.drawerItems
import com.mirea.city.data.model.CityPhoto
import com.mirea.city.navigation.Screen
import com.mirea.city.presentation.ui.components.NavigationRailComponent
import com.mirea.city.presentation.utils.CityNavigationType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesHomeScreen(
    navigationType: CityNavigationType,
    onCityClick: (String, Int) -> Unit,
    onFavoritesClick: () -> Unit,
    onMenuClick: () -> Unit,
    currentRoute: String = Screen.Home.route,
    onNavigationItemClick: (String) -> Unit = { },
    selectedCityId: String? = null,
    onCitySelected: (String?) -> Unit = { }
) {
    when (navigationType) {
        CityNavigationType.DRAWER_NAVIGATION -> {
            CitiesHomeCompactScreen(
                onCityClick = onCityClick,
                onFavoritesClick = onFavoritesClick,
                onMenuClick = onMenuClick
            )
        }
        CityNavigationType.NAVIGATION_RAIL -> {
            CitiesHomeMediumScreen(
                onCityClick = onCityClick,
                onFavoritesClick = onFavoritesClick,
                onMenuClick = onMenuClick,
                currentRoute = currentRoute,
                onNavigationItemClick = onNavigationItemClick
            )
        }
        CityNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            CitiesHomeExpandedScreen(
                onCityClick = onCityClick,
                onFavoritesClick = onFavoritesClick,
                selectedCityId = selectedCityId,
                onCitySelected = onCitySelected
            )
        }
    }
}

@Composable
fun CityImage(
    @DrawableRes cityImage: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .padding(dimensionResource(R.dimen.small))
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        painter = painterResource(cityImage),
        contentDescription = null
    )
}

@Composable
fun CityTitle(
    @StringRes cityTitle: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            stringResource(cityTitle),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 20.sp,
            modifier = Modifier.padding(dimensionResource(R.dimen.small))
        )
    }
}

@Composable
fun CitySubTitle(
    @StringRes citySubTitle: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            stringResource(citySubTitle),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            modifier = Modifier.padding(dimensionResource(R.dimen.small))
        )
    }
}

@Composable
fun CityItem(
    citiesPhoto: CityPhoto,
    onCityClick: (String, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .clickable{
                onCityClick(citiesPhoto.cityId, citiesPhoto.title)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary) // onPrimary
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CityImage(
                citiesPhoto.imageResId,
                modifier = Modifier
                .weight(0.4f)
                .clip(RoundedCornerShape(12.dp)),

            )
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = 8.dp)
            ) {
                CityTitle(citiesPhoto.title)
                CitySubTitle(citiesPhoto.subTitle)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesHomeCompactScreen(
    onCityClick: (String, Int) -> Unit,
    onFavoritesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
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
                            scope.launch {
                                drawerState.close()
                            }
                            when (item.route) {
                                "home" -> { }
                                "favorites" -> onFavoritesClick()
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
    ) {
        Scaffold(
            topBar = {
                CityTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    showSearchIcon = true,
                    onSearchClick = {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Search functionality Coming Soon!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = innerPadding
            ) {
                items(citiesPhoto) { city ->
                    CityItem(
                        citiesPhoto = city,
                        onCityClick = onCityClick
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
fun CitiesHomeMediumScreen(
    onCityClick: (String, Int) -> Unit,
    onFavoritesClick: () -> Unit,
    onMenuClick: () -> Unit,
    currentRoute: String,
    onNavigationItemClick: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRailComponent(
            currentRoute = currentRoute,
            onItemClick = { route ->
                when (route) {
                    "home" -> { }
                    "favorites" -> onFavoritesClick()
                }
                onNavigationItemClick(route)
            },
            modifier = Modifier.fillMaxHeight()
        )

        Surface(modifier = Modifier.weight(1f)) {
            Scaffold(
                topBar = {
                    CityTopAppBar(
                        scrollBehavior = scrollBehavior,
                        onMenuClick = onMenuClick,
                        showSearchIcon = true,
                        showMenuIcon = false,
                        onSearchClick = {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    message = "Search functionality Coming Soon!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    items(citiesPhoto) { city ->
                        CityItem(
                            citiesPhoto = city,
                            onCityClick = onCityClick
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CitiesHomeExpandedScreen(
    onCityClick: (String, Int) -> Unit,
    onFavoritesClick: () -> Unit,
    selectedCityId: String?,
    onCitySelected: (String?) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(citiesPhoto) { city ->
            CityItem(
                citiesPhoto = city,
                onCityClick = { cityId, cityNameRes ->
                    if (selectedCityId == city.cityId) {
                        onCitySelected(null)
                    } else {
                        onCityClick(city.cityId, city.title)
                        onCitySelected(city.cityId)
                    }
                }
            )
        }
    }
}