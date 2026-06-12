package ru.vysokov.recipesappcompose.data.model

import kotlinx.serialization.Serializable
import ru.vysokov.recipesappcompose.data.database.entity.CategoryEntity

@Serializable
data class CategoryDto(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

fun CategoryDto.toEntity() =
    CategoryEntity(
        id = id,
        name = title,
        description = description,
        imageUrl = imageUrl
    )