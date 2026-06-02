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
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto


class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }

        loadData()

        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                val categories = RecipesRepo.loadCategories()
                Log.i("!!!", "\nКатегорий: ${categories.size}")

                val recipes = categories.map { category ->
                    async(Dispatchers.IO) {
                        try {
                            val recipesList = RecipesRepo.loadRecipes(category.id)
                            Log.i(
                                "!!!",
                                "\nПоток ${Thread.currentThread().name} | Категория ${category.title} | Рецептов: ${recipesList.size}"
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

}

object RecipesRepo {
    private val client = OkHttpClient()

    private suspend fun getRequest(urlString: String): String = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(urlString).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Response code: ${response.code}")
            response.body?.string() ?: throw IOException("Empty response body")
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