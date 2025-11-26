package com.mirea.city

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mirea.city.presentation.ui.screen.CitiesDetailsCompactScreen
import com.mirea.city.presentation.viewmodel.CityAppViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CitiesDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun favoriteButton_togglesFavoriteStatus() {
        // Given - запускаем экран деталей с тестовыми данными
        composeTestRule.setContent {
            CitiesDetailsCompactScreen(
                cityAppViewModel = CityAppViewModel.preview(),
                placeId = "red_square",
                imageResId = com.mirea.city.R.drawable.place_red_square,
                titleResId = com.mirea.city.R.string.place_red_square,
                onBackClick = { }
            )
        }

        // When 1 - находим и нажимаем кнопку "Добавить в избранное"
        composeTestRule.onNodeWithContentDescription("Add this place to favorites")
            .assertExists()
            .performClick()

        // Then 1 - проверяем что кнопка изменилась на "Удалить из избранного"
        composeTestRule.onNodeWithContentDescription("Remove from Favorites")
            .assertExists()

        // When 2 - нажимаем кнопку снова
        composeTestRule.onNodeWithContentDescription("Remove from Favorites")
            .performClick()

        // Then 2 - проверяем что кнопка вернулась в исходное состояние
        composeTestRule.onNodeWithContentDescription("Add this place to favorites")
            .assertExists()
    }
}