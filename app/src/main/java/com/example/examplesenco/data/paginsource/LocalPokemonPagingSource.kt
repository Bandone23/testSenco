package com.example.examplesenco.data.paginsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.examplesenco.data.local.dao.PokemonDao
import com.example.examplesenco.data.model.PokemonItem
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class LocalPokemonPagingSource (
    private val pokemonDao: PokemonDao
) : PagingSource<Int, PokemonItem>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonItem> {
        try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            // Obtener todos los Pokémon de la base de datos
            val pokemonEntities = pokemonDao.getAllPokemons().first()

            // Aplicar paginación manual
            val startPosition = page * pageSize
            val endPosition = minOf((page + 1) * pageSize, pokemonEntities.size)

            // Asegurarse de que no nos salimos de los límites
            if (startPosition >= pokemonEntities.size) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = if (page > 0) page - 1 else null,
                    nextKey = null
                )
            }

            // Tomar la parte correspondiente a esta página
            val paginatedEntities = pokemonEntities.subList(startPosition, endPosition)

            // Convertir las entidades a modelos de dominio
            val items = paginatedEntities.map { entity ->
                PokemonItem(
                    name = entity.name,
                    url = entity.url
                )
            }

            return LoadResult.Page(
                data = items,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (endPosition < pokemonEntities.size) page + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}