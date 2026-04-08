package ru.vysokov.recipesappcompose.ui.recipes

import androidx.compose.runtime.Immutable
import ru.vysokov.recipesappcompose.data.model.IngredientDto

@Immutable
data class IngredientsUiModel(
    val name: String,
    val amount: String
)

fun IngredientDto.toUiModel() = IngredientsUiModel(
    name = description,
    amount = "$quantity $unitOfMeasure"
)