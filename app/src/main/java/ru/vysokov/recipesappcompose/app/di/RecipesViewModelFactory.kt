package ru.vysokov.recipesappcompose.app.di

import androidx.lifecycle.SavedStateHandle
import ru.vysokov.recipesappcompose.data.repository.RecipesRepository
import ru.vysokov.recipesappcompose.features.recipes.presentation.RecipesViewModel

class RecipesViewModelFactory(
    private val repository: RecipesRepository,
    private val savedStateHandle: SavedStateHandle
) : Factory<RecipesViewModel> {

    override fun create(): RecipesViewModel {
        return RecipesViewModel(savedStateHandle, repository)
    }
}