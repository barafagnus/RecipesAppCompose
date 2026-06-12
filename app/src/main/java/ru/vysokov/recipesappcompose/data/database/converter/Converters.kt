package ru.vysokov.recipesappcompose.data.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import ru.vysokov.recipesappcompose.data.model.IngredientDto

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split("|||")
    }

    @TypeConverter
    fun fromList(value: List<String>): String {
        return value.joinToString("|||")
    }

    @TypeConverter
    fun fromIngredientDtoList(value: List<IngredientDto>): String = Json.encodeToString(value)

    @TypeConverter
    fun toIngredientDtoList(value: String): List<IngredientDto> = Json.decodeFromString(value)
}