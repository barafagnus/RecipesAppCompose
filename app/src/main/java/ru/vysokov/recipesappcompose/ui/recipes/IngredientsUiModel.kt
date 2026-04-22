package ru.vysokov.recipesappcompose.ui.recipes

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import ru.vysokov.recipesappcompose.data.model.IngredientDto

@Immutable
@Parcelize
data class IngredientsUiModel(
    val name: String,
    val amount: String
): Parcelable

fun IngredientDto.toUiModel() = IngredientsUiModel(
    name = description,
    amount = "$quantity $unitOfMeasure"
)