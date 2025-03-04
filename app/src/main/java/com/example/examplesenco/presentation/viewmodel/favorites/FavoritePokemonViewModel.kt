package com.example.examplesenco.presentation.viewmodel.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplesenco.data.local.FavoritesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePokemonViewModel @Inject constructor(
    private val favoritesManager: FavoritesManager
) : ViewModel() {
    private val _favoritePokemonList = MutableStateFlow<List<String>>(emptyList()) // ✅ List<String>
    val favoritePokemonList: StateFlow<List<String>> = _favoritePokemonList


    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favoritesManager.getAllFavorites().collectLatest { favorites ->
                _favoritePokemonList.value = favorites
            }
        }
    }
}