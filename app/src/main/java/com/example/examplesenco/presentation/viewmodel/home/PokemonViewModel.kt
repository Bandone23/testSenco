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
import kotlinx.coroutines.flow.catch
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
        processIntent(PokemonIntent.LoadPokemons)
    }

    fun processIntent(intent: PokemonIntent) {
        when (intent) {
            is PokemonIntent.LoadPokemons -> {
                fetchPokemonList()
            }
        }
    }

    fun fetchPokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase()
                .cachedIn(viewModelScope)
                .catch { e ->
                    Log.e("PokemonViewModel", "Error in ViewModel: ${e.message}", e)
                    _pokemonState.value = PokemonViewState.Error(
                        e.message ?: "Error desconocido al cargar Pokémon"
                    )
                }
                .collectLatest { pagingData ->
                    _pokemonState.value = PokemonViewState.Success(flowOf(pagingData))
                }
        }
    }
}