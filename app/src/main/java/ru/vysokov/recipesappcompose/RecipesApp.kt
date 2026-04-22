package ru.vysokov.recipesappcompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.ui.categories.CategoriesScreen
import ru.vysokov.recipesappcompose.ui.details.RecipeDetailsScreen
import ru.vysokov.recipesappcompose.ui.favorites.FavoritesScreen
import ru.vysokov.recipesappcompose.ui.navigation.BottomNavigation
import ru.vysokov.recipesappcompose.ui.navigation.Destination
import ru.vysokov.recipesappcompose.ui.recipes.RecipeUiModel
import ru.vysokov.recipesappcompose.ui.recipes.RecipesScreen
import ru.vysokov.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    RecipesAppComposeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        if (currentRoute != Destination.Categories.route) {
                            navController.navigate(Destination.Categories.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    },
                    onFavoritesClick = {
                        if (currentRoute != Destination.Favorites.route) {
                            navController.navigate(Destination.Favorites.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = Destination.Categories.route
            ) {
                composable(
                    route = Destination.Categories.route
                ) {
                    CategoriesScreen(
                        onCategoryClick = { categoryId ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId
                                )
                            )
                        },
                    )
                }

                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(
                        navArgument("categoryId") { type = NavType.IntType },
                    )
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId")
                        ?: error("Category ID is required.")
                    val title = RecipesRepositoryStub.getCategoryById(categoryId)?.title
                        ?: error("Category ID is required.")

                    RecipesScreen(
                        categoryId = categoryId,
                        categoryTitle = title,
                        onRecipeClick = { recipeId, recipe ->
                            backStackEntry?.savedStateHandle?.set(
                                Constants.KEY_RECIPE_OBJECT,
                                recipe
                            )
                            navController.navigate(
                                Destination.RecipeDetails.createRoute(recipeId)
                            )
                        }
                    )
                }

                composable(
                    route = Destination.Favorites.route
                ) {
                    FavoritesScreen()
                }

                composable(
                    route = Destination.RecipeDetails.route
                ) {
                    RecipeDetailsScreen(
                        recipe = navController.previousBackStackEntry?.savedStateHandle?.get<RecipeUiModel>(
                            Constants.KEY_RECIPE_OBJECT
                        ) ?: error("Recipe object is required.")
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RecipesAppPreview() {
    RecipesApp()
}