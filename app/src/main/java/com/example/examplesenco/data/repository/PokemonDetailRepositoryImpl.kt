package com.example.examplesenco.data.repository

import com.example.examplesenco.data.model.detail.OfficialArtwork
import com.example.examplesenco.data.model.detail.OtherSprites
import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import com.example.examplesenco.data.model.detail.Sprites
import com.example.examplesenco.data.remote.ApiService
import com.example.examplesenco.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PokemonDetailRepository {
    override fun getPokemonDetails(name: String): Flow<Result<PokemonDetailResponse>> = flow {
        try {
            val response = apiService.getSearchPokemon(name)
            val speciesResponse = apiService.getPokemonSpecies(name) // 🔥 Obtener descripción

            if (response.isSuccessful && response.body() != null) {
                val pokemon = response.body()!!

                val description = speciesResponse.body()?.flavorTextEntries
                    ?.firstOrNull { it.language.name == "en" }?.text ?: "No description available"

                emit(Result.success(pokemon.copy(description = description))) // 🔥 Agregar descripción
            } else {
                emit(Result.failure(Exception("Error fetching Pokémon details")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

}