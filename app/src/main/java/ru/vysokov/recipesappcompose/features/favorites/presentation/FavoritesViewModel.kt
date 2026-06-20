package ru.vysokov.recipesappcompose.features.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesManager: FavoriteDataStoreManager
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<FavoritesUiState> = favoritesManager
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