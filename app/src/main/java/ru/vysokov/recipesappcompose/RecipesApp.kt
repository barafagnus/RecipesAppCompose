package ru.vysokov.recipesappcompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.vysokov.recipesappcompose.ui.theme.RecipesAppComposeTheme

@Composable
fun RecipesApp() {
    RecipesAppComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Text(
                text = "Recipes App",
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RecipesAppPreview() {
    RecipesApp()
}