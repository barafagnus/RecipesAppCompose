package ru.vysokov.recipesappcompose.data.repository

import kotlinx.coroutines.flow.Flow
import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto

interface RecipesRepository {
    fun getCategories(): Flow<List<CategoryDto>>
    fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>>
    fun getRecipe(recipeId: Int): Flow<RecipeDto?>
}