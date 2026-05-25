package ru.vysokov.recipesappcompose.features.favorites.presentation.model

import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipeUiModel

data class FavoritesUiState(
    val favoriteRecipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
