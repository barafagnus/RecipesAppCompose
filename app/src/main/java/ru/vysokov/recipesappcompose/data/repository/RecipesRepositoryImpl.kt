package ru.vysokov.recipesappcompose.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vysokov.recipesappcompose.core.network.api.RecipesApiService
import ru.vysokov.recipesappcompose.data.database.RecipesDatabase
import ru.vysokov.recipesappcompose.data.database.entity.toCategoryDto
import ru.vysokov.recipesappcompose.data.database.entity.toRecipeDto
import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto
import ru.vysokov.recipesappcompose.data.model.toEntity
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesApiService: RecipesApiService,
    database: RecipesDatabase
) : RecipesRepository {
    private val categoryDao = database.categoryDao()
    private val recipeDao = database.recipeDao()

    override fun getCategories(): Flow<List<CategoryDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = recipesApiService.getCategories()
                categoryDao.insertCategories(categories = categories.map { it.toEntity() })
            } catch (e: Exception) {
                Log.e("Network", "Error load categories: ${e.message}")
            }
        }

        return categoryDao.getAllCategories()
            .map { entities ->
                entities.map { it.toCategoryDto() }
            }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recipes = recipesApiService.getRecipesByCategory(categoryId)
                recipeDao.insertRecipes(recipes = recipes.map { it.toEntity(categoryId) })
            } catch (e: Exception) {
                Log.e("Network", "Error load recipes, categoryId=$categoryId: ${e.message}")
            }
        }

        return recipeDao.getRecipesByCategory(categoryId)
            .map { entities ->
                entities.map { it.toRecipeDto() }
            }
    }

    override fun getRecipe(recipeId: Int): Flow<RecipeDto?> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recipe = recipesApiService.getRecipe(recipeId = recipeId)
                val categoryId = recipeDao.getRecipeById(recipeId).first()?.categoryId ?: 0

                recipeDao.insertRecipe(recipe.toEntity(categoryId))
                Log.d("Network", "Детали рецепта получены из API")
            } catch (e: Exception) {
                Log.e("Network", "Error load recipe, recipeId=$recipeId: ${e.message}")
            }
        }

        return recipeDao.getRecipeById(recipeId)
            .map { entity -> entity?.toRecipeDto() }
    }
}