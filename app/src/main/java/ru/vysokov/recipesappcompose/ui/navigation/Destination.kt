package ru.vysokov.recipesappcompose.ui.navigation

import ru.vysokov.recipesappcompose.core.Constants
import java.net.URLEncoder

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Recipes :
        Destination(
            "recipes/{${Constants.KEY_CATEGORY_ID}}/{${Constants.KEY_CATEGORY_TITLE}}/{${Constants.KEY_CATEGORY_IMAGE_URL}}"
        ) {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String): String {
            val encodedTitle = URLEncoder.encode(categoryTitle, "UTF-8")
            val encodedUrl = URLEncoder.encode(categoryImageUrl, "UTF-8")
            return "recipes/$categoryId/$encodedTitle/$encodedUrl"
        }
    }

    object RecipeDetails : Destination("recipe/{${Constants.KEY_RECIPE_ID}}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }

    object Favorites : Destination("favorites")
}