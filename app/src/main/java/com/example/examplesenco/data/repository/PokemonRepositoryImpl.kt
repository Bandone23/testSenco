package com.example.examplesenco.data.repository


import androidx.paging.PagingData
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import com.example.examplesenco.data.repository.datasource.PokemonSourceFactory
import com.example.examplesenco.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val factory: PokemonSourceFactory
) : PokemonRepository {


    override fun getPokemonPagingData(): Flow<PagingData<PokemonItem>> {
      return factory.getPokemonPagingData()
    }

    override fun getPokemonDetails(name: String): Flow<PokemonDetailResponse> {
        return factory.getPokemonDetails(name)
    }

}