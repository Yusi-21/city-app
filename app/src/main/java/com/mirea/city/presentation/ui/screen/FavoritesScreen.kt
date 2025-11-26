package com.mirea.city.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mirea.city.CityTopAppBar
import com.mirea.city.data.model.PlacePhoto
import com.mirea.city.presentation.ui.components.FavoriteCarousel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mirea.city.R
import com.mirea.city.presentation.viewmodel.CityAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    cityAppViewModel: CityAppViewModel,
    onBackClick: () -> Unit,
    onFavoriteClick: (PlacePhoto) -> Unit
) {
    val favorites by cityAppViewModel.favoriteItems.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CityTopAppBar(
                onMenuClick = onBackClick,
                showBackButton = true,
                showSearchIcon = false,
                showShareIcon = false,
                title = {
                    Text(
                        text = "Favorites (${favorites.size})",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_favorites_yet),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                FavoriteCarousel(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 4.dp),
                    favorites = favorites,
                    onItemClick = onFavoriteClick
                )
            }
        }
    }
}