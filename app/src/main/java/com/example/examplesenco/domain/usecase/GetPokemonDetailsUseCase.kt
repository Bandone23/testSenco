package com.example.examplesenco.domain.usecase

import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import com.example.examplesenco.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonDetailRepository
) {
    operator fun invoke(name: String): Flow<Result<PokemonDetailResponse>> {
        return repository.getPokemonDetails(name)
    }
}