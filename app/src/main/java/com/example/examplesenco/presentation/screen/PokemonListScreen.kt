package com.example.examplesenco.presentation.screen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.examplesenco.presentation.viewmodel.PokemonViewModel
import com.example.examplesenco.presentation.viewmodel.PokemonViewState

@Composable
fun PokemonListScreen(viewModel: PokemonViewModel,
                      modifier: Modifier = Modifier
) {
    val pokemonState by viewModel.pokemonState.collectAsStateWithLifecycle()
    Log.d("PokemonListScreen", "State: $pokemonState")
    when (pokemonState) {
        is PokemonViewState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is PokemonViewState.Success -> {
            val pokemonFlow = (pokemonState as PokemonViewState.Success).data
            val pokemonPagingData = pokemonFlow.collectAsLazyPagingItems()

            if (pokemonPagingData.itemCount == 0) {
                Log.d("PokemonListScreen", "No items found")
               LoadingScreen()
            } else {
                PokemonList(pokemonPagingData)
            }
        }

        is PokemonViewState.Error -> {
            val errorMessage = (pokemonState as PokemonViewState.Error).message
            ErrorScreen(errorMessage, modifier = modifier)
        }
    }
}
