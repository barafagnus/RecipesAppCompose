package ru.vysokov.recipesappcompose.features.recipes.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.data.repository.RecipesRepository
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipesUiState
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.toUiModel
import java.net.URLDecoder

class RecipesViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository
) : ViewModel() {
    val categoryId: Int = savedStateHandle[Constants.KEY_CATEGORY_ID]
        ?: error("Category ID is required.")

    val categoryTitle: String = savedStateHandle.get<String>(Constants.KEY_CATEGORY_TITLE)
        ?.let { URLDecoder.decode(it, "UTF-8") }
        ?: error("categoryTitle is required.")

    val categoryImageUrl: String = savedStateHandle.get<String>(Constants.KEY_CATEGORY_IMAGE_URL)
        ?.let { URLDecoder.decode(it, "UTF-8") }
        ?: error("categoryImageUrl is required.")

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                repository.getRecipesByCategory(categoryId)
                    .map { dtos -> dtos.map { it.toUiModel() } }
                    .collect { recipes ->
                        _uiState.update { state ->
                            state.copy(
                                recipes = recipes,
                                categoryId = categoryId,
                                categoryTitle = categoryTitle,
                                categoryImageUrl = categoryImageUrl,
                                isLoading = false
                            )
                        }
                    }


            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

}