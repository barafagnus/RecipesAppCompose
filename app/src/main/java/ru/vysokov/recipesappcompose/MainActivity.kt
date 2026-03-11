package ru.vysokov.recipesappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.vysokov.recipesappcompose.ui.theme.RecipesAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipesAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Text(
                        text = "Recipes App",
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}