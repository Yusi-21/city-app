package com.mirea.city


import com.mirea.city.data.repository.citiesPhoto
import com.mirea.city.data.repository.getDetailsByPlaceId
import com.mirea.city.data.repository.getPlacesByCityId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CitiesDataTest {

    @Test
    fun `citiesPhoto should return 16 cities`() {
        // When - получаем список городов
        val cities = citiesPhoto

        // Then - проверяем что их 16
        assertEquals(16, cities.size)
    }

    @Test
    fun `citiesPhoto should contain specific cities`() {
        // When - получаем список городов
        val cities = citiesPhoto

        // Then - проверяем что есть конкретные города
        val cityIds = cities.map { it.cityId }
        assert(cityIds.contains("moscow"))
        assert(cityIds.contains("london"))
        assert(cityIds.contains("paris"))
    }

    @Test
    fun `getPlacesByCityId returns correct places for moscow`() {
        // When - получаем места для Москвы
        val moscowPlaces = getPlacesByCityId("moscow")

        // Then - проверяем что есть 4 места и они правильные
        assertEquals(4, moscowPlaces.size)

        val placeIds = moscowPlaces.map { it.placeId }
        assertTrue("Should contain Red Square", placeIds.contains("red_square"))
        assertTrue("Should contain Moscow Kremlin", placeIds.contains("moscow_kremlin"))
    }

    @Test
    fun `getPlacesByCityId returns empty list for unknown city`() {
        // When - получаем места для неизвестного города
        val unknownPlaces = getPlacesByCityId("unknown_city")

        // Then - должен вернуться пустой список
        assertTrue(unknownPlaces.isEmpty())
    }

    @Test
    fun `getDetailsByPlaceId returns correct details for red_square`() {
        // When - получаем детали для Красной площади
        val redSquareDetails = getDetailsByPlaceId("red_square")

        // Then - проверяем что детали не null
        assertNotNull(redSquareDetails)
        assertEquals("red_square", redSquareDetails?.placeId)
    }

    @Test
    fun `getDetailsByPlaceId returns null for unknown place`() {
        // When - получаем детали для неизвестного места
        val unknownDetails = getDetailsByPlaceId("unknown_place")

        // Then - должен вернуться null
        assertNull(unknownDetails)
    }
}