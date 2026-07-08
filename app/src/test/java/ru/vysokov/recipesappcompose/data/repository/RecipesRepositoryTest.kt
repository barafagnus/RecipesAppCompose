package ru.vysokov.recipesappcompose.data.repository

import app.cash.turbine.test
import fixtures.CategoryTestFixtures
import fixtures.RecipeTestFixtures
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.vysokov.recipesappcompose.core.network.api.RecipesApiService
import ru.vysokov.recipesappcompose.data.database.RecipesDatabase
import ru.vysokov.recipesappcompose.data.database.dao.CategoryDao
import ru.vysokov.recipesappcompose.data.database.dao.RecipeDao
import ru.vysokov.recipesappcompose.data.model.toEntity

class RecipesRepositoryTest {
    private val apiService = mockk<RecipesApiService>()
    private val database = mockk<RecipesDatabase>(relaxed = true)
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
        val count = 3
        val categoriesFixtures = CategoryTestFixtures.createCategoryDtoList(count = count)

        every { categoryDao.getAllCategories() } returns flowOf(
            categoriesFixtures.map { it.toEntity() }
        )

        coEvery { apiService.getCategories() } returns emptyList()
        coEvery { categoryDao.insertCategories(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()

            assertEquals(count, categories.size)
            assertEquals(categoriesFixtures[0].title, categories[0].title)
            assertEquals(categoriesFixtures[0].description, categories[0].description)
            assertEquals(categoriesFixtures[0].imageUrl, categories[0].imageUrl)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCategories still emits data when api throws exception`() = runTest {
        val count = 3
        val categoriesFixtures = CategoryTestFixtures.createCategoryDtoList(count = count)

        every { categoryDao.getAllCategories() } returns flowOf(
            categoriesFixtures.map { it.toEntity() }
        )

        coEvery { apiService.getCategories() } throws IOException("IO Exception")
        coEvery { categoryDao.insertCategories(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()

            assertEquals(count, categories.size)
            assertEquals(categoriesFixtures[0].title, categories[0].title)
            assertEquals(categoriesFixtures[0].description, categories[0].description)
            assertEquals(categoriesFixtures[0].imageUrl, categories[0].imageUrl)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 0) { categoryDao.insertCategories(any()) }
    }

    @Test
    fun `getRecipesByCategory returns flow filtered by categoryId`() = runTest {
        val categoryId = 1
        val recipe = RecipeTestFixtures.createRecipeDto()

        every { recipeDao.getRecipesByCategory(categoryId) } returns flowOf(
            listOf(recipe.toEntity(categoryId))
        )

        coEvery { apiService.getRecipesByCategory(categoryId) } returns emptyList()

        repository.getRecipesByCategory(categoryId).test {
            val recipes = awaitItem()

            assertEquals(1, recipes.size)
            assertEquals(recipe.id, recipes[0].id)
            assertEquals(recipe.title, recipes[0].title)
            assertEquals(recipe.imageUrl, recipes[0].imageUrl)
            assertEquals(recipe.ingredients, recipes[0].ingredients)
            assertEquals(recipe.method, recipes[0].method)

            cancelAndIgnoreRemainingEvents()
        }
    }
}