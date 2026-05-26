package ru.vysokov.recipesappcompose.features.details.presentation.model

import ru.vysokov.recipesappcompose.features.recipes.presentation.model.IngredientsUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipeUiModel

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val isFavorite: Boolean = false,
    val currentPortions: Int = 1,
    val scaledIngredients: List<IngredientsUiModel>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
