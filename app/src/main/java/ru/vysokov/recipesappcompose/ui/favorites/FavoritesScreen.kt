package ru.vysokov.recipesappcompose.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.data.model.RecipeDto
import ru.vysokov.recipesappcompose.ui.recipes.RecipeItem
import ru.vysokov.recipesappcompose.ui.recipes.RecipeUiModel
import ru.vysokov.recipesappcompose.ui.recipes.toUiModel
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun FavoritesScreen(
    favoritesRecipes: List<RecipeDto>,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ScreenHeader(
            title = stringResource(R.string.favorites),
            contentDescription = stringResource(R.string.favorites),
            imageModel = R.drawable.bcg_favorites
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
            contentPadding = PaddingValues(Dimens.paddingMedium)
        ) {
            items(items = favoritesRecipes, key = { it.id }) { recipe ->
                val recipe = recipe.toUiModel()
                RecipeItem(
                    model = recipe,
                    onClick = { onRecipeClick(recipe.id, recipe) }
                )
            }
        }
    }
}