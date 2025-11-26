package com.mirea.city.data.model

import androidx.annotation.StringRes

data class PlaceDetails(
    val placeId: String,
    @StringRes val addressId: Int,
    @StringRes val descId: Int
)