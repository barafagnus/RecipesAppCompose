package ru.vysokov.recipesappcompose.app.di

interface Factory<T> {
    fun create(): T
}