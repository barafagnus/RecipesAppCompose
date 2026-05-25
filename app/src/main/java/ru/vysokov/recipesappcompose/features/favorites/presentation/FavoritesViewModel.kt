package ru.vysokov.recipesappcompose.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.vysokov.recipesappcompose.core.utils.FavoriteDataStoreManager
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.features.favorites.presentation.model.FavoritesUiState
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.toUiModel

class FavoritesViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<FavoritesUiState> = favoriteManager
        .getFavoritesIdsFlow()
        .flatMapLatest { ids ->
            loadFavoriteRecipes(ids)

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState()
        )

    private fun loadFavoriteRecipes(ids: Set<String>): Flow<FavoritesUiState> = flow {
        emit(FavoritesUiState(isLoading = true))

        try {
            val recipes = ids.mapNotNull {
                val id = it.toIntOrNull()
                RecipesRepositoryStub.getRecipeById(id)?.toUiModel()
            }
            emit(
                FavoritesUiState(
                    favoriteRecipes = recipes,
                    isLoading = false,
                    errorMessage = null
                )
            )
        } catch (e: Exception) {
            emit(
                FavoritesUiState(
                    favoriteRecipes = emptyList(),
                    isLoading = false,
                    errorMessage = e.message
                )
            )
        }
    }
}