package ru.vysokov.recipesappcompose.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.vysokov.recipesappcompose.data.database.dao.CategoryDao
import ru.vysokov.recipesappcompose.data.database.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        fun buildDatabase(context: Context): RecipesDatabase {
            return Room.databaseBuilder(
                context = context.applicationContext,
                klass = RecipesDatabase::class.java,
                name = "recipes_database"
            )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        }

    }
}