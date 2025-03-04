package com.example.examplesenco.data.model.detail

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val sprites: Sprites?,
    @SerializedName("types") val types: List<PokemonTypeWrapper>,
    @SerializedName("description") val description: String = ""

)
