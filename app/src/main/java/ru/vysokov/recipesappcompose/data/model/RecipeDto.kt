package ru.vysokov.recipesappcompose.data.model

import kotlinx.serialization.Serializable
import ru.vysokov.recipesappcompose.data.database.entity.RecipeEntity

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String
)

fun RecipeDto.toEntity(categoryId: Int) =
    RecipeEntity(
        id = id,
        title = title,
        categoryId = categoryId,
        imageUrl = imageUrl,
        ingredients = ingredients,
        method = method
    )