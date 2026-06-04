package ru.vysokov.recipesappcompose.data.repository

import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto

interface RecipesRepository {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto>
    suspend fun getRecipe(recipeId: Int): RecipeDto
}