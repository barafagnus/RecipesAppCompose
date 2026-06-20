package ru.vysokov.recipesappcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.vysokov.recipesappcompose.core.utils.FavoriteDataStoreManager
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    favoritesManager: FavoriteDataStoreManager
) : ViewModel() {
    val favoritesCount: StateFlow<Int> = favoritesManager.getFavoriteCountFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}