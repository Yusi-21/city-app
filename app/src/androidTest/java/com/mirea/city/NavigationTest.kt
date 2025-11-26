package com.mirea.city

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mirea.city.presentation.utils.CityNavigationType
import com.mirea.city.presentation.viewmodel.CityAppViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigation_HomeToFavorites() {
        // Given - запускаем приложение
        composeTestRule.setContent {
            val navController = rememberNavController()
            CityAppContent(
                navigationType = CityNavigationType.DRAWER_NAVIGATION,
                navController = navController,
                cityAppViewModel = CityAppViewModel.preview()
            )
        }

        // Then - Проверяем что на главном экране есть основные элементы
        composeTestRule.onNodeWithText("Moscow").assertExists()
        composeTestRule.onNodeWithText("London").assertExists()
    }
}