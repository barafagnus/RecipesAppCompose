package ru.vysokov.recipesappcompose.core.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto

interface RecipesApiService {
    @GET("category")
    suspend fun getCategories(): List<CategoryDto>

    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategory(@Path("id") categoryId: Int): List<RecipeDto>

    @GET("recipe/{id}")
    suspend fun getRecipe(@Path("id") recipeId: Int): RecipeDto
}