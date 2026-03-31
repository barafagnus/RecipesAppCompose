package ru.vysokov.recipesappcompose.ui.recipes

import androidx.compose.runtime.Immutable
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.data.model.IngredientDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
)

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients,
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl
)