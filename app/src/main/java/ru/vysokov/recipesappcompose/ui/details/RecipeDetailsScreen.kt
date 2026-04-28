package ru.vysokov.recipesappcompose.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.vysokov.recipesappcompose.core.ui.FavoritesButton
import ru.vysokov.recipesappcompose.core.ui.ScreenHeader
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryStub
import ru.vysokov.recipesappcompose.ui.details.components.ErrorPlaceholder
import ru.vysokov.recipesappcompose.ui.details.components.IngredientsList
import ru.vysokov.recipesappcompose.ui.details.components.InstructionsList
import ru.vysokov.recipesappcompose.ui.details.components.PortionsSelector
import ru.vysokov.recipesappcompose.ui.recipes.RecipeUiModel
import ru.vysokov.recipesappcompose.ui.recipes.toUiModel
import ru.vysokov.recipesappcompose.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeUiModel
) {
    val recipeState = remember { RecipesRepositoryStub.getRecipeById(recipe.id)?.toUiModel() }
    var currentPortions by rememberSaveable { mutableStateOf(1) }
    var isFavoriteState by remember { mutableStateOf(false) }

    val scaledIngredients = remember(currentPortions) {
        recipeState?.ingredients?.map { ingredients ->
            val amount = ingredients.amount.toDoubleOrNull()
            if (amount != null) {
                ingredients.copy(
                    amount = (amount * currentPortions).toString()
                )
            } else ingredients
        }
    }

    if (recipeState == null) {
        ErrorPlaceholder()
    } else

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            ScreenHeader(
                title = recipeState.title,
                contentDescription = recipeState.title,
                imageModel = recipeState.imageUrl,
                favoritesButton = {
                    FavoritesButton(
                        isFavorite = isFavoriteState,
                        onClick = { isFavoriteState = !isFavoriteState },
                    )
                }
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            PortionsSelector(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
                currentPortions = currentPortions,
                onPortionsChange = { currentPortions = it }
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            IngredientsList(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
                scaledIngredients = scaledIngredients ?: emptyList()
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            InstructionsList(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
                instructions = recipeState.method
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))
        }
}