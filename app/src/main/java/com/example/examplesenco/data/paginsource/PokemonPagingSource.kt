package com.example.examplesenco.data.paginsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.remote.ApiService

class PokemonPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, PokemonItem>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonItem> {
        val page = params.key ?: 0
        return try {

            val response = apiService.getPokemon(offset = page * 20, limit = 20)
            if (!response.isSuccessful || response.body() == null) {

                return LoadResult.Error(Exception("API error"))
            }
            val pokemons = response.body()?.results ?: emptyList()


            LoadResult.Page(
                data = pokemons,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (pokemons.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}