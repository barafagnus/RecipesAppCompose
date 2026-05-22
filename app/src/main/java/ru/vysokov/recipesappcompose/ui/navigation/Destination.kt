package ru.vysokov.recipesappcompose.ui.navigation

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Recipes :
        Destination("recipes/{categoryId}?title={categoryTitle}&imageUrl={categoryImageUrl}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String) =
            "recipes/$categoryId?title=$categoryTitle&imageUrl=$categoryImageUrl"
    }

    object RecipeDetails : Destination("recipe/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }

    object Favorites : Destination("favorites")
}