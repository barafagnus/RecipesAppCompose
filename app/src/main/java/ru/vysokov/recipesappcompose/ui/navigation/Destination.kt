package ru.vysokov.recipesappcompose.ui.navigation

import android.net.Uri

sealed class Destination(val route: String) {
    object Categories: Destination("categories")
    object Recipes: Destination("recipes/{categoryId}/{categoryTitle}") {
        fun createRoute(categoryId: Int, categoryTitle: String) = "recipes/$categoryId/${
            Uri.encode(categoryTitle)}"
    }
    object RecipeDetails: Destination("recipe/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
    object Favorites: Destination("favorites")
}