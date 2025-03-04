package com.example.examplesenco.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.examplesenco.data.model.PokemonItem

@Composable
fun PokemonList(pokemonPagingData: LazyPagingItems<PokemonItem>, modifier: Modifier = Modifier) {
    Log.d("PokemonList", "Item count: ${pokemonPagingData.itemCount}")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 🔹 2 columnas en la cuadrícula
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp, start = 8.dp, end = 8.dp), // 🔹 Añadir padding arriba
        contentPadding = PaddingValues(8.dp), // 🔹 Espaciado dentro de la grilla
        horizontalArrangement = Arrangement.spacedBy(8.dp), // 🔹 Espaciado entre columnas
        verticalArrangement = Arrangement.spacedBy(8.dp) // 🔹 Espaciado entre filas
    ) {
        items(pokemonPagingData.itemCount) { index ->
            val pokemon = pokemonPagingData[index]
            if (pokemon != null) {
                PokemonItem(pokemon,"")
            }
        }
    }
}