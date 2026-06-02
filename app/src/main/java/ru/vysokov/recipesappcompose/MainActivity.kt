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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.core.network.NetworkConfig
import ru.vysokov.recipesappcompose.core.network.api.RecipesApiService
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
        val recipesApi = RecipesApi.apiService

        lifecycleScope.launch {
            try {
                val categories = recipesApi.getCategories()
                Log.i("!!!", "\nКатегорий: ${categories.size}")

                val recipes = categories.map { categoryDto ->
                    async(Dispatchers.IO) {
                        try {
                            recipesApi.getRecipesByCategory(categoryDto.id)
                        } catch (e: Exception) {
                            Log.e("!!!", "Error load Recipe: ${e.message}")
                            emptyList<RecipeDto>()
                        }
                    }
                }.awaitAll().flatten()
                Log.i("!!!", "\nРецептов всего загружено: ${recipes.size}")
            } catch (e: Exception) {
                Log.e("!!!", "Error load category: ${e.message}")
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

object RecipesApi {
    private val contentType = "application/json".toMediaType()
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    val apiService: RecipesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(RecipesApiService::class.java)
    }
}