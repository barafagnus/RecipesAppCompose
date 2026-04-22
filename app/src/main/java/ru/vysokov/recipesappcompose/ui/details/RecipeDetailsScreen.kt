package ru.vysokov.recipesappcompose.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vysokov.recipesappcompose.ui.recipes.RecipeUiModel

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeUiModel
) {

    Column() {
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier.padding(16.dp)
        )
    }
}