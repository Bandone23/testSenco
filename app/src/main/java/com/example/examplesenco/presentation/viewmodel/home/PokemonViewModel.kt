package com.example.examplesenco.presentation.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.examplesenco.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _pokemonState = MutableStateFlow<PokemonViewState>(PokemonViewState.Loading)
    val pokemonState: StateFlow<PokemonViewState> = _pokemonState.asStateFlow()

    init {
        fetchPokemonList()
    }


    private fun fetchPokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase()
                .cachedIn(viewModelScope) // Optimiza el flujo para recomposiciones en Compose
                .collectLatest { pagingData ->
                    Log.d("PokemonViewModel", "Data received: $pagingData")
                    _pokemonState.value = PokemonViewState.Success(flowOf(pagingData))
                }
        }
    }
}