package ru.vysokov.recipesappcompose.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ru.vysokov.recipesappcompose.core.Constants

class FavoritesPrefsManager(
    context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.FAVORITES_PREFS_KEY, Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean {
        val currentFavorites =
            sharedPreferences.getStringSet(Constants.FAVORITES_PREFS_KEY, emptySet())
        return currentFavorites?.contains(recipeId.toString()) ?: false
    }

    fun addToFavorites(recipeId: Int) {
        val favorites = getFavoritesSet()
        favorites.add(recipeId.toString())
        saveFavorites(favorites)
    }

    fun removeFromFavorites(recipeId: Int) {
        val favorites = getFavoritesSet()
        favorites.remove(recipeId.toString())
        saveFavorites(favorites)
    }

    fun getAllFavorites(): Set<String> =
        sharedPreferences.getStringSet(Constants.FAVORITES_PREFS_KEY, null)?.toSet() ?: emptySet()

    private fun getFavoritesSet(): MutableSet<String> {
        val favoritesSet = sharedPreferences.getStringSet(Constants.FAVORITES_PREFS_KEY, emptySet())
        return favoritesSet?.toMutableSet() ?: mutableSetOf()
    }

    private fun saveFavorites(set: Set<String>) {
        sharedPreferences.edit {
            putStringSet(Constants.FAVORITES_PREFS_KEY, set)
        }
    }
}