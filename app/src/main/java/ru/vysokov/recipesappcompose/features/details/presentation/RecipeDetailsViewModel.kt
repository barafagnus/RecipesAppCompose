package ru.vysokov.recipesappcompose.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.core.utils.FavoriteDataStoreManager
import ru.vysokov.recipesappcompose.data.repository.RecipesRepository
import ru.vysokov.recipesappcompose.features.details.presentation.model.RecipeDetailsUiState
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.IngredientsUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipeUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.toUiModel
import java.math.BigDecimal

class RecipeDetailsViewModel(
    application: Application,
    repository: RecipesRepository,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)
    private val recipeId = savedStateHandle.get<Int>(Constants.KEY_RECIPE_ID) ?: 0

    private val _portions = MutableStateFlow(1)

    private val recipeFlow = flow {
        try {
            val recipe = repository.getRecipe(recipeId).toUiModel()
            emit(recipe)
        } catch (e: Exception) {
            emit(null)
        }
    }

    val uiState: StateFlow<RecipeDetailsUiState> = combine(
        recipeFlow,
        favoriteManager.isFavoriteFlow(recipeId),
        _portions
    ) { recipe, isFavorite, portions ->
        if (recipe != null) {
            RecipeDetailsUiState(
                recipe = recipe,
                isFavorite = isFavorite,
                currentPortions = portions,
                scaledIngredients = getScaledIngredients(recipe, portions),
                isLoading = false
            )
        } else {
            RecipeDetailsUiState(
                errorMessage = "Рецепт не найден.",
                isLoading = false
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = RecipeDetailsUiState(isLoading = true)
    )

    private fun getScaledIngredients(
        recipe: RecipeUiModel,
        currentPortions: Int
    ): List<IngredientsUiModel> {
        return recipe.ingredients.map { ingredients ->
            val amount = ingredients.amount.toBigDecimalOrNull()
            if (amount != null) {
                val calculatedAmount = amount.multiply(BigDecimal(currentPortions))
                ingredients.copy(
                    amount = calculatedAmount
                        .stripTrailingZeros()
                        .toPlainString()
                )
            } else ingredients
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val isFavorite = uiState.value.isFavorite

            if (isFavorite) favoriteManager.removeFavorite(recipeId)
            else favoriteManager.addFavorite(recipeId)
        }
    }

    fun updatePortions(portions: Int) {
        if (portions >= 1) {
            _portions.value = portions
        }
    }
}