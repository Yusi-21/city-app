package com.mirea.city.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirea.city.presentation.state.CityAppState
import com.mirea.city.data.model.PlacePhoto
import com.mirea.city.presentation.utils.CityContentType
import com.mirea.city.presentation.utils.CityNavigationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityAppViewModel : ViewModel() {
    private val _appState = MutableStateFlow(
        CityAppState(
            navigationType = CityNavigationType.DRAWER_NAVIGATION,
            contentType = CityContentType.LIST_ONLY
        )
    )

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    private val _favoriteItems = MutableStateFlow<List<PlacePhoto>>(emptyList())

    val appState: StateFlow<CityAppState> = _appState.asStateFlow()
    val favoriteItems: StateFlow<List<PlacePhoto>> = _favoriteItems.asStateFlow()

    companion object {
        fun preview() : CityAppViewModel {
            return CityAppViewModel()
        }
    }

    fun updateNavigationType(navigationType: CityNavigationType) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(
                navigationType = navigationType,
                contentType = if (navigationType == CityNavigationType.PERMANENT_NAVIGATION_DRAWER) {
                    CityContentType.LIST_AND_DETAILS
                } else {
                    CityContentType.LIST_ONLY
                }
            )
        }
    }

    fun selectCity(cityId: String?) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(
                isSelectedCityId = cityId,
                isShowingHomePage = cityId == null
            )
        }
    }

    fun selectPlace(placeId: String?) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(isSelectedPlaceId = placeId)
        }
    }

    fun clearSelection() {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(
                isSelectedCityId = null,
                isSelectedPlaceId = null
            )
        }
    }

    fun addToFavorites(place: PlacePhoto) {
        viewModelScope.launch {
            if (!_favorites.value.contains(place.placeId)) {
                val newFavorites = _favorites.value.toMutableSet().apply {
                    add(place.placeId)
                }

                val newItems = _favoriteItems.value.toMutableList().apply {
                    if (none { it.placeId == place.placeId }) {
                        add(place)
                    }
                }

                _favorites.value = newFavorites
                _favoriteItems.value = newItems

            }
        }
    }

    fun removeFromFavorites(placeId: String) {
        viewModelScope.launch {
            val newFavorites = _favorites.value.toMutableSet().apply {
                remove(placeId)
            }

            val newItems = _favoriteItems.value.toMutableList().apply {
                removeAll { it.placeId == placeId }
            }

            _favorites.value = newFavorites
            _favoriteItems.value = newItems

        }
    }

    fun isFavorite(placeId: String): Boolean {
        return _favorites.value.contains(placeId)
    }
}