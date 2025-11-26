package com.mirea.city

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mirea.city.presentation.ui.screen.CitiesHomeScreen
import com.mirea.city.presentation.utils.CityNavigationType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CitiesHomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun citiesHomeScreen_displaysAllCities() {
        // Given - запускаем главный экран
        composeTestRule.setContent {
            CitiesHomeScreen(
                navigationType = CityNavigationType.DRAWER_NAVIGATION,
                onCityClick = { _, _ -> },
                onFavoritesClick = { },
                onMenuClick = { }
            )
        }

        // Then - проверяем что отображаются основные города
        composeTestRule.onNodeWithText("Moscow").assertExists()
        composeTestRule.onNodeWithText("London").assertExists()
        composeTestRule.onNodeWithText("Paris").assertExists()
        composeTestRule.onNodeWithText("Beijing").assertExists()
        composeTestRule.onNodeWithText("Dubai").assertExists()
    }
}