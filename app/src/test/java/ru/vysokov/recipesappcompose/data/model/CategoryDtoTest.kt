package ru.vysokov.recipesappcompose.data.model

import junit.framework.TestCase.assertEquals
import org.junit.Test
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.features.categories.presentation.model.toUiModel

//В CategoryDtoTest проверить CategoryDto.toUiModel():
//mapper maps empty title correctly
//mapper preserves very long description

class CategoryDtoTest {
    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryDto(
            id = 1,
            title = "Завтраки",
            description = "Утренние блюда",
            imageUrl = "breakfast.jpg"
        )

        val result = dto.toUiModel()
        assertEquals(1, result.id)
        assertEquals("Завтраки", result.title)
        assertEquals("Утренние блюда", result.description)
        assertEquals("${Constants.IMAGES_BASE_URL}breakfast.jpg", result.imageUrl)
    }

    @Test
    fun `mapper maps empty title correctly`() {
        val dto = CategoryDto(
            id = 1,
            title = "",
            description = "Утренние блюда",
            imageUrl = "breakfast.jpg"
        )

        val result = dto.toUiModel()
        assertEquals("", result.title)
    }

    @Test
    fun `mapper preserves very long description`() {
        val dto = CategoryDto(
            id = 1,
            title = "Завтраки",
            description = "Утренние блюда".repeat(1000),
            imageUrl = "breakfast.jpg"
        )

        val result = dto.toUiModel()
        assertEquals("Утренние блюда".repeat(1000), result.description)
    }
}