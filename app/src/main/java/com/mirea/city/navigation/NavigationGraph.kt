package com.mirea.city.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mirea.city.presentation.ui.screen.CitiesDetailsScreen
import com.mirea.city.presentation.ui.screen.CitiesHomeScreen
import com.mirea.city.presentation.ui.screen.CitiesPlacesScreen
import com.mirea.city.presentation.ui.screen.FavoritesScreen
import com.mirea.city.data.model.PlacePhoto
import com.mirea.city.presentation.utils.CityNavigationType
import com.mirea.city.presentation.viewmodel.CityAppViewModel


// sealed class -все возможные случаи описаны в одном месте
sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Places: Screen("places/{cityId}/{cityNameRes}") {
        fun createRoute(cityId: String, cityNameRes: Int) = "places/$cityId/$cityNameRes"
    }

    object Details: Screen("details/{placeId}/{imageResId}/{titleResId}") {
        fun createRoute(placeId: String, imageResId: Int, titleResId: Int) =
            "details/$placeId/$imageResId/$titleResId"
    }

    object Favorites: Screen("favorites")
}

fun NavController.navigateToPlaces(cityId: String, cityNameRes: Int) {
    navigate(Screen.Places.createRoute(cityId, cityNameRes))
}

fun NavController.navigateToDetails(placeId: String, imageResId: Int, titleResId: Int) {
    navigate(Screen.Details.createRoute(placeId, imageResId, titleResId))
}

fun NavController.navigateToFavorites() {
    navigate(Screen.Favorites.route)
}

fun NavController.navigateBack() {
    popBackStack()
}

fun NavGraphBuilder.cityNavigationGraph(
    navController: NavController,
    cityAppViewModel: CityAppViewModel,
    navigationType: CityNavigationType,
    currentRoute: String = Screen.Home.route,
    onNavigationItemClick: (String) -> Unit = { }
) {
    composable(Screen.Home.route) {
        CitiesHomeScreen(
            navigationType = navigationType,
            onCityClick = { cityId, cityNameRes ->
                navController.navigateToPlaces(cityId, cityNameRes)
            },
            onFavoritesClick = { navController.navigateToFavorites() },
            onMenuClick = { },
            currentRoute = currentRoute,
            onNavigationItemClick = onNavigationItemClick
        )
    }

    composable(
        Screen.Places.route,
        arguments = listOf(
            navArgument("cityId") { type = NavType.StringType },
            navArgument("cityNameRes") { type = NavType.IntType }

        )
    ) {backStackEntry ->
        val cityId = backStackEntry.arguments?.getString("cityId") ?: ""
        val cityNameRes = backStackEntry.arguments?.getInt("cityNameRes") ?: 0

        CitiesPlacesScreen(
            navigationType = navigationType,
            cityId = cityId,
            cityNameRes = cityNameRes,
            onPlaceClick = { place: PlacePhoto ->
                navController.navigateToDetails(
                    placeId = place.placeId,
                    imageResId = place.placesImageResId,
                    titleResId = place.placesTitleId
                )

            },
            onBackClick = {
                navController.navigateBack()
            },
            currentRoute = currentRoute,
            onNavigationItemClick = onNavigationItemClick
        )
    }

    composable(
        Screen.Details.route,
        arguments = listOf(
            navArgument("placeId")    { type = NavType.StringType },
            navArgument("imageResId") { type = NavType.IntType },
            navArgument("titleResId") { type = NavType.IntType },
        )
    ) { backStackEntry ->
        val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
        val imageResId = backStackEntry.arguments?.getInt("imageResId") ?: 0
        val titleResId = backStackEntry.arguments?.getInt("titleResId") ?: 0

        CitiesDetailsScreen(
            navigationType = navigationType,
            cityAppViewModel = cityAppViewModel,
            placeId = placeId,
            imageResId = imageResId,
            titleResId = titleResId,
            onBackClick = { navController.navigateBack() },
            currentRoute = currentRoute,
            onNavigationItemClick = onNavigationItemClick
        )
    }

    composable(Screen.Favorites.route) {
        FavoritesScreen(
            cityAppViewModel = cityAppViewModel,
            onBackClick = {
                navController.navigateBack()
            },
            onFavoriteClick = { place ->
                navController.navigateToDetails(
                    placeId = place.placeId,
                    imageResId = place.placesImageResId,
                    titleResId = place.placesTitleId
                )
            }
        )
    }
}