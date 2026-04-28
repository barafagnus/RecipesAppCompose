package ru.vysokov.recipesappcompose.core

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val KEY_RECIPE_OBJECT = "recipe"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"

    fun createRecipeDeepLink(recipeId: Int) = "$DEEP_LINK_BASE_URL/recipe/$recipeId"
}