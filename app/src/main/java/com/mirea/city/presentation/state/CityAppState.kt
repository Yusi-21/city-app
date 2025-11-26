package com.mirea.city.presentation.state

import com.mirea.city.presentation.utils.CityContentType
import com.mirea.city.presentation.utils.CityNavigationType

data class CityAppState(
    val navigationType: CityNavigationType,
    val contentType: CityContentType,
    val isShowingHomePage: Boolean = true,
    val isSelectedCityId: String? = null,
    val isSelectedPlaceId: String? = null
)