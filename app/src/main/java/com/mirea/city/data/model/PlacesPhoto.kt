package com.mirea.city.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PlacePhoto(
    @DrawableRes val placesImageResId: Int,
    @StringRes val placesTitleId: Int,
    val placeId: String
)