package ru.vysokov.recipesappcompose.features.recipes.presentation.model

data class RecipesUiState(
    val recipes: List<RecipeUiModel> = emptyList(),
    val categoryId: Int = 0,
    val categoryTitle: String = "",
    val categoryImageUrl: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)