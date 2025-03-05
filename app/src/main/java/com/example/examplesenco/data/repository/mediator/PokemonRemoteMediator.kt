package com.example.examplesenco.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.examplesenco.data.local.dao.PokemonDao
import com.example.examplesenco.data.local.entity.PokemonEntity
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val apiService: ApiService,
    private val pokemonDao: PokemonDao
) : RemoteMediator<Int, PokemonItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonItem>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val pokemonCount = pokemonDao.getPokemonCount()
                    if (pokemonCount > 0) {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    }
                    0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.pages.size
                }
            }

            val response = apiService.getPokemon(offset = page * 20, limit = 20)

            if (!response.isSuccessful) {
                return MediatorResult.Error(HttpException(response))
            }

            val pokemons = response.body()?.results ?: emptyList()

            if (loadType == LoadType.REFRESH) {
                pokemonDao.clearAll()
            }

            val pokemonEntities = pokemons.map { pokemonItem ->
                PokemonEntity(
                    name = pokemonItem.name,
                    url = pokemonItem.url
                )
            }
            pokemonDao.insertAll(pokemonEntities)

            MediatorResult.Success(
                endOfPaginationReached = pokemons.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}