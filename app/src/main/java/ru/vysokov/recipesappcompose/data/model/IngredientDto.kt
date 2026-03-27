package ru.vysokov.recipesappcompose.data.model

data class IngredientDto(
    val quantity: Float,
    val unitOfMeasure: String,
    val description: String
)