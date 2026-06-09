package ru.vysokov.recipesappcompose.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vysokov.recipesappcompose.data.database.converter.Converters
import ru.vysokov.recipesappcompose.data.database.dao.CategoryDao
import ru.vysokov.recipesappcompose.data.database.dao.RecipeDao
import ru.vysokov.recipesappcompose.data.database.entity.CategoryEntity
import ru.vysokov.recipesappcompose.data.database.entity.RecipeEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        CategoryEntity::class,
        RecipeEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipesDatabase? = null

        fun getDatabase(context: Context): RecipesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = RecipesDatabase::class.java,
                    name = "recipes_database"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }

        }
    }
}