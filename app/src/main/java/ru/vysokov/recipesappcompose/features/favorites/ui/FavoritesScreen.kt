package ru.vysokov.recipesappcompose.features.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.data.model.RecipeDto
import ru.vysokov.recipesappcompose.features.recipes.ui.component.RecipeItem
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipeUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.toUiModel
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

        if (favoritesRecipes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.paddingMedium),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.empty_favorites_text),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
                contentPadding = PaddingValues(Dimens.paddingMedium)
            ) {
                items(items = favoritesRecipes, key = { it.id }) { recipe ->
                    val recipeUiModel = recipe.toUiModel()
                    RecipeItem(
                        model = recipeUiModel,
                        onClick = { onRecipeClick(recipeUiModel.id, recipeUiModel) }
                    )
                }
            }
        }
    }
}