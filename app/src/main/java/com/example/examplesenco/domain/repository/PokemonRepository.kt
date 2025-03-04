package com.example.examplesenco.domain.repository

import androidx.paging.PagingData
import com.example.examplesenco.data.model.PokemonItem
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonPagingData(): Flow<PagingData<PokemonItem>>
}