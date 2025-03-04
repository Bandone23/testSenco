package com.example.examplesenco.data.model.description

import com.google.gson.annotations.SerializedName
import com.example.examplesenco.data.model.description.Language

data class FlavorTextEntry(
    @SerializedName("flavor_text") val text: String,
    @SerializedName("language") val language: Language
)
