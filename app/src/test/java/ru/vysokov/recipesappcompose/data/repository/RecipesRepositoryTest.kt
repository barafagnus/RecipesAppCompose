package ru.vysokov.recipesappcompose.data.repository

import android.database.sqlite.SQLiteException
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.vysokov.recipesappcompose.core.network.api.RecipesApiService
import ru.vysokov.recipesappcompose.data.database.RecipesDatabase
import ru.vysokov.recipesappcompose.data.database.dao.CategoryDao
import ru.vysokov.recipesappcompose.data.database.dao.RecipeDao
import ru.vysokov.recipesappcompose.data.database.entity.CategoryEntity
import ru.vysokov.recipesappcompose.data.database.entity.RecipeEntity
import ru.vysokov.recipesappcompose.data.model.IngredientDto

class RecipesRepositoryTest {
    private val apiService = mockk<RecipesApiService>()
    private val database = mockk<RecipesDatabase>()
    private val categoryDao = mockk<CategoryDao>()
    private val recipeDao = mockk<RecipeDao>()

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setup() {
        every { database.categoryDao() } returns categoryDao
        every { database.recipeDao() } returns recipeDao
        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCategories emits categories from database`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(
                CategoryEntity(
                    id = 1,
                    name = "Завтраки",
                    description = "Утренние блюда",
                    imageUrl = "breakfast.jpg"
                )
            )
        )

        coEvery { apiService.getCategories() } returns emptyList()
        coEvery { categoryDao.insertCategories(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()

            assertEquals(1, categories.size)
            assertEquals("Завтраки", categories[0].title)
            assertEquals("Утренние блюда", categories[0].description)
            assertEquals("breakfast.jpg", categories[0].imageUrl)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCategories still emits data when api throws exception`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(
                CategoryEntity(
                    id = 1,
                    name = "Завтраки",
                    description = "Утренние блюда",
                    imageUrl = "breakfast.jpg"
                )
            )
        )

        coEvery { apiService.getCategories() } throws SQLiteException("Network error")
        coEvery { categoryDao.insertCategories(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()

            assertEquals(1, categories.size)
            assertEquals("Завтраки", categories[0].title)
            assertEquals("Утренние блюда", categories[0].description)
            assertEquals("breakfast.jpg", categories[0].imageUrl)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 0) { categoryDao.insertCategories(any()) }
    }

    @Test
    fun `getRecipesByCategory returns flow filtered by categoryId`() = runTest {
        val categoryId = 1
        val recipe = RecipeEntity(
            id = 1,
            title = "Чизбургер с беконом",
            categoryId = 1,
            imageUrl = "cheeseburger.jpg",
            ingredients = listOf(
                IngredientDto("0.4", "кг", "говяжий фарш"),
                IngredientDto("4.0", "шт", "ломтика бекона")
            ),
            method = listOf(
                "Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце.",
                "Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты.",
            )
        )

        every { recipeDao.getRecipesByCategory(categoryId) } returns flowOf(
            listOf(recipe)
        )

        coEvery { apiService.getRecipesByCategory(categoryId) } returns emptyList()

        repository.getRecipesByCategory(categoryId).test {
            val recipes = awaitItem()

            assertEquals(1, recipes.size)
            assertEquals(1, recipes[0].id)
            assertEquals("Чизбургер с беконом", recipes[0].title)
            assertEquals("cheeseburger.jpg", recipes[0].imageUrl)
            assertEquals(
                listOf(
                    IngredientDto("0.4", "кг", "говяжий фарш"),
                    IngredientDto("4.0", "шт", "ломтика бекона")
                ), recipes[0].ingredients
            )
            assertEquals(
                listOf(
                    "Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце.",
                    "Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты.",
                ), recipes[0].method
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}