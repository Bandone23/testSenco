package com.example.examplesenco.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.paginsource.PokemonPagingSource
import com.example.examplesenco.data.remote.ApiService
import com.example.examplesenco.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
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
          .onEach { pagingData ->
              // Log para verificar que el flujo llega aquí y sigue pasando
              Log.d("PokemonRepository", "Flow received data: $pagingData")
          }
          .catch { e ->
              Log.d("PokemonRepository", "Error in repository: ${e.message}")
              throw Exception("Error al cargar los Pokémon: ${e.message}")
          }
          .onCompletion {
              // Este log se ejecutará cuando el flujo termine (sin importar si fue por error o éxito)
              Log.d("PokemonRepository", "Flow completed.")
          }
  }

}