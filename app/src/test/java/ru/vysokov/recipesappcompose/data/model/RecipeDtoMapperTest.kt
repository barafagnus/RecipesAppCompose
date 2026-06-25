package ru.vysokov.recipesappcompose.data.model

import fixtures.RecipeTestFixtures
import junit.framework.TestCase.assertEquals
import org.junit.Test
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.IngredientsUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.toUiModel

class RecipeDtoMapperTest {
    @Test
    fun `maps DTO to UI model correctly`() {
        val dto = RecipeTestFixtures.createRecipeDto()
        val result = dto.toUiModel()

        assertEquals(1, result.id)
        assertEquals("Паста карбонара", result.title)
        assertEquals(
            listOf(
                IngredientsUiModel(
                    amount = "200",
                    unitOfMeasure = "г",
                    name = "Паста"
                )
            ), result.ingredients
        )
        assertEquals(listOf("Отварить", "Смешать"), result.method)
        assertEquals("${Constants.IMAGES_BASE_URL}pasta.jpg", result.imageUrl)
    }

    @Test
    fun `prepends base url to relative imageUrl`() {
        val dto = RecipeTestFixtures.createRecipeDto()
        val result = dto.toUiModel()

        assertEquals("${Constants.IMAGES_BASE_URL}pasta.jpg", result.imageUrl)
    }

    @Test
    fun `preserves full imageUrl starting with http`() {
        val dto = RecipeTestFixtures.createRecipeDto(
            imageUrl = "http://example.com/images/pasta.jpg"
        )
        val result = dto.toUiModel()

        assertEquals("http://example.com/images/pasta.jpg", result.imageUrl)
    }
}