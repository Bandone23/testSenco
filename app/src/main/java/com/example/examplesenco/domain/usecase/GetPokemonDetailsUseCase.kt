package com.example.examplesenco.domain.usecase

import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import com.example.examplesenco.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<Result<PokemonDetailResponse>> {
        return repository.getPokemonDetails(name)
            .map { Result.success(it) }
            .catch { e ->
                emit(Result.failure(e))
            }
    }
}