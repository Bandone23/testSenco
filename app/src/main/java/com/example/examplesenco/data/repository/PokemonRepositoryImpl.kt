package com.example.examplesenco.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.paginsource.PokemonPagingSource
import com.example.examplesenco.data.remote.ApiService
import com.example.examplesenco.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PokemonRepository {

  override  fun getPokemonPagingData(): Flow<PagingData<PokemonItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(apiService) }
        ).flow
    }

/*    suspend fun getPokemonType(pokemonId: String): String {
        return try {
            val response = apiService.getSearchPokemon(pokemonId)
            if (response.isSuccessful) {
                response.body()!!.name
            } else {
                "normal"
            }
        } catch (e: Exception) {
            "normal"
        }
    }*/
}