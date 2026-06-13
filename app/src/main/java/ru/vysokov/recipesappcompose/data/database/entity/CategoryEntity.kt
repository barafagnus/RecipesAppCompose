package ru.vysokov.recipesappcompose.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vysokov.recipesappcompose.data.model.CategoryDto

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

fun CategoryEntity.toCategoryDto() =
    CategoryDto(
        id = id,
        title = name,
        description = description,
        imageUrl = imageUrl
    )