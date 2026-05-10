package ru.vysokov.recipesappcompose.core

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val KEY_RECIPE_OBJECT = "recipe"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
    const val FAVORITES_PREFS_KEY = "favorite_recipe_ids"
    const val FAVORITES_PREFS_NAME = "favorites_prefs"
    const val DATASTORE_PREFS = "recipe_app_prefs"

    fun createRecipeDeepLink(recipeId: Int) = "$DEEP_LINK_BASE_URL/recipe/$recipeId"
}