package com.example.examplesenco.domain.usecase

import androidx.paging.PagingData
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase  @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<PokemonItem>> {
        return repository.getPokemonPagingData()
    }
}