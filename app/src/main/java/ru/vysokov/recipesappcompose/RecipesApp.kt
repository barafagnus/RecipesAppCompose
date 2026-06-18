package ru.vysokov.recipesappcompose

import android.app.Application
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import ru.vysokov.recipesappcompose.app.di.RecipeApplication
import ru.vysokov.recipesappcompose.app.di.RecipeDetailsViewModelFactory
import ru.vysokov.recipesappcompose.app.di.RecipesViewModelFactory
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.core.utils.FavoriteDataStoreManager
import ru.vysokov.recipesappcompose.features.categories.ui.CategoriesScreen
import ru.vysokov.recipesappcompose.features.details.presentation.RecipeDetailsViewModel
import ru.vysokov.recipesappcompose.features.details.ui.RecipeDetailsScreen
import ru.vysokov.recipesappcompose.features.favorites.ui.FavoritesScreen
import ru.vysokov.recipesappcompose.features.recipes.ui.RecipesScreen
import ru.vysokov.recipesappcompose.ui.navigation.BottomNavigation
import ru.vysokov.recipesappcompose.ui.navigation.Destination
import ru.vysokov.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp(deepLinkIntent: Intent?) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current.applicationContext
    val favoritesManager =
        remember { FavoriteDataStoreManager(context = context) }
    val favoritesCount by favoritesManager.getFavoriteCountFlow().collectAsState(initial = 0)
    val appContainer = (LocalContext.current.applicationContext as RecipeApplication).appContainer
    val recipesRepository = appContainer.recipesRepository

    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->
            val recipeId: Int? = when (uri.scheme) {
                "recipeapp" ->
                    if (uri.host == "recipe") uri.pathSegments[0].toIntOrNull() else null

                "https", "http" ->
                    if (uri.pathSegments[0] == "recipe") uri.pathSegments[1].toIntOrNull() else null

                else -> null
            }

            if (recipeId != null) {
                delay(100)
                navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
            }
        }
    }

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
                    },
                    favoritesCount = favoritesCount
                )
            }
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = Destination.Categories.route,
            ) {
                composable(
                    route = Destination.Categories.route
                ) {
                    CategoriesScreen(
                        onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId,
                                    categoryTitle,
                                    categoryImageUrl
                                )
                            )
                        }
                    )
                }

                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(
                        navArgument(Constants.KEY_CATEGORY_ID) { type = NavType.IntType },
                        navArgument(Constants.KEY_CATEGORY_TITLE) { type = NavType.StringType },
                        navArgument(Constants.KEY_CATEGORY_IMAGE_URL) { type = NavType.StringType },
                    )
                ) { navBackStackEntry ->
                    val recipesViewModel = remember(navBackStackEntry) {
                        RecipesViewModelFactory(
                            repository = recipesRepository,
                            savedStateHandle = navBackStackEntry.savedStateHandle
                        )
                            .create()
                    }

                    RecipesScreen(
                        onRecipeClick = { recipeId ->
                            navController.navigate(
                                Destination.RecipeDetails.createRoute(recipeId)
                            )
                        },
                        viewModel = recipesViewModel
                    )
                }

                composable(
                    route = Destination.Favorites.route
                ) {
                    FavoritesScreen(
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                        }
                    )
                }

                composable(
                    route = Destination.RecipeDetails.route,
                    arguments = listOf(navArgument(Constants.KEY_RECIPE_ID) {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->

                    val recipeDetailsViewModel: RecipeDetailsViewModel =
                        remember(navBackStackEntry) {
                            RecipeDetailsViewModelFactory(
                                repository = appContainer.recipesRepository,
                                application = context.applicationContext as Application,
                                savedStateHandle = navBackStackEntry.savedStateHandle
                            )
                                .create()
                        }
                    RecipeDetailsScreen(
                        viewModel = recipeDetailsViewModel
                    )
                }
            }
        }
    }
}