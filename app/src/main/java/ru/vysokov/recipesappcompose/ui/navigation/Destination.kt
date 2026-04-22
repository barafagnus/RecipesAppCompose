package ru.vysokov.recipesappcompose.ui.navigation

sealed class Destination(val route: String) {
    object Categories: Destination("categories")
    object Recipes: Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }
    object RecipeDetails: Destination("recipe/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
    object Favorites: Destination("favorites")
}