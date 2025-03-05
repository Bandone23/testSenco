package com.example.examplesenco.data.repository.datasource

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.examplesenco.data.local.dao.PokemonDao
import com.example.examplesenco.data.local.entity.PokemonEntity
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import com.example.examplesenco.data.paginsource.LocalPokemonPagingSource
import com.example.examplesenco.data.paginsource.PokemonPagingSource
import com.example.examplesenco.data.remote.ApiService
import com.example.examplesenco.data.repository.mediator.PokemonRemoteMediator
import com.example.examplesenco.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject
class PokemonSourceFactory @Inject constructor(
    private val apiService: ApiService,
    private val pokemonDao: PokemonDao,
    private val connectivityManager: ConnectivityManager
):PokemonRepository  {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonPagingData(): Flow<PagingData<PokemonItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = PokemonRemoteMediator(
                apiService = apiService,
                pokemonDao = pokemonDao
            ),
            pagingSourceFactory = {
                // Si no hay conexión, usa la base de datos local
                if (!isNetworkAvailable()) {
                    Log.d("PokemonRepository", "Using local data source")
                    LocalPokemonPagingSource(pokemonDao)
                } else {
                    Log.d("PokemonRepository", "Using remote data source")
                    PokemonPagingSource(apiService)
                }
            }
        ).flow
            .catch { e ->
                Log.e("PokemonRepository", "Error in repository: ${e.message}", e)
                emit(PagingData.empty())
            }
    }




    override fun getPokemonDetails(name: String): Flow<PokemonDetailResponse>  = flow {
        try {
            val response = apiService.getSearchPokemon(name)
            val speciesResponse = apiService.getPokemonSpecies(name)
            if (response.isSuccessful) {
                response.body()?.let {
                    val description = speciesResponse.body()?.flavorTextEntries
                        ?.firstOrNull { it.language.name == "en" }?.text ?: "No description available"
                    emit(it.copy(description = description))
                } ?: throw Exception("Empty response body")
            } else {
                throw Exception("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Método para guardar Pokémon en la base de datos
    suspend fun savePokemonsToDatabase(pokemons: List<PokemonItem>) {
        val pokemonEntities = pokemons.map { PokemonEntity.fromDomainModel(it) }
        pokemonDao.insertAll(pokemonEntities)
    }
}