package ru.vysokov.recipesappcompose.data.model

import fixtures.CategoryTestFixtures
import junit.framework.TestCase.assertEquals
import org.junit.Test
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.features.categories.presentation.model.toUiModel

class CategoryDtoTest {
    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryTestFixtures.createCategoryDto()
        val result = dto.toUiModel()

        assertEquals(1, result.id)
        assertEquals("Бургеры", result.title)
        assertEquals("Всякие там бургерсы", result.description)
        assertEquals("${Constants.IMAGES_BASE_URL}burger.jpg", result.imageUrl)
    }

    @Test
    fun `mapper maps empty title correctly`() {
        val dto = CategoryTestFixtures.createCategoryDto(title = "")
        val result = dto.toUiModel()

        assertEquals("", result.title)
    }

    @Test
    fun `mapper preserves very long description`() {
        val dto = CategoryTestFixtures.createCategoryDto(description = "Всякие там бургерсы".repeat(1000))

        val result = dto.toUiModel()
        assertEquals("Всякие там бургерсы".repeat(1000), result.description)
    }
}