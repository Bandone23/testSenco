package com.example.examplesenco.presentation.viewmodel

import androidx.paging.PagingData
import com.example.examplesenco.data.model.PokemonItem

sealed class PokemonViewState {
    object Loading : PokemonViewState()
    data class Success(val data: PagingData<PokemonItem>) : PokemonViewState()
    data class Error(val message: String) : PokemonViewState()
}