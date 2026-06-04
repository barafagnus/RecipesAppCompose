package ru.vysokov.recipesappcompose.features.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ru.vysokov.recipesappcompose.core.ui.FavoritesButton
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.core.ui.ShareButton
import ru.vysokov.recipesappcompose.core.utils.shareRecipe
import ru.vysokov.recipesappcompose.features.details.presentation.RecipeDetailsViewModel
import ru.vysokov.recipesappcompose.features.details.presentation.model.RecipeDetailsUiState
import ru.vysokov.recipesappcompose.features.details.ui.component.IngredientsList
import ru.vysokov.recipesappcompose.features.details.ui.component.InstructionsList
import ru.vysokov.recipesappcompose.features.details.ui.component.PortionsSelector
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailsViewModel
) {
    val uiState: RecipeDetailsUiState by viewModel.uiState.collectAsState()
    val recipe = uiState.recipe
    val context = LocalContext.current

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
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
    } else if (recipe != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(
                title = recipe.title,
                contentDescription = recipe.title,
                imageModel = recipe.imageUrl,
                favoritesButton = {
                    FavoritesButton(
                        isFavorite = uiState.isFavorite,
                        onClick = { viewModel.toggleFavorite() },
                    )
                },
                shareButton = {
                    ShareButton(
                        onClick = { shareRecipe(context, recipe.id, recipe.title) }
                    )
                }
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            PortionsSelector(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
                currentPortions = uiState.currentPortions,
                onPortionsChange = { viewModel.updatePortions(it) }
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            IngredientsList(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
                scaledIngredients = uiState.scaledIngredients ?: emptyList()
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            InstructionsList(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
                instructions = recipe.method
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))
        }
    }
}