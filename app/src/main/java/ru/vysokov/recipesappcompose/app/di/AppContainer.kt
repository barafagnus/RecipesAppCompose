package ru.vysokov.recipesappcompose.app.di

import android.content.Context
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.vysokov.recipesappcompose.BuildConfig
import ru.vysokov.recipesappcompose.core.network.NetworkConfig
import ru.vysokov.recipesappcompose.core.network.api.RecipesApiService
import ru.vysokov.recipesappcompose.data.database.RecipesDatabase
import ru.vysokov.recipesappcompose.data.repository.RecipesRepositoryImpl
import java.util.concurrent.TimeUnit

class AppContainer(context: Context) {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val contentType = "application/json".toMediaType()
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val recipesApi: RecipesApiService =
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(RecipesApiService::class.java)


    private val recipesDatabase = RecipesDatabase.buildDatabase(context)
    val recipesRepository = RecipesRepositoryImpl(recipesApi, recipesDatabase)
}