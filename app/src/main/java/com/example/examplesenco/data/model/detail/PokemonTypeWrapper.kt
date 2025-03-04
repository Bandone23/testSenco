package com.example.examplesenco.data.model.detail

import com.google.gson.annotations.SerializedName

data class PokemonTypeWrapper(
    @SerializedName("type") val type: PokemonType
)
