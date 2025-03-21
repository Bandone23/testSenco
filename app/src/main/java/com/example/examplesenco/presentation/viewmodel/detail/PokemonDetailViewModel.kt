package com.example.examplesenco.presentation.viewmodel.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplesenco.data.local.FavoritesManager
import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import com.example.examplesenco.domain.usecase.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel  @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val favoritesManager: FavoritesManager
) : ViewModel() {
    private val _pokemonDetailState = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val pokemonDetailState: StateFlow<PokemonDetailState> = _pokemonDetailState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()


    fun fetchPokemonDetails(name: String) {
        viewModelScope.launch {
            launch {
                getPokemonDetailsUseCase(name)
                    .catch { error ->
                        _pokemonDetailState.value = PokemonDetailState.Error(
                            error.message ?: "Unknown error"
                        )
                    }
                    .collect { result ->
                        result.onSuccess { pokemon ->
                            val primaryType = pokemon.types.firstOrNull()?.type?.name ?: "normal"
                            val images = getPokemonImages(pokemon)
                            val description = pokemon.description

                            _pokemonDetailState.value = PokemonDetailState.Success(
                                pokemon,
                                primaryType,
                                images,
                                description
                            )
                        }.onFailure { error ->
                            _pokemonDetailState.value = PokemonDetailState.Error(
                                error.message ?: "Unknown error"
                            )
                        }
                    }
            }


            launch {
                favoritesManager.isFavorite(name)
                    .collect { isFav ->
                        _isFavorite.value = isFav
                    }
            }
        }
    }

    private fun getPokemonImages(pokemon: PokemonDetailResponse): List<String> {
        return listOfNotNull(
            pokemon.sprites?.other?.officialArtwork?.frontImage,
            pokemon.sprites?.frontImage,
            pokemon.sprites?.backImage,
            pokemon.sprites?.frontShiny,
            pokemon.sprites?.backShiny
        )
    }

    fun toggleFavorite(name: String) {
        viewModelScope.launch {
            favoritesManager.toggleFavorite(name)
        }
    }
}