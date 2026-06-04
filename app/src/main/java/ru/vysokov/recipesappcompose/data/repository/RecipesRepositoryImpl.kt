package ru.vysokov.recipesappcompose.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vysokov.recipesappcompose.core.network.api.RecipesApiService
import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto

class RecipesRepositoryImpl(
    private val recipesApiService: RecipesApiService
) : RecipesRepository {
    override suspend fun getCategories(): List<CategoryDto> {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getCategories()
            } catch (e: Exception) {
                Log.e("Network", "Error load categories: ${e.message}")
                emptyList()
            }
        }
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getRecipesByCategory(categoryId = categoryId)
            } catch (e: Exception) {
                Log.e("Network", "Error load recipes, categoryId=$categoryId: ${e.message}")
                emptyList()
            }
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getRecipe(recipeId = recipeId)
            } catch (e: Exception) {
                Log.e("Network", "Error load recipe, recipeId=$recipeId: ${e.message}")
                throw e
            }
        }
    }
}