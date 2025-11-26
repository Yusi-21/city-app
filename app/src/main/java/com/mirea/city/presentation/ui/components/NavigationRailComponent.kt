package com.mirea.city.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirea.city.data.repository.drawerItems

@Composable
fun NavigationRailComponent(
    currentRoute: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier
) {
    NavigationRail(
        modifier = modifier.width(100.dp).padding(16.dp)
    ) {
        drawerItems.forEach { item ->
            NavigationRailItem(
                selected = currentRoute == item.route,
                onClick = { onItemClick(item.route) },
                icon = {
                    when (item.route) {
                        "home" -> Icon(Icons.Outlined.Place, contentDescription = null)
                        "favorites" -> Icon(Icons.Default.Favorite, contentDescription = null)
                        else -> Icon(Icons.Outlined.Place, contentDescription = null)
                    }
                }
            )
        }
    }
}