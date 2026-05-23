package ru.vysokov.recipesappcompose.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.features.categories.presentation.model.toUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipeUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.toUiModel
import ru.vysokov.recipesappcompose.features.recipes.ui.component.RecipeItem
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipesScreen(
    categoryId: Int?,
    categoryTitle: String,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    categoryImageUrl: String,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(categoryId) {
        isLoading = true
        try {
            categoryId?.let {
                recipes = RecipesRepositoryStub.getRecipesByCategoryId(categoryId).map {
                    it.toUiModel()
                }
            }
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ScreenHeader(
            title = categoryTitle,
            contentDescription = stringResource(R.string.recipes),
            imageModel = categoryImageUrl
            )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
                contentPadding = PaddingValues(Dimens.paddingMedium)
            ) {
                items(items = recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        model = recipe,
                        onClick = { onRecipeClick(recipe.id, recipe) }
                    )
                }
            }
        }

    }
}