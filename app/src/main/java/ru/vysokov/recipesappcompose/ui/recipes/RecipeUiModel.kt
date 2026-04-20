package ru.vysokov.recipesappcompose.ui.recipes

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.data.model.RecipeDto

@Immutable
@Parcelize
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientsUiModel>,
    val method: List<String>,
    val imageUrl: String,
): Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl
)