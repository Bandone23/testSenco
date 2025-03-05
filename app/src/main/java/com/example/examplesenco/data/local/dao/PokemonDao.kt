package com.example.examplesenco.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplesenco.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemons(): Flow<List<PokemonEntity>>

    @Query("DELETE FROM pokemon_table")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM pokemon_table")
    suspend fun getPokemonCount(): Int
}