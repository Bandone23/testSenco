package com.example.examplesenco.data.remote

import com.example.examplesenco.data.model.PokemonResponse
import com.example.examplesenco.data.model.description.PokemonSpeciesResponse
import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(@Query("offset") offset: Int,
                           @Query("limit") limit: Int
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getSearchPokemon(@Path("name") name: String): Response<PokemonDetailResponse>

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(@Path("name") name: String): Response<PokemonSpeciesResponse>
}