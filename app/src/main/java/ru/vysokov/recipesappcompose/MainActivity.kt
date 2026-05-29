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
import kotlinx.serialization.json.Json
import ru.vysokov.recipesappcompose.core.Constants
import ru.vysokov.recipesappcompose.data.model.CategoryDto
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }
        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        val thread = Thread({
            Log.i("!!!", "Выполняю запрос на потоке:: ${Thread.currentThread().name}")

            val url = URL(Constants.RECIPES_BASE_URL + "/category")
            val conn = url.openConnection() as HttpURLConnection
            try {
                conn.connect()
                val body = conn.inputStream.bufferedReader().use {
                    it.readText()
                }
                val categories = Json.decodeFromString<List<CategoryDto>>(body)

                Log.i("!!!", "responseCode: ${conn.responseCode}")
                Log.i("!!!", "responseMessage: ${conn.responseMessage}")

                Log.i("!!!", "categories size: ${categories.size}")
                Log.i("!!!", "categories: ${categories.map { it.title }}")

            } catch (e: Exception) {
                Log.e("!!!", "load api data: ${e.message}")
            } finally {
                conn.disconnect()
            }
        }, "RecipesApiThread")

        thread.start()

        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
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