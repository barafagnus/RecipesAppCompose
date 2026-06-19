package ru.vysokov.recipesappcompose.app.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ru.vysokov.recipesappcompose.data.repository.RecipesRepository
import ru.vysokov.recipesappcompose.features.details.presentation.RecipeDetailsViewModel

class RecipeDetailsViewModelFactory(
    private val repository: RecipesRepository,
    private val application: Application,
    private val savedStateHandle: SavedStateHandle
) : Factory<RecipeDetailsViewModel> {

    override fun create(): RecipeDetailsViewModel {
        return RecipeDetailsViewModel(application, repository, savedStateHandle)
    }
}