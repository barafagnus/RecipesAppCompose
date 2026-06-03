package ru.vysokov.recipesappcompose.data.repository

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.vysokov.recipesappcompose.core.network.NetworkConfig
import ru.vysokov.recipesappcompose.core.network.api.RecipesApiService

object RetrofitClient {
    private val contentType = "application/json".toMediaType()
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val apiService: RecipesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(RecipesApiService::class.java)
    }

    val recipesRepository: RecipesRepository by lazy {
        RecipesRepositoryImpl(apiService)
    }
}