package com.example.examplesenco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examplesenco.data.model.PokemonItem

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val url: String
) {

    fun toDomainModel(): PokemonItem {
        return PokemonItem(name = name, url = url)
    }

    companion object {
        fun fromDomainModel(pokemonItem: PokemonItem): PokemonEntity {
            return PokemonEntity(
                name = pokemonItem.name,
                url = pokemonItem.url
            )
        }
    }
}
