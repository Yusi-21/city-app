package com.mirea.city.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CityPhoto(
    @DrawableRes val imageResId: Int,
    @StringRes val title: Int,
    @StringRes val subTitle: Int,
    val cityId: String
)
