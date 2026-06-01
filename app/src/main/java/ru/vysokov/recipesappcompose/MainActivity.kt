package ru.vysokov.recipesappcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.vysokov.recipesappcompose.RecipesRepoCoroutines.loadRecipes
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.flatten


class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }

        loadDataUsingThreadPool()

        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
    }

    // По классике Thread Pools
    private fun loadDataUsingThreadPool() {
        threadPool.execute {
            try {
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                val categories = RecipesRepoTP.loadCategories()

                categories.forEach { category ->
                    threadPool.execute {
                        try {
                            val recipesList = RecipesRepoTP.loadRecipes(category.id)
                            Log.i(
                                "!!!", "\nПоток ${Thread.currentThread().name} | Категория ${category.title} | Рецептов: ${recipesList.size}"
                            )
                        } catch (e: Exception) {
                            Log.e("!!!", "load api data Recipe: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("!!!", "load api data Category: ${e.message}")
            }
        }
    }

    // Через корутины
    private fun loadDataUsingCoroutines() {
        lifecycleScope.launch {
            try {
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                val categories = RecipesRepoCoroutines.loadCategories()
                Log.i("!!!", "\nКатегорий: ${categories.size}")

                val recipes = categories.map { category ->
                    async(Dispatchers.IO) {
                        try {
                            val recipesList = loadRecipes(category.id)
                            Log.i(
                                "!!!", "\nПоток ${Thread.currentThread().name} | Категория ${category.title} | Рецептов: ${recipesList.size}"
                            )
                            recipesList
                        } catch (e: Exception) {
                            Log.e("!!!", "load api data Recipe: ${e.message}")
                            emptyList<RecipeDto>()
                        }
                    }
                }.awaitAll().flatten()

                Log.i("!!!", "\nРецептов всего загружено: ${recipes.size}")

            } catch (e: Exception) {
                Log.e("!!!", "load api data Category: ${e.message}")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }

}

// По классике Thread Pools
object RecipesRepoTP {
    private fun getRequest(urlString: String): String  {
        val url = URL(urlString)
        val conn = url.openConnection() as HttpURLConnection
        try {
            conn.connect()
            return conn.inputStream.bufferedReader().use { it.readText() }
        } finally {
            conn.disconnect()
        }
    }

     fun loadCategories(): List<CategoryDto> {
        val response = getRequest("${Constants.RECIPES_BASE_URL}/category")
        return Json.decodeFromString(response)
    }

     fun loadRecipes(categoryId: Int): List<RecipeDto> {
        val response = getRequest("${Constants.RECIPES_BASE_URL}/category/$categoryId/recipes")
        return Json.decodeFromString(response)
    }
}

// Через корутины
object RecipesRepoCoroutines {
    private suspend fun getRequest(urlString: String): String = withContext(Dispatchers.IO) {
        val url = URL(urlString)
        val conn = url.openConnection() as HttpURLConnection
        try {
            conn.connect()
            conn.inputStream.bufferedReader().use { it.readText() }
        } finally {
            conn.disconnect()
        }
    }

    suspend fun loadCategories(): List<CategoryDto> {
        val response = getRequest("${Constants.RECIPES_BASE_URL}/category")
        return Json.decodeFromString(response)
    }

    suspend fun loadRecipes(categoryId: Int): List<RecipeDto> {
        val response = getRequest("${Constants.RECIPES_BASE_URL}/category/$categoryId/recipes")
        return Json.decodeFromString(response)
    }
}