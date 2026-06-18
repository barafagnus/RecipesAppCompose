package ru.vysokov.recipesappcompose.app.di

import android.app.Application

class RecipeApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}