package com.mirea.city.data.model

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

@SuppressLint("SupportAnnotationUsage")
data class MenuItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
)
