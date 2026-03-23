package ru.vysokov.recipesappcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.vysokov.recipesappcompose.ui.categories.CategoriesScreen
import ru.vysokov.recipesappcompose.ui.navigation.BottomNavigation
import ru.vysokov.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }

    RecipesAppComposeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreen = ScreenId.CATEGORIES },
                    onFavoritesClick = { currentScreen = ScreenId.FAVORITES }
                )
            }
        ) { paddingValues ->
            when (currentScreen) {
                ScreenId.CATEGORIES -> {
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        CategoriesScreen()
                    }
                }
                ScreenId.FAVORITES -> {
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Избранное"
                        )
                    }
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