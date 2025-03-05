package com.example.examplesenco.presentation.viewmodel.home

sealed class PokemonIntent {
    object LoadPokemons : PokemonIntent()
}