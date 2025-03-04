package com.example.examplesenco.domain.repository

import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import kotlinx.coroutines.flow.Flow

interface PokemonDetailRepository {
    fun getPokemonDetails(name: String): Flow<Result<PokemonDetailResponse>>
}
