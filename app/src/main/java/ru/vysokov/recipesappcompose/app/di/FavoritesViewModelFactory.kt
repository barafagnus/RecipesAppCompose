package ru.vysokov.recipesappcompose.app.di

import android.app.Application
import ru.vysokov.recipesappcompose.features.favorites.presentation.FavoritesViewModel

class FavoritesViewModelFactory(
    private val application: Application
) : Factory<FavoritesViewModel> {

    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(application)
    }
}