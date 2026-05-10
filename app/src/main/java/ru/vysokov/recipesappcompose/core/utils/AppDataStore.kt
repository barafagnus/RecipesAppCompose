package ru.vysokov.recipesappcompose.core.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ru.vysokov.recipesappcompose.core.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.DATASTORE_PREFS,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context = context,
                sharedPreferencesName = Constants.FAVORITES_PREFS_NAME
            )
        )
    }
)

object PreferencesKeys {
    val FAVORITE_RECIPE_IDS = stringSetPreferencesKey("favorite_recipe_ids")
}