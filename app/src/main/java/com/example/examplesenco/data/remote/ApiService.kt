package com.example.examplesenco.data.remote

import com.example.examplesenco.data.model.PokemonResponse
import com.example.examplesenco.data.model.detail.PokemonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemon(@Query("offset") offset: Int,
                           @Query("limit") limit: Int
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getSearchPokemon(@Path("name") name: String): Response<PokemonDetailResponse>
}