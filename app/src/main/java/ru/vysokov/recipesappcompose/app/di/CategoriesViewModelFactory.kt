package ru.vysokov.recipesappcompose.app.di

import ru.vysokov.recipesappcompose.data.repository.RecipesRepository
import ru.vysokov.recipesappcompose.features.categories.presentation.CategoriesViewModel

class CategoriesViewModelFactory(
    private val repository: RecipesRepository
) : Factory<CategoriesViewModel> {

    override fun create(): CategoriesViewModel {
        return CategoriesViewModel(repository)
    }
}