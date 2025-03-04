package com.example.examplesenco.presentation.viewmodel.detail

import com.example.examplesenco.data.model.detail.PokemonDetailResponse

sealed class PokemonDetailState {
    object Loading : PokemonDetailState()
    data class Success(  val data: PokemonDetailResponse,
                         val type: String,
                         val images: List<String>,
                         val description: String) : PokemonDetailState()
    data class Error(val message: String) : PokemonDetailState()
}