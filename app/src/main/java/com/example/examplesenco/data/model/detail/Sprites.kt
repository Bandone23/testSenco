package com.example.examplesenco.data.model.detail

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("other") val other: OtherSprites,
    @SerializedName("front_default") val frontImage: String,
    @SerializedName("back_default") val backImage: String,
    @SerializedName("front_shiny") val frontShiny: String,
    @SerializedName("back_shiny") val backShiny: String,
)
