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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.features.categories.presentation.model.toUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.RecipesViewModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipeUiModel
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.RecipesUiState
import ru.vysokov.recipesappcompose.features.recipes.presentation.model.toUiModel
import ru.vysokov.recipesappcompose.features.recipes.ui.component.RecipeItem
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipesScreen(
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
) {
    val viewModel: RecipesViewModel = viewModel()
    val uiState: RecipesUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ScreenHeader(
            title = uiState.categoryTitle,
            contentDescription = stringResource(R.string.recipes),
            imageModel = uiState.categoryImageUrl
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Ошибка загрузки: ${uiState.errorMessage}")
            }
        } else if (uiState.recipes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
                contentPadding = PaddingValues(Dimens.paddingMedium)
            ) {
                items(items = uiState.recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        model = recipe,
                        onClick = { onRecipeClick(recipe.id, recipe) }
                    )
                }
            }
        }

    }
}