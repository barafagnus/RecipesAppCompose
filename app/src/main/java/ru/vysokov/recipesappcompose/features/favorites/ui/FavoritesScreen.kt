package ru.vysokov.recipesappcompose.features.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.vysokov.recipesappcompose.R
import ru.vysokov.recipesappcompose.app.di.FavoritesViewModelFactory
import ru.vysokov.recipesappcompose.app.di.RecipeApplication
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.features.favorites.presentation.model.FavoritesUiState
import ru.vysokov.recipesappcompose.features.recipes.ui.component.RecipeItem
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun FavoritesScreen(
    onRecipeClick: (Int) -> Unit,
) {
    val application = LocalContext.current.applicationContext as RecipeApplication

    val viewModel = remember {
        FavoritesViewModelFactory(application).create()
    }
    val uiState: FavoritesUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ScreenHeader(
            title = stringResource(R.string.favorites),
            contentDescription = stringResource(R.string.favorites),
            imageModel = R.drawable.bcg_favorites
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
        } else if (uiState.favoriteRecipes.isEmpty()) {
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
                items(items = uiState.favoriteRecipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        model = recipe,
                        onClick = { onRecipeClick(recipe.id) }
                    )
                }
            }
        }
    }
}