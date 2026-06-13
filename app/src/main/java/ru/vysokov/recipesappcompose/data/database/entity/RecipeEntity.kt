package ru.vysokov.recipesappcompose.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vysokov.recipesappcompose.data.model.IngredientDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val categoryId: Int,
    val imageUrl: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>
)

fun RecipeEntity.toRecipeDto() =
    RecipeDto(
        id = id,
        ingredients = ingredients,
        title = title,
        method = method,
        imageUrl = imageUrl
    )