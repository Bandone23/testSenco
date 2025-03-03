package com.example.examplesenco.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.data.paginsource.PokemonPagingSource
import com.example.examplesenco.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getPokemonPagingData(): Flow<PagingData<PokemonItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(apiService) }
        ).flow
    }
}